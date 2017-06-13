package world;

import java.util.Date;

public class SmallLoop extends Loop {
	
	

	public SmallLoop(String name, String voiceFile, Date added, boolean active,
			int cadaN, Date start, Metric metric) {
		super(name, voiceFile, added, active, cadaN, start, metric);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		play();
		
	}

	@Override
	public void schedule() {
		Date now = new Date();
		Date add = getStart();
		while(add.before(now)){
			add = new Date((now.getTime()+(getCadaN()*getMetric().getMilis())));
		}
		setStart(add);
		System.out.println("scheduled: "+getStart());
		Reprod.timer.schedule(this, getStart(), getCadaN()*getMetric().getMilis());
	}
	
	

	

}
