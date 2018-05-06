package oscuroweb.ia.type;


public enum SexType {
	
	FEMALE(1, "Female"), 
	MALE(2, "Male");
	
	private final int cod;
	
	private final String desc;
	
	private SexType(int cod, String desc) {
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
