package oscuroweb.ia.type;

public enum RelationshipType {
	
	WIFE(1, "Wife"),
	OWN_CHILD(2, "Own-child"),
	HUSBAND(3, "Husband"),
	NOT_IN_FAMILY(4, "Not-in-family"),
	OTHER_RELATIVE(5, "Other-relative"),
	UNMARRIED(6, "Unmarried");
	
	private int cod;
	private String desc;
	
	private RelationshipType(int cod, String desc) {
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
