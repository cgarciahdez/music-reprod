package world;

import java.util.Date;

public class Once extends Record {
	
	public Once(String name, String voiceFile, Date added, boolean active,
			Date date, boolean done) {
		super(name, voiceFile, added, active);
		this.date = date;
		this.done = done;
	}


	private Date date;
	private boolean done;
	
	
	

	public Date getDate() {
		return date;
	}



	public boolean isDone() {
		return done;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		play();
		
	}



	@Override
	public void schedule() {
		Date now = new Date();
		if(now.before(date)){
			Reprod.timer.schedule(this, date);
		}
		
	}

}
