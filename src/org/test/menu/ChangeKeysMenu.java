package org.test.menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.test.game.Game;
import org.test.game.InputHandler;
import org.test.game.InputHandler.Key;
import org.test.gfx.Colors;
import org.test.gfx.Screen;

public class ChangeKeysMenu extends Menu{
	private Map<String, Key> keys;
	MenuItem[] menuItems;
	private int selected = 0;
	private int selectedColor;
	private int textColor;
	
	public ChangeKeysMenu(int x, int y, int width, int height, InputHandler input, int textColor, int selectedColor) {
		super("Change Keys", x, y, width, height, Colors.get(-1, 000, 555, -1), Colors.get(-1, -1, -1, 222));
		this.keys = input.getKeys();
		this.textColor = textColor;
		this.selectedColor = selectedColor;
	}

	public void tick(InputHandler input) {
		
	}

	public void render(Screen screen) {
		
	}

	public void createMenuItems(InputHandler input) {
		menuItems = new MenuItem[keys.size()];
		int index = 0;
		int yOffset = 10;
		int curY = 0;
		int centeredX = x + width / 2;
		
		for(Map.Entry<String, Key> element : keys.entrySet()){
			String itemText = element.getKey() + " " + (char)(element.getValue().getKeycode());
			curY += yOffset;
			
			menuItems[index] = new MenuItem(itemText, centeredX - (itemText.length() * 8 / 2), curY, textColor) {
				
				public void onEnter() {
					new KeyListener() {
						
						public void keyTyped(KeyEvent e) {
								
						}
						
						public void keyReleased(KeyEvent e) {
							
						}
						
						public void keyPressed(KeyEvent e) {
							element.getValue().setKeycode(e.getKeyCode());
						}
					};
				}
			};
			index++;
		}
	}

}
