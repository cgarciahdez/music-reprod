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
	public final static String PATH = "C:"+SEP+"Messager";
//	public final static String PATH = SEP+"Users"+SEP+"camilagarciahernandez"+SEP+"Documents"+SEP+"Personal"+SEP+"Proyectos"+SEP+"Messager";


	private static HashMap<String,Record> records;
	private static HashMap<String, List> lists;
	static Timer timer;

	public Reprod() throws FileNotFoundException, ClassNotFoundException, IOException{
		records = new HashMap<String,Record>();
		lists = new HashMap<String, List>();
		timer = new Timer();
		File directorio = new File(PATH);
		if (!directorio.exists()) {
			System.out.println(PATH);
			directorio.mkdir();
		}
		File voz = new File(PATH+SEP+"sesiones");
		if(!voz.exists()){
			voz.mkdir();
		}
		File pers = new File(PATH+SEP+"persistance.txt");
		if(pers.exists()){
			load();
		}

	}

	public HashMap<String,Record> getRecords() {
		return records;
	}

	public HashMap<String, List> getLists(){
		return lists;
	}


	public boolean eraseTask(String name){
		Record r = records.remove(name);
		if (r == null) return false;
		r.cancel();
		return true;
	}

	public boolean eraseList(String name){
		List r = lists.remove(name);
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

	/**
	 * No repeat
	 * @param name
	 * @param voiceFile
	 * @param time
	 * @param days
	 * @return
	 */
	public boolean scheduleListNoRep(String name, String voiceFile, Date time, boolean[] days){
		if(lists.containsKey(name)) return false;
		List list = new List(time, voiceFile, name, days);
		lists.put(name, list);
		return true;
	}

	/**
	 * Repeat with no end
	 * @return
	 */
	public boolean scheduleListRep(String name, String voiceFile, Date time, boolean[] days, int cadaN, Metric metric){
		if(lists.containsKey(name)) return false;
		List list = new List(time, voiceFile, cadaN, metric, name, days);
		lists.put(name, list);
		return true;
	}

	/**
	 * Repeat with end
	 * @return
	 */
	public boolean scheduleListRep(String name, String voiceFile, Date time, boolean[] days, int cadaN, Metric metric, Date end){
		if(lists.containsKey(name)) return false;
		List list = new List(time, voiceFile, cadaN, metric, name, days, end);
		lists.put(name, list);
		return true;
	}

	public void cancelTask(String name){
		Record task = records.get(name);
		task.cancel();
		task.setActive(false);
	}
	
	public List getListByName(String name){
		return lists.get(name);
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
				new FileOutputStream(PATH+SEP+"persistance.txt"));
		oos.writeObject( lists );
		oos.close( );

	}
	
	public void salvar( String Filename) throws Exception
	{
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(Filename));
		oos.writeObject( lists );
		oos.close( );

	}

	public void load() throws FileNotFoundException, IOException, ClassNotFoundException{
		File salvar = new File(PATH+SEP+"persistance.txt");
		if (salvar.exists()){

			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream( salvar ) );
			lists = ( HashMap<String, List> )ois.readObject( );
			ois.close( );

			System.out.println("yes");

			for (List list : lists.values()) {
				list.schedule();
			}

		}
		else{
			//			records
			System.out.println("no");	
		}
	}
	
	public void load(String Filename) throws FileNotFoundException, IOException, ClassNotFoundException{
		File salvar = new File(Filename);
		System.out.println(salvar);
		if (salvar.exists()){

			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream( salvar ) );
//			lists = new HashMap<String, List>();
			lists = ( HashMap<String, List> )ois.readObject( );
			ois.close( );

//			System.out.println("yes");

			for (List list : lists.values()) {
				list.schedule();
			}

		}
		else{
			//			records
			System.out.println("no");	
		}
	}

}
