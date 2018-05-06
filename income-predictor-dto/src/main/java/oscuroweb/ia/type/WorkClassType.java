package oscuroweb.ia.type;

public enum WorkClassType {

	PRIVATE(1, "Private"),
	SELF_EMP_NOT_INC(2, "Self-emp-not-inc"),
	SELF_EMP_INC(3, "Self-emp-inc"),
	FEDERAL_GOV(4, "Federal-gov"),
	LOCAL_GOV(5, "Local-gov"),
	STATE_GOV(6, "State-gov"),
	WITHOUT_PAY(7, "Without-pay"),
	NEVER_WORKED(8, "Never-worked");
	
	private int cod;
	private String desc;
	
	private WorkClassType(int cod, String desc) {
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
