package world;

public enum Metric {
	SECOND (1000, "Segundo"),
	MINUTE (SECOND.getMilis()*60, "Minuto"),
	HOUR (MINUTE.getMilis()*60, "Hora"),
	DAY (HOUR.getMilis()*24, "Dia"),
	WEEK (DAY.getMilis()*7, "Semana"),
	MES(-1, "Mes"),
	ANHO (-1, "Año");
	
	private int nMilis;
	private String name;
	
	Metric(int nMilis, String name){
		this.nMilis=nMilis;
		this.name = name;
	}
	
	public int getMilis(){
		return nMilis;
	}
	
	public String getName(){
		return name;
	}
}
