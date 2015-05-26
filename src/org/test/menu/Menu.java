package org.test.menu;

import org.test.game.InputHandler;
import org.test.gfx.Font;
import org.test.gfx.Screen;

public abstract class Menu {
	protected String name;
	protected int x, y;
	protected int width, height;
	protected int color;
	protected int titleColor;
	public boolean show;
	
	public Menu(String name, int x, int y, int width, int height, int color, int titleColor){
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.titleColor = titleColor;
		this.show = false;
	}
	
	public abstract void tick(InputHandler input);
	
	public abstract void render(Screen screen);
	public abstract void createMenuItems(InputHandler input);
	
	protected void renderBackground(Screen screen){
		int xTile = 0;
		int yTile = 26;
		
		for(int i=0 ; i <= (width >> 3); i++){
			for(int j = 0; j <= (height >> 3); j++){
				int tile;
				int rotation;
				
				if(i == 0){
					tile = (xTile + 0) + yTile * 32;
					rotation = 0x00;
					
					if(j == 0) rotation = 0x00;
					else if(j == (height >> 3)) rotation = 0x02;
					else tile = (xTile + 1) + yTile * 32;
				}else if(i == (width >> 3)){
					tile = (xTile + 0) + yTile * 32;
					rotation = 0x01;
					
					if(j == 0) rotation = 0x01;
					else if(j == (height >> 3)) rotation = 0x03;
					else tile = (xTile + 1) + yTile * 32;
				}else{
					tile = (xTile + 2) + yTile * 32;
					rotation = 0x00;
					
					if(j == (height >> 3)) rotation = 0x02;
					else if(j == 0) rotation = 0x00;
					else tile = (xTile + 3) + yTile * 32;
				}
					
				screen.render(x + i * 8 + screen.xOffset, y + j * 8 + screen.yOffset, tile, color, rotation, 1);
			}
		}
		
		Font.render(name, screen, x + screen.xOffset + width / 2 - (name.length() * 8 / 2), y + screen.yOffset + 5, titleColor, 1);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void toggle(){
		show = !show;
	}
}
