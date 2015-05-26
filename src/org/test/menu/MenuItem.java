package org.test.menu;

import org.test.gfx.Font;
import org.test.gfx.Screen;

public abstract class MenuItem {
	private String name;
	private int x, y;
	private int color;
	
	public MenuItem(String name, int x, int y, int color){
		this.name = name;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public abstract void onEnter();
	
	public void render(Screen screen){
		Font.render(name, screen, x + screen.xOffset, y + screen.yOffset, color, 1);
	}
	
	public String getName(){
		return name;
	}
	
	public void setColor(int color){
		this.color = color;
	}
}
