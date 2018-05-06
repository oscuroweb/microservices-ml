package oscuroweb.ia.type;

public enum OccupationType {

	TECH_SUPPORT(1, "Tech-support"),
	CRAFT_REPAIR(2, "Craft-repair"),
	OTHER_SERVICE(3, "Other-service"),
	SALES(4, "Sales"),
	EXEC_MANAGERIAL(5, "Exec-managerial"),
	PROF_SPECIALITY(6, "Prof-specialty"),
	HANDLERS_CLEANERS(7, "Handlers-cleaners"),
	MACHINE_OP_INSPECT(8, "Machine-op-inspct"),
	ADM_CLERICAL(9, "Adm-clerical"),
	FARMING_FISHING(10, "Farming-fishing"),
	TRANSPORT_MOVING(11, "Transport-moving"),
	PRIV_HOUSE_SERV(12, "Priv-house-serv"),
	PROTECTIVE_SERV(13, "Protective-serv"),
	ARMED_FORCES(14, "Armed-Forces");	
	
	private int cod;
	private String desc;
	
	private OccupationType(int cod, String desc) {
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
