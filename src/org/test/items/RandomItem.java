package org.test.items;

import org.test.game.Game;
import org.test.sound.Sound;

public class RandomItem extends BasicItem{

	public RandomItem(int id, String name, int x, int y, int color, int colX, int colY, int colXOffset, int colYOffset, int colWidth, int colHeight, Sound sound) {
		super(id, name, x, y, color, colX, colY, colXOffset, colYOffset, colWidth, colHeight, sound);
		setXY((int)(Math.random() * ((Game.level.width << 3) - 16) + 1), (int)(Math.random() * ((Game.level.height << 3) - 16) + 1));
	}

}
