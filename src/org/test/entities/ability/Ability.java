package org.test.entities.ability;

import org.test.gfx.Screen;
import org.test.sound.Sound;
import org.test.time.Time;

public abstract class Ability {
	protected final int MESSAGE_ACTIVE_DURATION = 1;
	
	protected int cooldown;
	protected int range;
	protected int speed;
	protected boolean ready = true;
	protected boolean active = false;
	protected Time time;
	protected Time messageTime;
	protected String name;
	private String message;
	protected boolean renderMessage;
	protected Sound sound;
	
	public Ability(String name, int cooldown, int range, int speed, Sound sound){
		this.name = name;
		this.cooldown = cooldown;
		this.range = range;
		this.speed = speed;
		this.message = name + " in cooldown";
		this.sound = sound;
	}
	
	public abstract void tick();
	public abstract void render(Screen screen);
	protected abstract boolean inRange();
	protected abstract void move();
	
	public boolean isReady(){
		return ready;
	}
	
	protected void updateTimers(){
		if(time != null){
			time.tick();
			if(time.isDone()){
				ready = true;
				time = null;
			}
		}
		
		if(messageTime != null){
			messageTime.tick();
			if(messageTime.isDone()){
				renderMessage = false;
				messageTime = null;
			}
		}
	}
	
	public String getMessage(){
		return message;
	}
}
