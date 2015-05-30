package org.test.phisics;

import org.test.level.Level;

public abstract class Collider<T> implements Cloneable{
	protected int x, y;
	protected int xOffset, yOffset;
	protected int width, height;
	protected Level level;
	protected boolean active;
	protected T type;
	protected T colisionObject;
	
	public Collider(int x, int y, int xOffset, int yOffset, Level level, boolean active, T type){
		this.x = x + xOffset;
		this.y = y + yOffset;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.level = level;
		this.active = active;
		this.type = type;
	}
	
	public abstract void tick(int x, int y);
	public abstract boolean hasCollided(int xa, int ya, double speed);
	public abstract boolean hasCollided(int xa, int ya);
	
	public void setX(int x){
		this.x = x + xOffset;
	}
	
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y + yOffset;
	}
	
	public int getY(){
		return y;
	}
	
	public void setType(T type){
		this.type = type;
	}
	
	public T getColisionObject(){
		return colisionObject;
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
