package org.test.items;

import org.test.sound.Sound;

public class HealthRandomItem extends RandomItem{
	private double healAmmount ;
	
	public HealthRandomItem(int id, String name, double healAmmount, int x, int y, int color, int colX, int colY, int colXOffset, int colYOffset, int colWidth, int colHeight, Sound sound) {
		super(id, name, x, y, color, colX, colY, colXOffset, colYOffset, colWidth, colHeight, sound);
		this.healAmmount = healAmmount;
	}
	
	public double getHealAmmount(){
		return healAmmount;
	}
}
