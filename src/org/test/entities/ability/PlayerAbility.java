package org.test.entities.ability;

import org.test.enums.Direction;
import org.test.game.entities.Player;
import org.test.gfx.Screen;
import org.test.sound.Sound;

public abstract class PlayerAbility extends Ability{
	Player player;
	Direction direction;
	
	public PlayerAbility(String name, Player player, int cooldown, int range, int speed,Sound sound) {
		super(name, cooldown, range, speed, sound);
		this.player = player;
	}
	
	protected boolean inRange() {
		if(player.x > player.startX + (range << 3)) return false;
		if(player.x < player.startX - (range << 3)) return false;
		if(player.y > player.startY + (range << 3)) return false;
		if(player.y < player.startY - (range << 3)) return false;
		return true;
	}
	
	protected void move(){
		if(active){
			int xa = 0;
			int ya = 0;
			
			if(direction == Direction.UP) ya--;
			if(direction == Direction.DOWN) ya++;
			if(direction == Direction.LEFT) xa--;
			if(direction == Direction.RIGHT) xa++;
			
			if(direction == Direction.UP_LEFT) {
				ya--;
				xa--;
			}	
			if(direction == Direction.UP_RIGHT){
				ya--;
				xa++;
			}
			if(direction == Direction.DOWN_LEFT){
				ya++;
				xa--;
			}
			if(direction == Direction.DOWN_RIGHT){
				ya++;
				xa++;
			}
			
			if(!player.getCollider().hasCollided(xa, ya)){
				player.x += xa * speed;
				player.y += ya * speed;
				if(!inRange()) active = false;
			}else active = false;
		}
	}
	
	public abstract void tick();
	public abstract void render(Screen screen);
}
