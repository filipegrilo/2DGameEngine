package org.test.game.entities;

import org.test.gfx.Colors;
import org.test.gfx.Screen;

public class HealthDisplay {
	private int fullHeartTile;
	private int halfHeartTile;
	private int color;
	private int x, y;
	private int xOffset;
	private Player player;
	private int numHearts;
	private int numHalfHearts;
	
	public HealthDisplay(Player player, int x, int y){
		this.player = player;
		this.x = x;
		this.y = y;
		this.xOffset = 5;
		this.fullHeartTile = 0 + 8 * 32;
		this.halfHeartTile = 1 + 8 * 32;
		this.color = Colors.get(-1, 000, 500, -1);
	}
	
	public void tick(){
		numHearts = (int)(player.health / 10);
		
		if((int)(player.health % 10) >= 5) numHalfHearts++;
		else numHalfHearts = 0;
	}
	
	public void render(Screen screen){
		for(int i=0; i < numHearts; i++)
			screen.render(x + screen.xOffset + i * (8 + xOffset), y + screen.yOffset, fullHeartTile, color, 0x00, 1);
		for(int i=0; i < numHalfHearts; i++)
			screen.render(x + screen.xOffset + numHearts * (8 + xOffset) , y + screen.yOffset, halfHeartTile, color, 0x00, 1);
	}
}
