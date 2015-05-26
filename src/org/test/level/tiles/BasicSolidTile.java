package org.test.level.tiles;

public class BasicSolidTile extends BasicTile{

	public BasicSolidTile(int id, int x, int y, boolean doesDamage,int tileColor, int levelColor) {
		super(id, x, y, doesDamage, tileColor, levelColor);
		this.solid = true;
	}

}
