package org.test.phisics;

import org.test.game.Game;
import org.test.level.Level;
import org.test.level.tiles.Tile;

public class BoxCollider<T> extends Collider<T>{
	public BoxCollider(int x, int y, int width, int height, Level level, T type){
		super(x, y, 0, 0, level, true, type);
		this.width = width;
		this.height = height;
	}
	
	public BoxCollider(int x, int y, int xOffset, int yOffset, int width, int height, Level level, T type){
		super(x, y, xOffset, yOffset, level, true, type);
		this.width = width;
		this.height = height;
	}
	
	public void tick(int x, int y){
		this.x = x + xOffset;
		this.y = y + yOffset;
	}
	
	public boolean hasCollided(int xa, int ya, double speed){
		for(Collider<T> collider : Game.colliders){
			if(!collider.equals(this)){
				if(this.x + xa * speed < collider.x + collider.width && this.x + xa * speed + this.width > collider.x){
					if(this.y + ya * speed < collider.y + collider.height && this.y + ya * speed + this.height > collider.y){
						this.colisionObject = collider.type;
						return true;
					}
				}
			}
		}
		
		this.colisionObject = null;
		return false;
	}
	
	public boolean hasCollided(int xa, int ya){
		int xMin = x + xOffset/2;
		int yMin = y + yOffset/2;
		int xMax = xMin + width/2;
		int yMax = yMin + height/2;
		
		for(int i = xMin; i < xMax; i++)
			if(isSolidTile(xa, ya, i, yMin)) return true;
		
		for(int i = xMin; i < xMax; i++)
			if(isSolidTile(xa, ya, i, yMax)) return true;
		
		for(int j = yMin; j < yMax; j++)
			if(isSolidTile(xa, ya, xMin, j)) return true;
		
		for(int j = yMin; j < yMax; j++)
			if(isSolidTile(xa, ya, xMax, j)) return true;
		
		return false;
	}
	
	private boolean isSolidTile(int xa, int ya, int x, int y){
		if(level == null) return false;
		
		Tile lastTile = level.getTile(x >> 3, y >> 3);
		Tile newTile = level.getTile((x + xa)>>3, (y + ya) >> 3);
		
		if(!lastTile.equals(newTile) && newTile.isSolid()) return true;
		
		return false;
	}
}
