package org.test.entities.ability;

import org.test.game.entities.Player;
import org.test.gfx.Screen;
import org.test.sound.Sound;
import org.test.time.Time;

public class Dash extends PlayerAbility{

	public Dash(Player player) throws CloneNotSupportedException {
		super("Dash", player, 4, 2, 3, (Sound)Sound.dash.clone());
	}

	public void tick() {
		updateTimers();
		move();
	}
	
	public void activate(){
		if(ready){
			player.startX = player.x;
			player.startY = player.y;
			this.direction = player.getDirection();
			
			ready = false;
			active = true;
			sound.play();
			time = new Time(cooldown);
		}else{
			if(messageTime == null){
				renderMessage = true;
				messageTime = new Time(MESSAGE_ACTIVE_DURATION);
			}
		}
	}

	public void render(Screen screen) {
		
	}

}
