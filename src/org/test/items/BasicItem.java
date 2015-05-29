package org.test.items;

import org.test.game.Game;
import org.test.gfx.Screen;
import org.test.phisics.BoxCollider;
import org.test.sound.Sound;

public class BasicItem extends Item{
	protected int itemId;
	protected int itemColor;
	
	public BasicItem(int id, String name,int x, int y, int color, int colX, int colY, int colXOffset, int colYOffset, int colWidth, int colHeight, Sound sound){
		super(id, false, name);
		this.itemId = x + y * 32;
		this.itemColor = color;
		this.x = x;
		this.y = y;
		this.collider = new BoxCollider<Item>(colX, colY, colXOffset, colYOffset, colWidth, colHeight, Game.level, this);
		Game.colliders.add(collider);
		this.sound = sound;
	}
	
	public BasicItem(int id, String name,int x, int y, int color, Sound sound){
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
		screen.render(posX, posY, this.x + this.y * 32, itemColor, 0x00, 1);
		screen.render(posX+8, posY, (this.x+1) + this.y * 32, itemColor, 0x00, 1);
		screen.render(posX, posY+8, this.x + (this.y+1) * 32, itemColor, 0x00, 1);
		screen.render(posX+8, posY+8, (this.x+1) + (this.y+1) * 32, itemColor, 0x00, 1);
	}
	
	public void setItemId(int x, int y){
		this.itemId = x + y * 32;
	}

	public void tick() {

	}
}
