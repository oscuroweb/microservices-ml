package oscuroweb.ia.dto;

public enum Column {

	AGE	("age"),
	WORKCLASS	("workclass"),
	FNLWGT	("fnlwgt"),
	EDUCATION 	("education"),
	EDUCATION_NUM	("educationNum"),
	MARITAL_STATUS	("maritalStatus"),
	OCCUPATION	("occupation"),
	RELATIONSHIP		("relationship"),
	RACE		("race"),
	SEX	("sex"),
	CAPITAL_GAIN	("capitalGain"),
	CAPITAL_LOSS	("capitalLoss"),
	HOURS_PER_WEEK	("hoursPerWeek"),
	NATIVE_COUNTRY	("nativeCountry"),
	INCOME	("income"),
	WORKCLASS_INDEX	("workclass_index"),
	EDUCATION_INDEX 	("education_index"),
	MARITAL_STATUS_INDEX	("maritalStatus_index"),
	OCCUPATION_INDEX	("occupation_index"),
	RELATIONSHIP_INDEX		("relationship_index"),
	RACE_INDEX		("race_index"),
	SEX_INDEX	("sex_index"),
	NATIVE_COUNTRY_INDEX	("nativeCountry_index"),
	INCOME_INDEX	("income_index"),
	FEATURES		("featrues"),
	PREDICTION	("prediction"),
	PREDICTION_LABEL	("predictionLabel");
	
	private final String colName;
	
	private Column(String colName) {
		this.colName = colName;
	}
	
	public final String colName() {
		return this.colName;
	}
	
}
