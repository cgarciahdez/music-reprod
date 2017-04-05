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

	

}
