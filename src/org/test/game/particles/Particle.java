package org.test.game.particles;

import org.test.enums.Direction;
import org.test.game.Game;
import org.test.gfx.Colors;
import org.test.gfx.Screen;
import org.test.phisics.BoxCollider;

public abstract class Particle implements Cloneable{
	protected int x;
	protected int y;
	private int spawnX;
	private int spawnY;
	protected int color;
	protected int tileIndex;
	protected Direction direction;
	protected boolean active;
	protected BoxCollider collider;
	
	public static Particle fireBall = new BasicParticle(new int[]{0 + 7 * 32}, new int[]{Colors.get(-1, 500, 531, 540),
																						 Colors.get(-1, 531, 540, 500),
																						 Colors.get(-1, 540, 500, 531)}, 
																						 0.1);
	
	public static Particle dash = new BasicParticle(new int[]{1 + 7 * 32}, new int[]{Colors.get(-1, 000, 333, 555)}, 0);
	
	public Particle(int tileIndex,int color, boolean active){
		this.color = color;
		this.tileIndex = tileIndex;
		this.active = active;
	}
	
	public abstract void tick();
	public abstract void render(Screen screen);
	
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
	
	public void setSpawn(int x, int y){
		this.spawnX = x;
		this.spawnY = y;
		this.x = x;
		this.y = y;
		this.collider = new BoxCollider(x, y, 8, 8, Game.level);
	}
	
	public int getSpawnX(){
		return spawnX;
	}
	
	public int getSpawnY(){
		return spawnY;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public int getColor(){
		return color;
	}
	
	public int getTileIndex(){
		return tileIndex;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	public BoxCollider getCollider(){
		return collider;
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
