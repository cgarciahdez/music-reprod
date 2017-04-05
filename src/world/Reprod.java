package world;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class Reprod {

	
	private static HashMap<String,Record> records; 
	static Timer timer;
	
	public Reprod(){
		records = new HashMap<String,Record>();
		timer = new Timer();
	}
	
	public HashMap<String,Record> getRecords() {
		return records;
	}
	

	public void eraseTask(String name){
		Record r = records.remove(name);
		r.cancel();
		
	}

	public boolean scheduleOnce(String name, String voiceFile, Date added, Date date){
		if (records.containsKey(name)) return false;
		Record task = new Once(name, voiceFile, added, true, date, true);
		records.put(name,task);
		timer.schedule(task, date);
		return true;
	}
	
	public boolean scheduleSmallLoop(String name, String voiceFile, Date added, int cadaN, Date start, Metric metric){
		if (records.containsKey(name)) return false;
		Record task = new SmallLoop(name, voiceFile, added, true, cadaN, start, metric);
		records.put(name,task);
		timer.scheduleAtFixedRate(task, start, cadaN*metric.getMilis());
		return true;
	}
	
	public boolean scheduleBigLoop(String name, String voiceFile, Date added, int cadaN, Date start, Metric metric){
		if (records.containsKey(name)) return false;
		Record task = new BigLoop(name, voiceFile, added, true, cadaN, start, metric);
		records.put(name,task);
		timer.schedule(task, start);
		return true;
	}
	
	public static void rescheduleBigLoop(BigLoop bl){
		Record task = new BigLoop(bl);
		records.put(bl.getName(),task);
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
