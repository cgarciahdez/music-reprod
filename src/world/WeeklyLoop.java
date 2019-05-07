package world;

import java.util.Date;

public class WeeklyLoop extends Loop {
	/**
	 *  Importante: el cadaN del weekly loop se usa para el small loop adentro. Siempre serepite 1 semana sin falta. 
	 *  Se puede usar sin el small loop
	 */
	
	private Date endDate;
	private boolean rep;
	private SmallLoop internal;
	
	/**
	 * Constructor to be used when there is no end date
	 * @param name
	 * @param voiceFile
	 * @param added
	 * @param active
	 * @param cadaN
	 * @param start
	 * @param metric
	 */
	public WeeklyLoop(String name, String voiceFile, Date added,
			boolean active, int cadaN, Date start, Metric metric) {
		super(name, voiceFile, added, active, cadaN, start, metric);
		rep = true;
		Date end = new Date(start.getTime());
		end.setHours(23);
		end.setMinutes(59);
		endDate = end;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor to be used then there is an end date
	 * @param name
	 * @param voiceFile
	 * @param added
	 * @param active
	 * @param cadaN
	 * @param start
	 * @param metric
	 * @param end
	 */
	
	public WeeklyLoop(String name, String voiceFile, Date added,
			boolean active, int cadaN, Date start, Metric metric, Date end) {
		super(name, voiceFile, added, active, cadaN, start, metric);
		endDate = end;
		rep = true;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor to be used when there is no rep.
	 * @param name
	 * @param voiceFile
	 * @param added
	 * @param active
	 * @param start
	 */
	public WeeklyLoop(String name, String voiceFile, Date added,
			boolean active, Date start) {
		super(name, voiceFile, added, active, 1, start, Metric.WEEK);
		rep = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void schedule() {
		Date now = new Date();
		Date add = getStart();
//		while(add.before(now)){
//			add = new Date((now.getTime()+(Metric.WEEK.getMilis())));
//		}
		setStart(add);
		System.out.println("scheduled weekly: "+getStart());
		Reprod.timer.scheduleAtFixedRate(this, getStart(), Metric.WEEK.getMilis());
		
	}
	
	public void stopInternal() {
		if(rep &&internal!=null)
		{
			internal.cancel();
		}
	}

	@Override
	public void run() {
		System.out.println("running weekly");
		if(rep){
			System.out.println("Running weekly loop");
			Date now = new Date();
			internal= new SmallLoop(getName(), getVoiceFile(), now, true, getCadaN(), getStart(), getMetric());
			
			endDate.setYear(now.getYear());
			endDate.setMonth(now.getMonth());
			endDate.setDate(now.getDate());
			internal.setEndDate(endDate);
			
			
			internal.schedule();
		}else{
			play();
		}
		
	}

}
