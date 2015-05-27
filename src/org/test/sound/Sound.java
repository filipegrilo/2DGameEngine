package org.test.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound implements Cloneable{
	private Clip audioClip;
	private boolean firstPlay = true;
	public static boolean soundActive = false;
	
	public static Sound background = new Sound("/sound/Background.wav", -10.0f);
	
	public static Sound walking = new Sound("/sound/Walking.wav", 6.0f);
	public static Sound swimming = new Sound("/sound/Swimming.wav", 6.0f);
	
	public static Sound fireball = new Sound("/sound/Fireball.wav", -2.0f);
	public static Sound dash = new Sound("/sound/Dash.wav", 6.0f);
	
	public static Sound hurt = new Sound("/sound/Hurt.wav", 6.0f);
	
	private Sound(String path){
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(path));
			audioClip = AudioSystem.getClip();
			audioClip.open(audioInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Sound(String path, float volume){
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(path));
			audioClip = AudioSystem.getClip();
			audioClip.open(audioInputStream);
			this.setVolume(volume);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playLoop(){
		audioClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void play(){
		if(!soundActive) return;
		
		if(firstPlay){
			firstPlay = false;
			audioClip.start();
		}else{
			if(!audioClip.isRunning()){
				audioClip.loop(1);;
			}
		}
	}
	
	public void stop(){
		audioClip.stop();
		audioClip.flush();
	}
	
	public void setVolume(float volume){
		FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(volume);
	}
	
	public boolean isActive(){
		return audioClip.isActive();
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
