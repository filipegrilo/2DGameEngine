package org.test.level.tiles;

import org.test.gfx.Colors;
import org.test.gfx.Screen;
import org.test.level.Level;

public abstract class Tile {
	
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, false, Colors.get(000, -1, -1, -1), 0xff000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, false, Colors.get(-1, 333, -1, -1), 0xff555555);
	public static final Tile GRASS = new BasicTile(2, 2, 0, false, Colors.get(-1, 131, 141, -1), 0xff00ff00);
	public static final Tile WATER = new AnimatedTile(3, false, new int[][] {{0,  5}, {1, 5}, {2, 5}, {1, 5}}, Colors.get(-1, 004, 115, -1), 0xff0000ff, 500);
	public static final Tile LAVA = new DamageAnimatedTile(4, 5, new int[][] {{0,  5}, {1, 5}, {2, 5}, {1, 5}} , Colors.get(-1, 510, 500, -1), 0xffff0000, 600);
	
	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	protected boolean doesDamage;
	private int levelColor;
	
	public Tile(int id, boolean isSolid, boolean isEmitter, boolean doesDamage, int levelColor){
		this.id = (byte) id;
		if(tiles[id] != null) throw new RuntimeException("Duplicated tile id on " + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.doesDamage = doesDamage;
		this.levelColor = levelColor;
		tiles[id] = this;
	}
	
	public byte getId(){
		return id;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public boolean isEmitter(){
		return emitter;
	}
	
	public int getLevelColor(){
		return levelColor;
	}
	
	public boolean doesDamage(){
		return doesDamage;
	}
	
	public abstract void tick();
	public abstract void render(Screen screen, Level level, int x, int y);
}
