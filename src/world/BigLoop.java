package world;

import java.util.Date;

public class BigLoop extends Loop {
	

	public BigLoop(String name, String voiceFile, Date added, boolean active,
			int cadaN, Date start, Metric metric) {
		super(name, voiceFile, added, active, cadaN, start, metric);
	}
	
	public BigLoop(BigLoop bl) {
		super(bl.getName(),bl.getVoiceFile(),bl.getAdded(),bl.isActive(), bl.getCadaN(), bl.getStart(), bl.getMetric());
	}

	@Override
	public void run() {
		play();
		changeStart();
		System.out.println(getStart());
		Reprod.rescheduleBigLoop(this);
		
		System.out.println(this.scheduledExecutionTime());
		//Reprod.timer;
		
	}

	@Override
	public void schedule() {
		Date now = new Date();
		Date add = getStart();
		while(add.before(now)){
			add = new Date((now.getTime()+(getCadaN()*getMetric().getMilis())));
		}
		setStart(add);
		Reprod.timer.schedule(this, getStart());		
	}

}
