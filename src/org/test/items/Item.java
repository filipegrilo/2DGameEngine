package org.test.items;

import org.test.game.Game;
import org.test.gfx.Colors;
import org.test.gfx.Screen;
import org.test.phisics.Collider;
import org.test.sound.Sound;

public abstract class Item implements Cloneable{
	public static final Item[] items = new Item[256];
	public static final Item pickaxe = new BasicItem(1, "Pickaxe", 0, 20, Colors.get(-1, 333, 321, 311), null);
	public static final Item broadsword = new BasicItem(2, "Broadsword", 2, 20, Colors.get(-1, 500, 111, 333), null);
	public static final Item chest = new BasicItem(3, "Chest", 0, 18, Colors.get(-1, 000, 310, 550), 0, 0, 8, 15, 6, 0, null);
	public static final Item health = new HealthRandomItem(4, "Helth", 20.0, 2, 18, Colors.get(-1, 150, 130, 500), 0, 0, 8, 10, 8, 4, Sound.heal);
	
	protected byte id;
	protected String name;
	protected boolean emitter;
	protected int x, y;
	protected int posX, posY;
	protected Collider<Item> collider;
	protected Sound sound;
	
	public Item(int id, boolean isEmitter, String name){
		this.id = (byte) id;
		if(items[id] != null) throw new RuntimeException("Duplicated Item id on " + id);
		this.emitter = isEmitter;
		this.name = name;
		items[id] = this;
	}
	
	public void setXY(int x, int y){
		if(x >= 0){
			this.posX = x;
			this.collider.setX(x);
		}
		if(y >= 0){
			this.posY = y;
			this.collider.setY(y);
		}
		
		if(x >= 0 || y >= 0){
			int tmp = Game.colliders.indexOf(collider);
			Game.colliders.set(tmp, collider);
		}
	}
	
	public byte getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getX(){
		return posX;
	}
	
	public int getY(){
		return posY;
	}
	
	public Sound getSound(){
		return sound;
	}
	
	public Collider getCollider(){
		return collider;
	}
	
	public void resetCollider(){
		try {
			this.collider = (Collider<Item>) collider.clone();
			this.collider.setType(this);
			Game.colliders.add(collider);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEmitter(){
		return emitter;
	}
	
	public abstract void tick();
	public abstract void render(Screen screen, int x, int y);
	public abstract void render(Screen screen);
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
