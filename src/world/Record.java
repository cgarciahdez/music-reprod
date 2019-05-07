package world;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public abstract class Record extends TimerTask implements Serializable {

	private String name;
	private String voiceFile;
	private Date added;
	private boolean active;

	public Record(String name, String voiceFile, Date added, boolean active) {
		super();
		this.name = name;
		this.voiceFile = voiceFile;
		this.added = added;
		this.active = active;
	}

	public void play(){
		System.out.println("play");
		System.out.println(name);
		System.out.println(this.scheduledExecutionTime());
		try{

			if(voiceFile.endsWith(".mp3")){
				System.out.println("mp3");
				System.out.println(voiceFile);
				BufferedInputStream bs = new BufferedInputStream(new FileInputStream(voiceFile));
				Player p = new Player(bs);
				p.play();
			}
			else{

				InputStream in = new FileInputStream(voiceFile);
				AudioStream as = new AudioStream(in);
				AudioPlayer.player.start(as);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Hubo un problema reproduciendo el archivo de sonido. Asegúrese que el archivo que escogió es un archivo de sonido váildo.");
		}
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

	public abstract void schedule();


}
