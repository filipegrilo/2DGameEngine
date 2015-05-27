package org.test.menu;

import org.test.game.InputHandler;
import org.test.gfx.Colors;
import org.test.gfx.Screen;

public class MainMenu extends Menu {
	
	private int selected = 0;
	private int selectedColor;
	private int textColor;
	private MenuItem[] menuItems;
	private SoundMenu soundMenu;
	
	public MainMenu(String name, int x, int y, int width, int height, int textColor, int selectedColor) {
		super(name, x, y, width, height, Colors.get(-1, 000, 555, -1), Colors.get(-1, -1, -1, 222));
		this.textColor = textColor;
		this.selectedColor = selectedColor;
		this.soundMenu = new SoundMenu("Sound", x, y, width, height, textColor, selectedColor);
	}
	
	public void createMenuItems(InputHandler input){
		soundMenu.createMenuItems(input);
		
		int size = 4;
		int width = getWidth();
		int height = getHeight();
		int yPos = 20;
		int xPos = x + width / 2;
		int yOffsetIncrement = 10;
		
		menuItems = new MenuItem[size];
		
		menuItems[0] = new MenuItem("Resume", xPos - (6 * 8 / 2), y + yPos, textColor){

			public void onEnter() {
				show = false;
			}
			
		};
		yPos += yOffsetIncrement;
		menuItems[1] = new MenuItem("Save Keys", xPos - (9 * 8 / 2), y + yPos, textColor){

			public void onEnter() {
				input.saveKeys();
			}
			
		};
		yPos += yOffsetIncrement;
		menuItems[2] = new MenuItem("Sound Options", xPos - (13 * 8 / 2), y + yPos, textColor){

			public void onEnter() {
				soundMenu.resetSelected();
				soundMenu.show = true;
			}
			
		};
		yPos += yOffsetIncrement;
		menuItems[3] = new MenuItem("Exit", xPos - (4 * 8 / 2), y + yPos, textColor){

			@Override
			public void onEnter() {
				System.exit(0);
			}
			
		};
		yPos += yOffsetIncrement;
		
	}
	
	public void tick(InputHandler input) {
		if(show){
			if(soundMenu.show){
				soundMenu.tick(input);
			}else{
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
	}
	
	public void render(Screen screen) {
		if(show){
			renderBackground(screen);
			
			if(soundMenu.show){
				soundMenu.render(screen);
			}else{
				for(int i=0; i < menuItems.length; i++){
					if(i == selected) menuItems[i].setColor(selectedColor);
					else menuItems[i].setColor(textColor);
					
					menuItems[i].render(screen);
				}
			}
		}
	}

	public void resetSelected(){
		selected = 0;
	}
}
