package org.test.phisics;

import org.test.level.Level;

public abstract class Collider {
	protected int x, y;
	protected int xOffset, yOffset;
	protected Level level;
	protected boolean active;
	
	public Collider(int x, int y, int xOffset, int yOffset, Level level, boolean active){
		this.x = x;
		this.y = y;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.level = level;
		this.active = active;
	}
	
	public abstract void tick(int x, int y);
	public abstract boolean hasCollided(int xa, int ya);
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return y;
	}
}
