package world;

import java.util.Date;
import java.util.TimerTask;

public abstract class Record extends TimerTask {
	private String name;
	private String voiceFile;
	private Date added;
	private boolean active;
	
	public void play(){
		System.out.println("play");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVoiceFile() {
		return voiceFile;
	}
	public void setVoiceFile(String voiceFile) {
		this.voiceFile = voiceFile;
	}
	public Date getAdded() {
		return added;
	}
	public void setAdded(Date added) {
		this.added = added;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
