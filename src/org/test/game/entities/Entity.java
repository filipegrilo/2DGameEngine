package org.test.game.entities;

import org.test.enums.Direction;
import org.test.gfx.Screen;
import org.test.level.Level;

public abstract class Entity {
	public int x, y;
	protected Direction direction;
	protected Level level;
	
	public Entity(Level level){
		init(level);
	}
	
	public final void init(Level level){
		this.level = level;
	}
	
	public abstract void tick();
	public abstract void render(Screen screen);
}
