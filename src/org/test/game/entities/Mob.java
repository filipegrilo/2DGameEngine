package org.test.game.entities;

import org.test.enums.Direction;
import org.test.level.Level;
import org.test.phisics.BoxCollider;
import org.test.phisics.Collider;

public abstract class Mob extends Entity{
	protected int speed;
	protected double health;
	
	protected String name;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	
	protected Collider collider;
	
	public Mob(Level level, String name, int x, int y, int speed, double health) {
		super(level);
		this.name = name;
		this.speed = speed;
		this.health = health;
		this.x = x;
		this.y = y;
		this.collider = new BoxCollider(x, y, 0, 0, 16, 16, level);
	}
	
	public void move(int xa, int ya){
		numSteps++;
		
		if(!collider.hasCollided(xa, ya)){
			if(ya < 0) movingDir = 0;
			else if(ya > 0) movingDir = 1;
			else if(xa < 0) movingDir = 2;
			else if(xa > 0) movingDir = 3;
			
			x += xa * speed;
			y += ya * speed;
			collider.tick(x, y);
		}
	}
	
	public String getName(){
		return name;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public Collider getCollider(){
		return collider;
	}
}
