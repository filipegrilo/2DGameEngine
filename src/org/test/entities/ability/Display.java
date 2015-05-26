package org.test.entities.ability;

import org.test.game.Game;
import org.test.gfx.Colors;
import org.test.gfx.Font;
import org.test.gfx.Screen;

public class Display {
	private int x, y;
	private int size;
	private int[] tilesId;
	private int[] colors;
	private int[] startColors;
	private Ability[] abilities;
	private String[] timers;
	private int color;
	private int textColor;
	
	public Display(int x, int y, Ability[] abilities, int[] colors, int[] tilesId){
		this.x = x;
		this.y = y;
		this.abilities = abilities;
		this.size = abilities.length;
		this.tilesId = tilesId;
		this.timers = new String[size];
		this.color = Colors.get(-1, 333, -1, -1);
		this.textColor = Colors.get(-1, -1, -1, 505);
		this.colors = new int[size];
		this.startColors = colors;
		
		
		for(int i=0 ; i < size; i++){
			timers[i] = "";
			colors[i] = startColors[i];
		}
		
	}
	
	public void tick(){
		for(int i=0; i < size; i++){
			if(abilities[i].ready) colors[i] = startColors[i];
			else{
				timers[i] = "" + (int) (abilities[i].time.remaining() + 1);
				colors[i] = Colors.get(000, 000, 000, 000);
			}
		}
	}
	
	public void render(Screen screen){
		for(int i=0; i < size; i++){
			screen.render(x + screen.xOffset + i * 15 + 4, y + screen.yOffset + 4, tilesId[i], colors[i], 0x00, 2);
			
			screen.render(x + screen.xOffset + i * 15, y + screen.yOffset , 0 + 26 * 32, color, 0x00, 1);
			screen.render(x + screen.xOffset + i * 15 + 8, y + screen.yOffset, 0 + 26 * 32, color, 0x01, 1);
			screen.render(x + screen.xOffset + i * 15, y + screen.yOffset + 8, 0 + 26 * 32, color, 0x02, 1);
			screen.render(x + screen.xOffset + i * 15 + 8, y + screen.yOffset + 8, 0 + 26 * 32, color, 0x03, 1);
			
			if(!abilities[i].ready)
				Font.render(timers[i], screen, x + screen.xOffset + i * 14 + 16 / 2 - (timers[i].length() * 8 / 2), y + screen.yOffset + 4, textColor, 1);
			if(abilities[i].renderMessage)
				Font.render(abilities[i].getMessage(), screen, screen.xOffset + Game.WIDTH / 2 - (abilities[i].getMessage().length() * 8 / 2), screen.yOffset + 10, Colors.get(-1, -1, -1, 500), 1);
		}
	}
}
