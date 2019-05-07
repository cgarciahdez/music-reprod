package world;

import java.util.Date;

public class SmallLoop extends Loop {
	
	private Date endDate;
	
	

	public SmallLoop(String name, String voiceFile, Date added, boolean active,
			int cadaN, Date start, Metric metric) {
		super(name, voiceFile, added, active, cadaN, start, metric);
		// TODO Auto-generated constructor stub
		System.out.println("made a small");
	}

	@Override
	public void run() {
		if(!this.isActive()){
			this.cancel();
		} else {			
			if(endDate!=null){
				Date now = new Date();
				if (now.after(endDate)){
					this.cancel();
				}else{
					play();
				}
			}
			else {
				play();
			}
		}
	}
	
	public void setEndDate(Date end){
		endDate = end;
	}

	@Override
	public void schedule() {
//		Date now = new Date();
//		Date add = getStart();
//		while(add.before(now)){
//			add = new Date((now.getTime()+(getCadaN()*getMetric().getMilis())));
//		}
//		setStart(add);
		System.out.println("scheduled: "+getStart());
		Reprod.timer.schedule(this, getStart(), getCadaN()*getMetric().getMilis());
	}
	
	

	

}
