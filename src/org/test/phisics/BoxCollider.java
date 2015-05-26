package org.test.phisics;

import org.test.level.Level;
import org.test.level.tiles.Tile;
//needs fixing
public class BoxCollider {
	private int x, y;
	private int xOffset, yOffset;
	private int width, height;
	private Level level;
	private boolean active = true;
	
	public BoxCollider(int x, int y, int width, int height, Level level){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.level = level;
		this.xOffset = 0;
		this.yOffset = 0;
	}
	
	public BoxCollider(int x, int y, int xOffset, int yOffset, int width, int height, Level level){
		this.x = x + xOffset;
		this.y = y + yOffset;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.width = width-1;
		this.height = height-1;
		this.level = level;
	}
	
	public void tick(int x, int y){
		this.x = x + xOffset;
		this.y = y + yOffset;
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
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return y;
	}
}
