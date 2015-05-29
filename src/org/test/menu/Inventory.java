package org.test.menu;

import org.test.game.InputHandler;
import org.test.gfx.Colors;
import org.test.gfx.Screen;

public class Inventory extends Menu{
	
	public Inventory(String name, int x, int y, int width, int height){
		super(name, x, y, width, height, Colors.get(-1, 444, 555, 000), Colors.get(-1, -1, -1, 500));
	}

	public void tick(InputHandler input) {
		if(show){
			
		}
	}

	public void render(Screen screen) {
		if(show){
			renderBackground(screen);
		}
	}

	public void createMenuItems(InputHandler input) {
		
	}
}
