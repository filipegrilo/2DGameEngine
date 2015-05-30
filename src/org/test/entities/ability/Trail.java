package org.test.entities.ability;

import org.test.game.Game;
import org.test.game.entities.Player;
import org.test.gfx.Screen;
import org.test.level.tiles.Tile;
import org.test.sound.Sound;
import org.test.time.Time;

public class Trail extends PlayerAbility{
	private final double TILE_DURATION = 5.0;
	
	private Time[] tilesTimes;
	private int[][] positions;
	private Tile[] originalTiles;
	
	private int width;
	
	public Trail(Player player) {
		super("Trail", player, 9, 5, 0, (Sound)Sound.trail.clone());
		width = 3;
		tilesTimes = new Time[range * width];
		positions = new int[range * width][2];
		originalTiles = new Tile[range * width];
	}

	public void tick() {
		updateTimers();
		
		for(int i=0; i < range * width; i++){
			if(tilesTimes[i] != null){
				tilesTimes[i].tick();
				
				if(tilesTimes[i].isDone()){
					tilesTimes[i] = null;
					Game.level.alterTile(positions[i][0], positions[i][1], originalTiles[i]);
					active = false;
				}
			}
			
		}
	}
	
	public void activate(){
		if(ready){
			sound.play();
			for(int i=0; i < range; i++){
				for(int j=0; j < width; j++){
					int xa = 0;
					int ya = 0;
					
					switch(player.getDirection()){
						case UP: 
							ya--;
							break;
						case DOWN: 
							ya++;
							break;
						case LEFT: 
							xa--;
							break;
						case RIGHT: 
							xa++;
							break;
						case UP_RIGHT:
							xa++;
							ya--;
							break;
						case UP_LEFT:
							xa--;
							ya--;
							break;
						case DOWN_RIGHT:
							xa++;
							ya++;
							break;
						case DOWN_LEFT:
							xa--;
							ya++;
							break;
					}
				 	
					int tmpX = (player.x >> 3) - (i + 1) * xa;
					int tmpY = (player.y >> 3) - (i + 1) * ya;
					
					if(xa != 0) tmpY += j;
					if(ya != 0) tmpX += j;
					
					if(tmpX > 0 && tmpY > 0 && tmpX < Game.level.width && tmpY < Game.level.height){
						originalTiles[j + i * width] = Game.level.getTile(tmpX, tmpY);
						Game.level.alterTile(tmpX, tmpY, Tile.LAVA);
						positions[j + i * width][0] = tmpX;
						positions[j + i * width][1] = tmpY;
						
						tilesTimes[j + i * width] = new Time(TILE_DURATION);
					}
				}
			}
			
			ready = false;
			active = true;
			time = new Time(cooldown);
		}else{
			if(messageTime == null){
				renderMessage = true;
				messageTime = new Time(MESSAGE_ACTIVE_DURATION);
			}
		}
	}

	public void render(Screen screen) {
		// TODO Auto-generated method stub
		
	}

}
