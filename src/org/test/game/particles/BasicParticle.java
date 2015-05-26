package org.test.game.particles;

import org.test.gfx.Screen;
import org.test.time.Time;

public class BasicParticle extends Particle{
	private Time time;
	private double delay;
	private int[] colors;
	private int[] tilesIndex;
	private int curColorIndex = 0;
	private int curTileIndex = 0;
	
	public BasicParticle(int[] tilesIndex,int[] colors,double delay) {
		super(tilesIndex[0], colors[0], true);
		this.delay = delay;
		this.colors = colors;
		this.tilesIndex = tilesIndex;
		if(delay > 0) time = new Time(delay);
		else time = null;
	}

	public void tick() {
		if(time == null){
			collider.tick(x, y);
			color = colors[0];
			tileIndex = tilesIndex[0];
			return;
		}
				
		time.tick();
		collider.tick(x, y);
		
		if(time.isDone()){
			curColorIndex = (curColorIndex + 1) % colors.length;
			curTileIndex = (curTileIndex + 1) % tilesIndex.length;
			
			color = colors[curColorIndex];
			tileIndex = tilesIndex[curTileIndex];
			time.reset();
		}
	}

	public void render(Screen screen) {
		screen.render(x, y, tileIndex, color, 0x00, 1);
	}
}
