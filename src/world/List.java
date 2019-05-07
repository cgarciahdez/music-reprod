package world;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class List implements Serializable {
	
	/**
	 * This is for no repeat. For repeat, create NewLoop, which you schedule in weeks. Copy of small loop.
	 * In run of new loop, create new SmallLoop with catch -> check end of day (endtime?) and if its over that time, stop self.
	 * Two new classes. But copies of smallloop?
	 * If no self cancel possible, schedule task foe 12am that stops other task.
	 **/
	
	
	private WeeklyLoop[] records; //Dom es 0
	private Date time;
	private Date added;
	private String voiceFile;
	private int cadaN;
	private Metric metric;
	private boolean[] days;
	private String name;
	private Date end;
	
	
	/**
	 * Constructor for non repeat
	 * @param time
	 * @param voiceFile
	 * @param name
	 * @param days
	 */
	public List(Date time, String voiceFile, String name, boolean[] days) {
		added = new Date();
		this.days = days;
		this.time = time;
		this.voiceFile = voiceFile;
		this.cadaN = cadaN;
		this.metric = metric;
		this.name = name;
		
		records = new WeeklyLoop[7];
		
		int j = 0;
		for (int i =0;i<days.length;i++){
			if (days[i]){
				Date today = new Date();
				today.setHours(time.getHours());
				today.setMinutes(time.getMinutes());
				today.setSeconds(time.getSeconds());
				Date toAdd = nextOfTheWeek(i+1, today);
				WeeklyLoop task = new WeeklyLoop(name+j, voiceFile, added, true,toAdd);
				records[i] = task;
				task.schedule();
				j++;
			}
		}
	}
	
	/**
	 * Constructor for list with repetion with no end
	 * @param time
	 * @param voiceFile
	 * @param cadaN
	 * @param metric
	 * @param name
	 * @param days
	 */
	public List(Date time, String voiceFile, int cadaN, Metric metric, String name, boolean[] days) {
		added = new Date();
		this.days = days;
		this.time = time;
		this.voiceFile = voiceFile;
		this.cadaN = cadaN;
		this.metric = metric;
		this.name = name;
		
		records = new WeeklyLoop[7];
		
		int j = 0;
		for (int i =0;i<days.length;i++){
			if (days[i]){
				Date today = new Date();
				today.setHours(time.getHours());
				today.setMinutes(time.getMinutes());
				today.setSeconds(time.getSeconds());
				Date toAdd = nextOfTheWeek(i+1, today);
				WeeklyLoop task = new WeeklyLoop(name+j, voiceFile, added, true,cadaN,toAdd,metric);
				records[i] = task;
				task.schedule();
				j++;
			}
		}
	}
	
	/**
	 * Constructor for repeat with end date
	 * @param time
	 * @param voiceFile
	 * @param cadaN
	 * @param metric
	 * @param name
	 * @param days
	 */
	public List(Date time, String voiceFile, int cadaN, Metric metric, String name, boolean[] days, Date end) {
		added = new Date();
		this.days = days;
		this.time = time;
		this.voiceFile = voiceFile;
		this.cadaN = cadaN;
		this.metric = metric;
		this.name = name;
		this.end = end;
		
		records = new WeeklyLoop[7];
		
		int j = 0;
		for (int i =0;i<days.length;i++){
			if (days[i]){
				Date today = new Date();
				today.setHours(time.getHours());
				today.setMinutes(time.getMinutes());
				today.setSeconds(time.getSeconds());
				Date toAdd = nextOfTheWeek(i+1, today);
				WeeklyLoop task = new WeeklyLoop(name+j, voiceFile, added, true,cadaN,toAdd,metric,end);
				records[i] = task;
				task.schedule();
				j++;
			}
		}
	}
	
	public void schedule(){
		for (WeeklyLoop record:records){
			if (record!=null){
				record.schedule();
			}
		}
	}
	
	public void changeDays(boolean[] newDays){
		//revisar que cambió. Si se quitó, cancelar task y volver null en array. Si se añadió, añadir!
		days = newDays;
		
	}
	
	public void changeRepeat(){
		
	}
	
	public void cancel(){
		for (WeeklyLoop record: records){
			if(record!=null){
				record.cancel();
				record.setActive(false);
				System.out.println("canceling record: "+record.getName());
			}
		}
	}
	
	
	
	private Date nextOfTheWeek(int dayOfWeek, Date date){
		Calendar deit = Calendar.getInstance();
		Date now = new Date();
		if(deit.get(Calendar.DAY_OF_WEEK)==dayOfWeek&&date.after(now)){
			return date;
		}
        int diff = dayOfWeek - deit.get(Calendar.DAY_OF_WEEK);
        
        diff = diff>0?diff:diff+7;
        deit.add(Calendar.DAY_OF_MONTH, diff);
        
        Date d = deit.getTime();
        
        return d;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getVoiceFile() {
		return voiceFile;
	}

	public void setVoiceFile(String voiceFile) {
		this.voiceFile = voiceFile;
	}

	public int getCadaN() {
		return cadaN;
	}

	public void setCadaN(int cadaN) {
		this.cadaN = cadaN;
	}

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WeeklyLoop[] getRecords() {
		return records;
	}

	public Date getAdded() {
		return added;
	}

	public boolean[] getDays() {
		return days;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	
	
	
	

}
