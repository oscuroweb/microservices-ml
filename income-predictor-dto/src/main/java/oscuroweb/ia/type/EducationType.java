package oscuroweb.ia.type;

public enum EducationType {

	BACHELORS(1, "Bachelors", 13d),
	SOME_COLLEGE(2, "Some-college", 10d),
	G11_TH(3, "11th", 7d),
	HS_GRAD(4, "HS-grad", 9d),
	PROF_SCHOOL(5, "Prof-school", 15d),
	ASSOC_ACDM(6, "Assoc-acdm", 12d),
	ASSOC_VOC(7, "Assoc-voc", 11d),
	G9_TH(8, "9th", 5d),
	G7_8_TH(9, "7th-8th", 4d),
	G12_TH(10, "12th", 8d),
	MASTER(11, "Masters", 14d),
	G1_4_TH(12, "1st-4th", 2d),
	G10_TH(13, "10th", 6d),
	DOCTORATE(14, "Doctorate", 16d),
	G5_6_TH(15, "5th-6th", 3d),
	PREESCHOOL(16, "Preschool", 1d);
	
	private int cod;
	private String desc;
	private Double num;
	
	private EducationType(int cod, String desc, Double num) {
		this.cod = cod;
		this.desc = desc;
		this.num = num;
	}
	
	public int cod() {
		return cod;
	}
	
	public String desc() {
		return desc;
	}
	
	public Double num() {
		return num;
	}
	
	
}
