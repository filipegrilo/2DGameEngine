package org.test.entities.ability;

import org.test.enums.Direction;
import org.test.game.particles.Particle;
import org.test.gfx.Screen;
import org.test.sound.Sound;
import org.test.time.Time;

public abstract class ProjetileAbility extends Ability{
	Particle particle;
	
	public ProjetileAbility(String name, Particle particle, int cooldown, int range, int speed, Sound sound) {
		super(name, cooldown, range, speed, sound);
		this.particle = particle;
	}
	
	protected void move(){
		if(active){
			int xa = 0;
			int ya = 0;
			
			switch(particle.getDirection()){
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
			
			if(!particle.getCollider().hasCollided(xa, ya)){
				particle.setX(particle.getX() + xa * speed);
				particle.setY(particle.getY() + ya * speed);
				particle.tick();
				if(!inRange()) active = false;
			}else active = false;
		}
	}
	
	public void activate(int x, int y, Direction direction){
		if(ready){
			int xOffset = 0;
			int yOffset = 0;
			
			switch (direction) {
				case UP: 
					yOffset--;
					break;
				case DOWN: 
					yOffset++;
					break;
				case RIGHT: 
					xOffset ++;
					break;
				case LEFT: 
					xOffset--;
					break;
				case UP_RIGHT:
					yOffset--;
					xOffset++;
					break;
				case UP_LEFT:
					yOffset--;
					xOffset--;
					break;
				case DOWN_RIGHT:
					yOffset++;
					xOffset++;
					break;
				case DOWN_LEFT:
					yOffset++;
					xOffset--;
					break;
			}
			
			particle.setDirection(direction);
			
			int spawnX = x + (xOffset << 3);
			int spawnY = y + (yOffset << 3);
			
			particle.setSpawn(spawnX, spawnY);
			
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
	
	protected boolean inRange() {
		if(Math.pow(particle.getX() - particle.getSpawnX(), 2) + Math.pow(particle.getY() - particle.getSpawnY(), 2) <= (range << 3) * (range << 3)) return true;
		return false;
	}
	
	public abstract void tick();
	public abstract void render(Screen screen);
}
