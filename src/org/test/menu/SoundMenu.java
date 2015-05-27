package org.test.menu;

import org.test.game.InputHandler;
import org.test.gfx.Colors;
import org.test.gfx.Screen;
import org.test.sound.Sound;

public class SoundMenu extends Menu{
	private int selected = 0;
	private int selectedColor;
	private int textColor;
	private MenuItem[] menuItems;
	
	public SoundMenu(String name, int x, int y, int width, int height, int textColor, int selectedColor) {
		super(name, x, y, width, height, Colors.get(-1, 000, 555, -1), Colors.get(-1, -1, -1, 222));
		this.textColor = textColor;
		this.selectedColor = selectedColor;
	}
	
	public void createMenuItems(InputHandler input){
		int size = 3;
		int width = getWidth();
		int height = getHeight();
		int yPos = 20;
		int xPos = x + width / 2;
		int yOffsetIncrement = 10;
		
		menuItems = new MenuItem[size];
		
		menuItems[0] = new MenuItem("Quit", xPos - (4 * 8 / 2), y + yPos, textColor){

			public void onEnter() {
				show = false;
			}
			
		};
		yPos += yOffsetIncrement;
		menuItems[1] = new MenuItem("Activate Sound", xPos - (14 * 8 / 2), y + yPos, textColor){

			public void onEnter() {
				Sound.soundActive = true;
			}
			
		};
		yPos += yOffsetIncrement;
		menuItems[2] = new MenuItem("Mute Sound", xPos - (10 * 8 / 2), y + yPos, textColor){

			@Override
			public void onEnter() {
				Sound.soundActive = false;
			}
			
		};
		yPos += yOffsetIncrement;
		
	}
	
	public void tick(InputHandler input) {
		if(show){
			if(input.get("Menu NavDown").isReleased()){
				selected++;
				
				if(selected > menuItems.length-1) selected = 0;
			}
			
			if(input.get("Menu NavUp").isReleased()){
				selected--;
				
				if(selected < 0) selected = menuItems.length-1;
			}
			
			if(input.get("Enter").isReleased()) menuItems[selected].onEnter();
		}
	}
	
	public void render(Screen screen) {
		if(show){
			renderBackground(screen);
			
			for(int i=0; i < menuItems.length; i++){
				if(i == selected) menuItems[i].setColor(selectedColor);
				else menuItems[i].setColor(textColor);
				
				menuItems[i].render(screen);
			}
		}
	}

	public void resetSelected(){
		selected = 0;
	}
}
