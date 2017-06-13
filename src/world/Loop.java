package world;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Loop extends Record {
	
	public Loop(String name, String voiceFile, Date added, boolean active,
			int cadaN, Date start, Metric metric) {
		super(name, voiceFile, added, active);
		this.cadaN = cadaN;
		this.start = start;
		this.metric = metric;
	}
	
	
	
	
	public int getCadaN() {
		return cadaN;
	}

	public Date getStart() {
		return start;
	}

	public Metric getMetric() {
		return metric;
	}
	
	public void setStart(Date start){
		this.start = start;
	}




	private int cadaN;
	private Date start;
	private Metric metric;
	
	@SuppressWarnings("deprecation")
	public void changeStart(){
		if (metric.equals(Metric.ANHO)){
			int newY = start.getYear()+cadaN;
//			start = new Date(newY, start.getMonth(), start.getDate());
			start.setYear(newY);
		}
		else if(metric.equals(Metric.MES)){
			int newM= (start.getMonth()+cadaN)%12;
			start.setMonth(newM);
		}
	}
	
	
	
}
