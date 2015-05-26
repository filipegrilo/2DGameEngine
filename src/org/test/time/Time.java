package org.test.time;

public class Time {
	private double duration;
	private long curTime;
	private long lastTime;
	private long target;
	private boolean done;
	
	public Time(double duration){
		this.duration = 1000 * duration;
		this.curTime = System.currentTimeMillis();
		this.lastTime = System.currentTimeMillis();
		this.target = (long) this.duration;
		this.done = false;
	}
	
	public void tick(){
		curTime = System.currentTimeMillis();
		
		if(!done){
			target -= curTime - lastTime;
			lastTime = curTime;
			
			if(target <= 0){
				done = true;
			}
		}
	}
	
	public double getDuration(){
		return duration;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public void reset(){
		done = false;
		target = (long)duration;
	}
	
	public double remaining(){
		return (double) (target / 1000);
	}
}
