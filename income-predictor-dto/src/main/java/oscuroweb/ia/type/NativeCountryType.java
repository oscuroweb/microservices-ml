package oscuroweb.ia.type;

public enum NativeCountryType {
	
	UNITED_STATES(1, "United-States"),
	CAMBODIA(2, "Cambodia"),
	ENGLAND(3, "England"),
	PUERTO_RICO(4, "Puerto-Rico"),
	CANADA(5, "Canada"),
	GERMANY(6, "Germany"),
	OUTLYING_US(7, "Outlying-US(Guam-USVI-etc)"),
	INDIA(8, "India"),
	JAPAN(9, "Japan"),
	GREECE(10, "Greece"),
	SOUTH(11, "South"),
	CHINA(12, "China"),
	CUBA(13, "Cuba"),
	IRAN(14, "Iran"),
	HONDURAS(15, "Honduras"),
	PHILIPPINES(16, "Philippines"),
	ITALY(17, "Italy"),
	POLAND(18, "Poland"),
	JAMAICA(19, "Jamaica"),
	VIETNAM(20, "Vietnam"),
	MEXICO(21, "Mexico"),
	PORTUGAL(22, "Portugal"),
	IRELAND(23, "Ireland"),
	FRANCE(24, "France"),
	DOMINICAN_REPUBLIC(25, "Dominican-Republic"),
	LAOS(26, "Laos"),
	ECUADOR(27, "Ecuador"),
	TAIWAN(28, "Taiwan"),
	HAITI(29, "Haiti"),
	COLUMBIA(30, "Columbia"),
	HUNGARY(31, "Hungary"),
	GUATEMALA(32, "Guatemala"),
	NICARAGUA(33, "Nicaragua"),
	SCOTLAND(34, "Scotland"),
	THAILAND(35, "Thailand"),
	YUGOSLAVIA(36, "Yugoslavia"),
	EL_SALVADOR(37, "El-Salvador"),
	TRINIDAD_TOBAGO(38, "Trinadad&Tobago"),
	PERU(39, "Peru"),
	HONG(40, "Hong"),
	HOLAND_NETHERLANDS(41, "Holand-Netherlands");
	
	private int cod;
	private String desc;
	
	private NativeCountryType(int cod, String desc) {
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
