package org.test.level.tiles;

public class DamageAnimatedTile extends AnimatedTile{
	private int damage;
	
	public DamageAnimatedTile(int id, int damage, int[][] animationCoords, int tileColor, int levelColor, int animationSwitchDelay) {
		super(id, true, animationCoords, tileColor, levelColor, animationSwitchDelay);
		this.damage = damage;
	}
	
	public int getDamage(){
		return damage;
	}
}
