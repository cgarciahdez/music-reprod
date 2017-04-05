package world;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Reprod {
	
	private static ArrayList<Record> records; 
	static Timer timer;
	
	public Reprod(){
		records = new ArrayList<Record>();
		timer = new Timer();
	}
	
	public ArrayList<Record> getRecords() {
		return records;
	}

	public void scheduleOnce(String name, String voiceFile, Date added, Date date){
		Record task = new Once(name, voiceFile, added, true, date, true);
		records.add(task);
		timer.schedule(task, date);
	}
	
	public void scheduleSmallLoop(String name, String voiceFile, Date added, int cadaN, Date start, Metric metric){
		Record task = new SmallLoop(name, voiceFile, added, true, cadaN, start, metric);
		records.add(task);
		timer.scheduleAtFixedRate(task, start, cadaN*metric.getMilis());
	}
	
	public void scheduleBigLoop(String name, String voiceFile, Date added, int cadaN, Date start, Metric metric){
		Record task = new BigLoop(name, voiceFile, added, true, cadaN, start, metric);
		records.add(task);
		timer.schedule(task, start);
	}
	
	public static void rescheduleBigLoop(BigLoop bl){
		System.out.println(records.remove(bl));
		Record task = new BigLoop(bl);
		records.add(task);
		timer.schedule(task, bl.getStart());
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Reprod r = new Reprod();
		Date now = new Date();
		Date play = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), (now.getSeconds()+10)/60==1?now.getMinutes()+1:now.getMinutes(), (now.getSeconds()+10)%60);
//		r.scheduleOnce("test", "holee",now ,play );
		now= new Date();
//		r.scheduleSmallLoop("small", "jeje", now, 10, play, Metric.SECOND);
		play.setYear(play.getYear());
		play.setMinutes(play.getMinutes());
		r.scheduleBigLoop("big", "juju", now, 1, play, Metric.ANHO);
		System.out.println(r.getRecords().toString());
		System.out.println(new Date(r.getRecords().get(0).scheduledExecutionTime()));
		Thread.sleep(10000);
		System.out.println(new Date(r.getRecords().get(0).scheduledExecutionTime()));
		
	}
}
