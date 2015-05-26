package org.test.game.entities;

import org.test.entities.ability.Ability;
import org.test.entities.ability.Dash;
import org.test.entities.ability.Display;
import org.test.entities.ability.FireBall;
import org.test.enums.Direction;
import org.test.game.Game;
import org.test.game.InputHandler;
import org.test.game.particles.Particle;
import org.test.gfx.Colors;
import org.test.gfx.Font;
import org.test.gfx.Screen;
import org.test.level.Level;
import org.test.menu.Hotbar;
import org.test.menu.Inventory;
import org.test.menu.Menu;

public class Player extends Mob{
	
	private InputHandler input;
	private Menu inventory;
	private Hotbar hotbar;
	private Display abilityDisplay;
	
	private FireBall fireBall = new FireBall();
	private Dash dash = new Dash(this);
	
	public int startX, startY;
	private int color = Colors.get(-1, 111, 145, 543);
	private int scale = 1;
	private int tickCount = 0;
	
	protected boolean isSwimming = false;
	private boolean showMessage = false;
	
	public Player(Level level, int x, int y, InputHandler input) throws CloneNotSupportedException{
		super(level, "Player", x, y, 1);
		this.input = input;
		this.inventory = new Inventory("Inventory", 8, 8, Game.WIDTH - 20, Game.HEIGHT - 20);
		this.hotbar = new Hotbar(3 * (Game.WIDTH / 4) - 5 * 15 / 2, Game.HEIGHT - 25);
		this.abilityDisplay = new Display(Game.WIDTH / 4 - 5 * 15 / 2, Game.HEIGHT - 25, new Ability[]{fireBall, dash},
																						 new int[]{Colors.get(000, 500, 531, 540),
																								   Colors.get(000, 500, 333, 555)},
																						 new int[]{0 + 7 * 32, 1 + 7 * 32});
		direction = Direction.DOWN;
	}
	
	public void tick() {
		if(input.get("Inventory").isReleased()) inventory.toggle();
		
		if(inventory.show) inventory.tick(input);
		else{
			int xa = 0;
			int ya = 0;
			
			if(input.get("Up").isPressed()){
				ya--;
				direction = Direction.UP;
			}
			if(input.get("Down").isPressed()){
				ya++;
				direction = Direction.DOWN;
			}
			if(input.get("Left").isPressed()){
				xa--;
				direction = Direction.LEFT;
			}
			if(input.get("Right").isPressed()){
				xa++;
				direction = Direction.RIGHT;
			}
			
			if(xa != 0 && ya != 0){
				if(xa > 0){
					if(ya > 0) direction = Direction.DOWN_RIGHT;
					if(ya < 0) direction = Direction.UP_RIGHT;
				}
				
				if(xa < 0){
					if(ya > 0) direction = Direction.DOWN_LEFT;
					if(ya < 0) direction = Direction.UP_LEFT;
				}
			}
			
			if(input.get("Abillity1").isReleased()) fireBall.activate(x, y, direction);
			if(input.get("Abillity2").isReleased()) dash.activate();
			
			if(input.get("Msg").isPressed()) showMessage = true;
			else showMessage = false;
			
			if(xa != 0 || ya != 0){
				move(xa,  ya);
				isMoving = true;
			}else{
				isMoving = false;
			}
			if(level.getTile(this.x >> 3, this.y >> 3).getId() == 3){
				isSwimming = true;
			}
			if(isSwimming && level.getTile(this.x >> 3, this.y >> 3).getId() != 3){
				isSwimming = false;
			}
			
			abilityDisplay.tick();
			fireBall.tick();
			dash.tick();
			hotbar.tick(input);
		}
		
		tickCount++;
	}

	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 28;
		int walkingSpeed = 4;
		
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;
		
		if(showMessage){
			Font.render("Hello", screen, xOffset - (5 * 8 / 2) + 4, yOffset - 10, Colors.get(-1, -1, -1, 500), 1);
		}
		
		fireBall.render(screen);
		
		if(movingDir == 1) xTile += 2;
		else if(movingDir > 1){
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}
		
		if(isSwimming){
			int waterColor = 0;
			yOffset += 4;
			
			if(tickCount % 60 < 15){ 
				waterColor = Colors.get(-1, -1, 225, -1);
			}	
			else if(15 <= tickCount % 60 && tickCount % 60 < 30){
				yOffset -= 1;
				waterColor = Colors.get(-1, 225, 115, -1);
			}	
			else if(30 <= tickCount % 60 && tickCount % 60 < 45){
				waterColor = Colors.get(-1, 115, -1, 225);
			}	
			else {
				yOffset -= 1;
				waterColor = Colors.get(-1, 225, 115, -1);
			}
				
			screen.render(xOffset, yOffset + 3, 0 + 27 * 32, waterColor, 0x00, 1);
			screen.render(xOffset + 8, yOffset + 3, 0 + 27 * 32, waterColor, 0x01, 1);
		}
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
		
		if(!isSwimming){
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
			screen.render(xOffset+ modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
		}
		
		abilityDisplay.render(screen);
		hotbar.render(screen);
		inventory.render(screen);
	}
}
