package oscuroweb.ia.type;

public enum RaceType {
	
	WHITE(1, "White"),
	ASIAN_PAC_ISLANDER(2, "Asian-Pac-Islander"),
	AMER_INDIAN_ESKIMO(3, "Amer-Indian-Eskimo"),
	BLACK(4, "Black"),
	OTHER(5, "Other");
	
	private int cod;
	private String desc;
	
	private RaceType(int cod, String desc) {
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
