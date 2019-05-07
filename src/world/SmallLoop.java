package world;

import java.util.Date;

public class SmallLoop extends Loop {
	
	private Date endDate;
	
	

	public SmallLoop(String name, String voiceFile, Date added, boolean active,
			int cadaN, Date start, Metric metric) {
		super(name, voiceFile, added, active, cadaN, start, metric);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		System.out.println("Running small loop. End date: "+endDate);
		if(endDate!=null){
			Date now = new Date();
			if (now.after(endDate)){
				System.out.println("what");
				this.cancel();
			}else{
				play();
			}
		}
		else {
			play();
		}
		
		
		
	}
	
	public void setEndDate(Date end){
		endDate = end;
	}

	@Override
	public void schedule() {
		Date now = getAdded();
		Date add = getStart();
		while(add.before(now)){
			add = new Date((add.getTime()+(getCadaN()*getMetric().getMilis())));
		}
		setStart(add);
		System.out.println("scheduled: "+getStart());
		Reprod.timer.schedule(this, getStart(), getCadaN()*getMetric().getMilis());
	}
	
	

	

}
