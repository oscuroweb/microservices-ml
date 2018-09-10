package oscuroweb.ia.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.ml.classification.DecisionTreeClassificationModel;
import org.apache.spark.ml.classification.DecisionTreeClassifier;
import org.apache.spark.ml.classification.RandomForestClassificationModel;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.IndexToString;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.util.MLWritable;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import oscuroweb.ia.dto.Column;
import oscuroweb.ia.service.SparkService;

@Slf4j
@Service
public class SparkServiceImpl implements SparkService {

	@Autowired
	private SparkSession spark;

	@Value("${models.path}")
	private String modelFolderPath;
	
	@Value("${models.versioned:false}")
	private Boolean modelVersioned;

	@Value("${dataset.file}")
	private String csvFile;
	
//	private long version = 0;

	/**
	 * Scheduler Service that construct a Machine Learning model to predict the income.
	 */
	@Scheduled(fixedDelay = 360000)
	@Override
	public void mlService() {
		
		Instant instant = Instant.now();
		Long version = instant.toEpochMilli();
		
		spark.sparkContext().setLogLevel("ERROR");

		Dataset<Row> dataset = spark.read()
				.option("ignoreLeadingWhiteSpace", true)
				.csv(csvFile);
		dataset.show();

		dataset = renameDataset(dataset);

		// Transform string columns to integer
		Map<String, StringIndexerModel> indexerModels = new HashMap<String, StringIndexerModel>();
		indexerModels.put("_c1", new StringIndexer().setInputCol(Column.WORKCLASS.colName()).setOutputCol(Column.WORKCLASS_INDEX.colName()).fit(dataset));
		indexerModels.put("_c3", new StringIndexer().setInputCol(Column.EDUCATION.colName()).setOutputCol(Column.EDUCATION_INDEX.colName()).fit(dataset));
		indexerModels.put("_c5", new StringIndexer().setInputCol(Column.MARITAL_STATUS.colName()).setOutputCol(Column.MARITAL_STATUS_INDEX.colName()).fit(dataset));
		indexerModels.put("_c6", new StringIndexer().setInputCol(Column.OCCUPATION.colName()).setOutputCol(Column.OCCUPATION_INDEX.colName()).fit(dataset));
		indexerModels.put("_c7", new StringIndexer().setInputCol(Column.RELATIONSHIP.colName()).setOutputCol(Column.RELATIONSHIP_INDEX.colName()).fit(dataset));
		indexerModels.put("_c8", new StringIndexer().setInputCol(Column.RACE.colName()).setOutputCol(Column.RACE_INDEX.colName()).fit(dataset));
		indexerModels.put("_c9", new StringIndexer().setInputCol(Column.SEX.colName()).setOutputCol(Column.SEX_INDEX.colName()).fit(dataset));
		indexerModels.put("_c13", new StringIndexer().setInputCol(Column.NATIVE_COUNTRY.colName()).setOutputCol(Column.NATIVE_COUNTRY_INDEX.colName()).fit(dataset));
		indexerModels.put("_c14", new StringIndexer().setInputCol(Column.INCOME.colName()).setOutputCol(Column.INCOME_INDEX.colName()).fit(dataset));

		for (String modelKey : indexerModels.keySet()) {
			StringIndexerModel model = indexerModels.get(modelKey);
			dataset = model.transform(dataset);
			saveModel(model, modelKey, version);

		}

		// Create features column
		String[] input = { 
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
		
		dataset = new VectorAssembler()
				.setInputCols(input)
				.setOutputCol(Column.FEATURES.colName())
				.transform(dataset);

		dataset.show();
		

		// Convert indexed labels back to original labels.
		IndexToString c14Label = new IndexToString()
				.setInputCol(Column.PREDICTION.colName())
				.setOutputCol(Column.PREDICTION_LABEL.colName())
				.setLabels(indexerModels.get("_c14").labels());
		

		// Split dataset
		Dataset<Row>[] splitDataset = dataset.randomSplit(new double[] { 0.7, 0.3 });
		Dataset<Row> training = splitDataset[0];
		Dataset<Row> test = splitDataset[1];

		// DecisionTree Model
		DecisionTreeClassificationModel dtModel = decisionTreeModel(training);
		Dataset<Row> predictionsDT = dtModel.transform(test);
		double accuracyDT = evaluateModel(predictionsDT, c14Label);
		
		System.out.println("Test Error Decision Tree = " + (1.0 - accuracyDT));

		// RandomForest Model
		RandomForestClassificationModel rfModel  = randomForestModel(training);
		Dataset<Row> predictionsRF = rfModel.transform(test);
		double accuracyRF = evaluateModel(predictionsRF, c14Label);
		System.out.println("Test Error Random Forest = " + (1.0 - accuracyRF));
		
		// Save the best model
		saveModel(rfModel, "rfModel", version);
		saveModel(dtModel, "dtModel", version);
		
	}
	
	/**
	 * Create decision tree model
	 * @param training Training dataset
	 * @return decision tree model
	 */
	private DecisionTreeClassificationModel decisionTreeModel(Dataset<Row> training) {

		DecisionTreeClassifier dt = new DecisionTreeClassifier()
				.setLabelCol(Column.INCOME_INDEX.colName())
				.setFeaturesCol(Column.FEATURES.colName())
				.setMaxBins(45);
		return dt.fit(training);
	}
	
	/**
	 * Create a random forest model
	 * @param training Training dataset
	 * @return random forest model
	 */
	private RandomForestClassificationModel randomForestModel(Dataset<Row> training) {

		RandomForestClassifier rf = new RandomForestClassifier()
				.setLabelCol(Column.INCOME_INDEX.colName())
				.setFeaturesCol(Column.FEATURES.colName())
				.setMaxBins(45);
		return rf.fit(training);
		
	}
	
	/**
	 * Evaluate model predictions
	 * @param predictions Prediction dataset
	 * @param c14Label IndexToLabel transformer
	 * @return Accuracy of the model
	 */
	private double evaluateModel(Dataset<Row> predictions, IndexToString c14Label) {
		
		predictions = c14Label.transform(predictions);

		// Select example rows to display.
		predictions.select(Column.PREDICTION.colName(), 
				Column.PREDICTION_LABEL.colName(), 
				Column.INCOME.colName(),  
				Column.AGE.colName(), 
				Column.WORKCLASS.colName(), 
				Column.FNLWGT.colName(), 
				Column.EDUCATION.colName(), 
				Column.EDUCATION_NUM.colName(), 
				Column.MARITAL_STATUS.colName(), 
				Column.OCCUPATION.colName(),
				Column.RELATIONSHIP.colName(), 
				Column.RACE.colName(), 
				Column.SEX.colName(), 
				Column.CAPITAL_GAIN.colName(), 
				Column.CAPITAL_LOSS.colName(), 
				Column.HOURS_PER_WEEK.colName(),
				Column.NATIVE_COUNTRY.colName())
		.show(false);

		// Select (prediction, true label) and compute test error.
		MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
				.setLabelCol(Column.INCOME_INDEX.colName())
				.setPredictionCol(Column.PREDICTION.colName())
				.setMetricName("accuracy");

		return evaluator.evaluate(predictions);
	}
	
	/**
	 * Rename dataset
	 * @param dataset Original dataset
	 * @return Renamed dataset
	 */
	private Dataset<Row> renameDataset(Dataset<Row> dataset) {
		

		return dataset.withColumn("_c0", dataset.col("_c0").cast("double"))
				.withColumn("_c2", dataset.col("_c2").cast("double"))
				.withColumn("_c4", dataset.col("_c4").cast("double"))
				.withColumn("_c10", dataset.col("_c10").cast("double"))
				.withColumn("_c11", dataset.col("_c11").cast("double"))
				.withColumn("_c12", dataset.col("_c12").cast("double"))
				.withColumnRenamed("_c0", Column.AGE.colName())
				.withColumnRenamed("_c1", Column.WORKCLASS.colName())
				.withColumnRenamed("_c2", Column.FNLWGT.colName())
				.withColumnRenamed("_c3", Column.EDUCATION.colName())
				.withColumnRenamed("_c4", Column.EDUCATION_NUM.colName())
				.withColumnRenamed("_c5", Column.MARITAL_STATUS.colName())
				.withColumnRenamed("_c6", Column.OCCUPATION.colName())
				.withColumnRenamed("_c7", Column.RELATIONSHIP.colName())
				.withColumnRenamed("_c8", Column.RACE.colName())
				.withColumnRenamed("_c9", Column.SEX.colName())
				.withColumnRenamed("_c10", Column.CAPITAL_GAIN.colName())
				.withColumnRenamed("_c11", Column.CAPITAL_LOSS.colName())
				.withColumnRenamed("_c12", Column.HOURS_PER_WEEK.colName())
				.withColumnRenamed("_c13", Column.NATIVE_COUNTRY.colName())
				.withColumnRenamed("_c14", Column.INCOME.colName());
	}
	
	/**
	 * Save model
	 * @param model Model to save
	 * @param name Name of the model
	 */
	private void saveModel(MLWritable model, String name, Long version) {


		String fullPath = formatPath(version).concat(name);
		log.debug("Full path to be saved: " + fullPath);

		try {
			model.save(fullPath);
		} catch (IOException e) {
			try {
				log.info("Removed directory {}", fullPath);
				model.write().overwrite().save(fullPath);
			} catch (IOException e1) {
				log.error("Error trying to save model {}", fullPath, e.getMessage());
			}
		}
		log.info("Model {} saved", fullPath);
	}
	
	private String formatPath(Long version) {
		
		String formatedPath = modelFolderPath;
		
		if (!formatedPath.endsWith("/")) {
			formatedPath = formatedPath.concat("/");
		}
		
		if (modelVersioned) {
			formatedPath = formatedPath.concat(version + "/");
		}
		
		return formatedPath;
	}
	
	@Override
	public long getVersion() {
		if (modelVersioned) {
			List<Long> lista = getAvailableVersions();
			lista.sort(Comparator.reverseOrder());
			if (lista.size() > 0)
				return lista.get(0);
			else
				return 0;
		} else {
			return 0;
		}
	}
	
	@Override
	public List<Long> getAvailableVersions() {
		Long[] array = {0l};
		if (modelVersioned) {
			File path = new File(modelFolderPath);
			List<Long> lista = new ArrayList<>();
			Arrays.asList(path.list()).forEach(f -> {
				try {
					Long l = Long.parseLong(f);
					lista.add(l);
				} catch (Exception e) {
					log.debug(e.getMessage());;
				}
			});
			return lista;
		} else {
			return Arrays.asList(array);
		}
	}

}
