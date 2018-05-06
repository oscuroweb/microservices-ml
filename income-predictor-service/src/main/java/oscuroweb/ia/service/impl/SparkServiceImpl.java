package oscuroweb.ia.service.impl;

import java.util.Collections;

import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.Transformer;
import org.apache.spark.ml.classification.RandomForestClassificationModel;
import org.apache.spark.ml.feature.IndexToString;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import oscuroweb.ia.dto.Column;
import oscuroweb.ia.dto.IncomeDto;
import oscuroweb.ia.dto.OutputDto;
import oscuroweb.ia.service.SparkService;

@Service
public class SparkServiceImpl implements SparkService {
	
	@Autowired
	private SparkSession spark;

	@Value("${models.path}")
	private String modelFolderPath;

	@Override
	public OutputDto evaluate(IncomeDto input) {
		spark.sparkContext().setLogLevel("ERROR");
		
		Encoder<IncomeDto> incomeEncoder = Encoders.bean(IncomeDto.class);
		Dataset<Row> dataset = spark.createDataset(
		  Collections.singletonList(input),
		  incomeEncoder
		).toDF();
		
		dataset.show();

		String[] inputColumn = { 
				Column.AGE.colName(), 
				Column.WORKCLASS_INDEX.colName(), 
				Column.FNLWGT.colName(), 
				Column.EDUCATION_INDEX.colName(), 
				Column.EDUCATION_NUM.colName(), 
				Column.MARITAL_STATUS_INDEX.colName(), 
				Column.OCCUPATION_INDEX.colName(),
				Column.RELATIONSHIP_INDEX.colName(), 
				Column.RACE_INDEX.colName(), 
				Column.SEX_INDEX.colName(), 
				Column.CAPITAL_GAIN.colName(), 
				Column.CAPITAL_LOSS.colName(), 
				Column.HOURS_PER_WEEK.colName(),
				Column.NATIVE_COUNTRY_INDEX.colName()};
		
		VectorAssembler vectorAssembler = new VectorAssembler()
				.setInputCols(inputColumn)
				.setOutputCol(Column.FEATURES.colName());
		

		RandomForestClassificationModel model = RandomForestClassificationModel.load(modelFolderPath.concat("rfModel"));
		
		PipelineModel pipelineModel = new PipelineModel("rfPipeline", new Transformer[] {
				StringIndexerModel.load(modelFolderPath.concat("_c1")).setInputCol(Column.WORKCLASS.colName()).setOutputCol(Column.WORKCLASS_INDEX.colName()),
				StringIndexerModel.load(modelFolderPath.concat("_c3")).setInputCol(Column.EDUCATION.colName()).setOutputCol(Column.EDUCATION_INDEX.colName()),
				StringIndexerModel.load(modelFolderPath.concat("_c5")).setInputCol(Column.MARITAL_STATUS.colName()).setOutputCol(Column.MARITAL_STATUS_INDEX.colName()),
				StringIndexerModel.load(modelFolderPath.concat("_c6")).setInputCol(Column.OCCUPATION.colName()).setOutputCol(Column.OCCUPATION_INDEX.colName()),
				StringIndexerModel.load(modelFolderPath.concat("_c7")).setInputCol(Column.RELATIONSHIP.colName()).setOutputCol(Column.RELATIONSHIP_INDEX.colName()),
				StringIndexerModel.load(modelFolderPath.concat("_c8")).setInputCol(Column.RACE.colName()).setOutputCol(Column.RACE_INDEX.colName()),
				StringIndexerModel.load(modelFolderPath.concat("_c9")).setInputCol(Column.SEX.colName()).setOutputCol(Column.SEX_INDEX.colName()),
				StringIndexerModel.load(modelFolderPath.concat("_c13")).setInputCol(Column.NATIVE_COUNTRY.colName()).setOutputCol(Column.NATIVE_COUNTRY_INDEX.colName()),
				vectorAssembler,
				model
		});
		
		dataset.show();
		
		Dataset<Row> predictions = pipelineModel.transform(dataset);
	
		predictions.show();

		// Convert indexed labels back to original labels.
		IndexToString c14Label = new IndexToString()
				.setInputCol(Column.PREDICTION.colName())
				.setOutputCol(Column.PREDICTION_LABEL.colName())
				.setLabels(StringIndexerModel.load(modelFolderPath.concat("_c14")).labels());
		predictions = c14Label.transform(predictions);
		
		Dataset<Row> predictionsResult = predictions.select(Column.PREDICTION.colName(), Column.PREDICTION_LABEL.colName());
		
		predictionsResult.show();
		
		return OutputDto.builder().label((String)  predictionsResult.collectAsList().get(0).get(1)).build();
		
	}

}
