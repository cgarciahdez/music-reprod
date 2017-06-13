package world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TreeMap;


public class Reprod implements Serializable {
	
	public final static String SEP = File.separator;
	public final static String PATH = "guardadito";
	
	private static HashMap<String,Record> records;
	static Timer timer;
	
	public Reprod() throws FileNotFoundException, ClassNotFoundException, IOException{
		records = new HashMap<String,Record>();
		timer = new Timer();
		File pers = new File("persistance.txt");
		if(pers.exists()){
			load();
		}
		
	}
	
	public HashMap<String,Record> getRecords() {
		return records;
	}
	

	public boolean eraseTask(String name){
		Record r = records.remove(name);
		if (r == null) return false;
		r.cancel();
		return true;
	}

	public boolean scheduleOnce(String name, String voiceFile, Date added, Date date){
		if (records.containsKey(name)||records.containsKey(name+"0")) return false;
		Record task = new Once(name, voiceFile, added, true, date, true);
		records.put(name,task);
		task.schedule();
		return true;
	}
	
	public boolean scheduleSmallLoop(String name, String voiceFile, Date added, int cadaN, Date start, Metric metric){
		if (records.containsKey(name)||records.containsKey(name+"0")) return false;
		Record task = new SmallLoop(name, voiceFile, added, true, cadaN, start, metric);
		records.put(name,task);
		task.schedule();
		return true;
	}
	
	public boolean scheduleBigLoop(String name, String voiceFile, Date added, int cadaN, Date start, Metric metric){
		if (records.containsKey(name)||records.containsKey(name+"0")) return false;
		Record task = new BigLoop(name, voiceFile, added, true, cadaN, start, metric);
		records.put(name,task);
		task.schedule();
		return true;
	}
	
	public boolean scheduleList(String name, String voiceFile, Date added, Date date, boolean[] dias){
		if (records.containsKey(name)||records.containsKey(name+"0")) return false;
		int j = 0;
		
		for (int i =0;i<dias.length;i++){
			if (dias[i]){
				Date toAdd = nextOfTheWeek(i+1, date);
				Record task = new SmallLoop(name+j, voiceFile, added, true, 1, toAdd, Metric.WEEK);
				records.put(name+j, task);
				task.schedule();
				j++;
			}
		}
		
		return true;
	}
	
	public void cancelTask(String name){
		Record task = records.get(name);
		task.cancel();
		task.setActive(false);
	}
	
	private Date nextOfTheWeek(int dayOfWeek, Date date){
		Calendar deit = Calendar.getInstance();
		deit.setTime(date);
		Date now = new Date();
		if(deit.DAY_OF_WEEK==dayOfWeek&&date.after(now)){
			return date;
		}
        int diff = dayOfWeek - deit.get(Calendar.DAY_OF_WEEK);
        
        diff = diff>0?diff:diff+7;
        deit.add(Calendar.DAY_OF_MONTH, diff);
        
        Date d = deit.getTime();
        
        return d;
	}
	
	public static void rescheduleBigLoop(BigLoop bl){
		Record task = new BigLoop(bl);
//		records.put(bl.getName(),task);
		task.schedule();
		
	}
	
	public void salvar( ) throws Exception
	{
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("persistance.txt"));
		oos.writeObject( records );
		oos.close( );

//		Path path = FileSystems.getDefault().getPath(PATH+SEP+"sesion"+SEP+".log");
//		File pw = new File(PATH+SEP+"sesion"+SEP+".log");
//		if(pw.exists()){
//			Files.setAttribute(path, "dos:hidden", false);
//		}
//
//		oos = new ObjectOutputStream(
//				new FileOutputStream(PATH+SEP+"sesion"+SEP+".log"));
//		oos.writeObject(password );
//		oos.close( );


//		Files.setAttribute(path, "dos:hidden", true);
	}
	
	public void load() throws FileNotFoundException, IOException, ClassNotFoundException{
		File salvar = new File("persistance.txt");
		if (salvar.exists()){

			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream( salvar ) );
			records = ( HashMap<String, Record> )ois.readObject( );
			ois.close( );
			
			System.out.println("yes");
			
			for (Record record : records.values()) {
				record.schedule();
			}

		}
		else{
//			records
			System.out.println("no");	
		}
	}
	
}
