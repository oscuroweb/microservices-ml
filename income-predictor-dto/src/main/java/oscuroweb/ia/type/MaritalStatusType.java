package oscuroweb.ia.type;

public enum MaritalStatusType {

	MARRIED_CIV_SPOUSE(1, "Married-civ-spouse"),
	DIVORCED(2, "Divorced"),
	NEVER_MARRIED(3, "Never-married"),
	SEPARATED(4, "Separated"),
	WIDOWED(5, "Widowed"),
	MARRIED_SPOUSE_ABSENT(6, "Married-spouse-absent"),
	MARRIED_AF_SPOUSE(7, "Married-AF-spouse");
	
	private int cod;
	private String desc;
	
	private MaritalStatusType(int cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}
	
	public int cod() {
		return cod;
	}
	
	public String desc() {
		return desc;
	}
	
}
