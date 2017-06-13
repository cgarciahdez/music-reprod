package world;

public enum Metric {
	SECOND (1000, "Segundo(s)"),
	MINUTE (SECOND.getMilis()*60, "Minuto(s)"),
	HOUR (MINUTE.getMilis()*60, "Hora(s)"),
	DAY (HOUR.getMilis()*24, "Dia(s)"),
	WEEK (DAY.getMilis()*7, "Semana(s)"),
	MES(-1, "Mes(es)"),
	ANHO (-1, "Año(s)");
	
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
	
	@Override
	public String toString() {
		return name;
	}
}
