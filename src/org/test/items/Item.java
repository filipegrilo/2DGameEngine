package org.test.items;

import org.test.gfx.Colors;
import org.test.gfx.Screen;

public abstract class Item implements Cloneable{
	public static final Item[] items = new Item[256];
	public static final Item pickaxe = new BasicItem(1, "Pickaxe", 0, 20, Colors.get(-1, 333, 321, 311));
	public static final Item broadsword = new BasicItem(2, "Broadsword", 2, 20, Colors.get(-1, 500, 111, 333));
	public static final Item chest = new BasicItem(3, "Chest", 0, 18, Colors.get(-1, 000, 310, 550));
	
	protected byte id;
	protected String name;
	protected boolean emitter;
	protected int x, y;
	
	public Item(int id, boolean isEmitter, String name){
		this.id = (byte) id;
		if(items[id] != null) throw new RuntimeException("Duplicated Item id on " + id);
		this.emitter = isEmitter;
		this.name = name;
		items[id] = this;
	}
	
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public byte getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
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
