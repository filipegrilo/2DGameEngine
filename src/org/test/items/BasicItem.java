package org.test.items;

import org.test.gfx.Screen;

public class BasicItem extends Item{
	protected int itemId;
	protected int itemColor;
	private int x, y;
	
	public BasicItem(int id, String name,int x, int y,int color){
		super(id, false, name);
		this.itemId = x + y * 32;
		this.itemColor = color;
		this.x = x;
		this.y = y;
	}

	public void render(Screen screen, int x, int y) {
		screen.render(x, y, this.x + this.y * 32, itemColor, 0x00, 1);
		screen.render(x+8, y, (this.x+1) + this.y * 32, itemColor, 0x00, 1);
		screen.render(x, y+8, this.x + (this.y+1) * 32, itemColor, 0x00, 1);
		screen.render(x+8, y+8, (this.x+1) + (this.y+1) * 32, itemColor, 0x00, 1);
	}
	
	public void render(Screen screen) {
		screen.render(x, y, this.x + this.y * 32, itemColor, 0x00, 1);
		screen.render(x+8, y, (this.x+1) + this.y * 32, itemColor, 0x00, 1);
		screen.render(x, y+8, this.x + (this.y+1) * 32, itemColor, 0x00, 1);
		screen.render(x+8, y+8, (this.x+1) + (this.y+1) * 32, itemColor, 0x00, 1);
	}
	
	public void setItemId(int x, int y){
		this.itemId = x + y * 32;
	}

	public void tick() {

	}
}
