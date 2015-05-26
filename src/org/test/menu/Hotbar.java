package org.test.menu;

import java.awt.List;

import org.test.game.Game;
import org.test.game.InputHandler;
import org.test.gfx.Colors;
import org.test.gfx.Font;
import org.test.gfx.Screen;
import org.test.items.Item;

public class Hotbar {
	private int x, y;
	private Item[] items;
	
	private int selected;
	
	public Hotbar(int x, int y){
		this.x = x;
		this.y = y;
		this.items = new Item[5];
		this.selected = 0;
		items[0] = Item.pickaxe;
		items[1] = Item.broadsword;
	}
	
	public void tick(InputHandler input){
		if(input.get("Inventory1").isPressed()) selected = 0;
		if(input.get("Inventory2").isPressed()) selected = 1;
		if(input.get("Inventory3").isPressed()) selected = 2;
		if(input.get("Inventory4").isPressed()) selected = 3;
		if(input.get("Inventory5").isPressed()) selected = 4;
		
		if(input.get("Next Item").isReleased()){
			selected++;
			
			if(selected > items.length-1) selected = 0;
		}
		
		if(input.get("Prev Item").isReleased()){
			selected--;
			
			if(selected < 0) selected = items.length-1;
		}
	}
	
	public void render(Screen screen){
		int color;
		
		for(int i=0; i < items.length; i++){
			if(i == selected) color = Colors.get(-1, 444, 505, -1);
			else color = Colors.get(-1, 444, 555, -1);
			
			screen.render(x + screen.xOffset + i * 15, y + screen.yOffset , 0 + 26 * 32, color, 0x00, 1);
			screen.render(x + screen.xOffset + i * 15 + 8, y + screen.yOffset, 0 + 26 * 32, color, 0x01, 1);
			screen.render(x + screen.xOffset + i * 15, y + screen.yOffset + 8, 0 + 26 * 32, color, 0x02, 1);
			screen.render(x + screen.xOffset + i * 15 + 8, y + screen.yOffset + 8, 0 + 26 * 32, color, 0x03, 1);
			
			if(items[i] != null){
				String itemName = items[i].getName();
				
				items[i].render(screen,x + screen.xOffset + i * 15, y + screen.yOffset);
				
				if(i == selected)
					Font.render(itemName, screen, screen.xOffset + x + (16 * items.length) / 2 - itemName.length() * 8 / 2, y + screen.yOffset - 15, Colors.get(-1, -1, -1, 000), 1);
			}
		}
	}
}
