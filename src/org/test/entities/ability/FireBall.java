package org.test.entities.ability;

import org.test.game.particles.Particle;
import org.test.gfx.Screen;

public class FireBall extends ProjetileAbility{
	public FireBall() throws CloneNotSupportedException {
		super("Fireball", (Particle) Particle.fireBall.clone(), 2, 10, 3);
	}

	public void tick() {
		updateTimers();
		move();
	}
	
	public void render(Screen screen) {
		if(active) particle.render(screen);
	}

}
