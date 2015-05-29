package org.test.game.entities;

import org.test.entities.ability.Ability;
import org.test.entities.ability.Dash;
import org.test.entities.ability.Display;
import org.test.entities.ability.FireBall;
import org.test.enums.Direction;
import org.test.game.Game;
import org.test.game.InputHandler;
import org.test.gfx.Colors;
import org.test.gfx.Font;
import org.test.gfx.Screen;
import org.test.items.HealthRandomItem;
import org.test.items.Item;
import org.test.level.Level;
import org.test.level.tiles.DamageAnimatedTile;
import org.test.level.tiles.Tile;
import org.test.menu.Hotbar;
import org.test.menu.Inventory;
import org.test.menu.Menu;
import org.test.phisics.BoxCollider;
import org.test.sound.Sound;
import org.test.time.Time;

public class Player extends Mob{
	private final double DAMAGE_TICK = 0.5;
	private final double BLIND_TICK = 2;
	
	private final double MAX_HEALTH = 100;
	
	private InputHandler input;
	private HealthDisplay healthDisplay;
	private Menu inventory;
	private Hotbar hotbar;
	private Display abilityDisplay;
	
	private FireBall fireBall = new FireBall();
	private Dash dash = new Dash(this);
	
	public int startX, startY;
	private int color = Colors.get(-1, 111, 145, 543);
	private int scale = 1;
	private int tickCount = 0;
	
	private Time damageTimer;
	private Time blindTimer;
	
	protected boolean isSwimming = false;
	protected boolean isWalking = false;
	protected boolean isBlinded = false;
	protected boolean isDead = false;
	private boolean showMessage = false;
	
	public Player(Level level, int x, int y, InputHandler input) throws CloneNotSupportedException{
		super(level, "Player", x, y, 1, 100);
		this.collider = new BoxCollider(x, y, 0, 0, 16, 16, level, this);
		Game.colliders.add(collider);
		this.input = input;
		this.healthDisplay = new HealthDisplay(this, Game.WIDTH - 10 * (8 + 5) - 5, 5);
		this.inventory = new Inventory("Inventory", 8, 8, Game.WIDTH - 20, Game.HEIGHT - 20);
		this.hotbar = new Hotbar(3 * (Game.WIDTH / 4) - 5 * 15 / 2, Game.HEIGHT - 25);
		this.abilityDisplay = new Display(Game.WIDTH / 4 - 5 * 15 / 2, Game.HEIGHT - 25, new Ability[]{fireBall, dash},
																						 new int[]{Colors.get(000, 500, 531, 540),
																								   Colors.get(000, 500, 333, 555)},
																						 new int[]{0 + 7 * 32, 1 + 7 * 32});
		direction = Direction.DOWN;
		Sound.background.playLoop();
	}
	
	public void tick() {
		soundControl();
		
		if(input.get("Inventory").isReleased()) inventory.toggle();
		
		if(inventory.show) inventory.tick(input);
		else{
			Tile curTile = level.getTile(x >> 3, y >> 3);
			
			movementControl(curTile);
			abilityControl();
			damageControl(curTile);
			effectsControl();
			
			if(input.get("Msg").isPressed()) showMessage = true;
			else showMessage = false;
			
			healthDisplay.tick();
			abilityDisplay.tick();
			fireBall.tick();
			dash.tick();
			hotbar.tick(input);
		}
		
		tickCount++;
	}

	public void render(Screen screen) {
		playerRender(screen);
		
		fireBall.render(screen);
		
		healthDisplay.render(screen);
		abilityDisplay.render(screen);
		hotbar.render(screen);
		inventory.render(screen);
		
		effectsRenderer(screen);
	}
	
	private void soundControl(){
		if(!Sound.soundActive) Sound.background.stop();
		else{
			if(!Sound.background.isActive()) Sound.background.playLoop();
		}
		
		if(isWalking) Sound.walking.play();
		else Sound.walking.stop();
		
		if(isSwimming && isMoving) Sound.swimming.play();
		else Sound.swimming.stop();
	}
	
	private void damageControl(Tile curTile){
		if(damageTimer != null) damageTimer.tick();
		
		if(curTile.doesDamage()){
			if(damageTimer == null){
				health -= ((DamageAnimatedTile) curTile).getDamage();
				damageTimer = new Time(DAMAGE_TICK);
			}else{
				if(damageTimer.isDone()){
					damageTimer.reset();
					Sound.hurt.play();
					health -= ((DamageAnimatedTile) curTile).getDamage();
				}
			}
		}else{
			damageTimer = null;
		}
		
		if(health <= 0) isDead = true;
	}
	
	private void movementControl(Tile curTile){
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
			}else if(xa < 0){
				if(ya > 0) direction = Direction.DOWN_LEFT;
				if(ya < 0) direction = Direction.UP_LEFT;
			}
		}
		
		if(collider.hasCollided(xa, ya, speed)){
			try{
				Item item = (Item)(collider.getColisionObject());
				
				if(item.getId() == 4){
					Game.colliders.remove(item.getCollider());
					Game.level.items.remove(item);
					HealthRandomItem healItem = (HealthRandomItem) item;
					healItem.getSound().play();
					health += healItem.getHealAmmount();
					if(health > MAX_HEALTH) health = MAX_HEALTH;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(xa != 0 || ya != 0){
			move(xa,  ya);
			isMoving = true;
		}else{
			isMoving = false;
		}
		
		if(curTile.getId() == 3 || curTile.getId() == 4) isSwimming = true;
		if(isSwimming && curTile.getId() != 3 && curTile.getId() != 4) isSwimming = false;
		
		if(!isSwimming && isMoving) isWalking = true;
		else isWalking = false;
	}

	private void abilityControl(){
		if(input.get("Abillity1").isReleased())fireBall.activate(x, y, direction);
		if(input.get("Abillity2").isReleased()) dash.activate();
	}
	
	private void effectsControl(){
		if(blindTimer != null) blindTimer.tick();
		
		if(isBlinded){
			if(blindTimer == null){
				blindTimer = new Time(BLIND_TICK);
			}
			else{
				if(blindTimer.isDone()){
					isBlinded = false;
					blindTimer = null;
				}
			}
		}
	}

	private void playerRender(Screen screen){
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
		
		if(movingDir == 1) xTile += 2;
		else if(movingDir > 1){
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}
		
		if(isSwimming){
			int waterColor = 0;
			Tile curTile = level.getTile(x >> 3, y >> 3);
			yOffset += 4;
			
			if(tickCount % 60 < 15){ 
				if(curTile.getId() == 3) waterColor = Colors.get(-1, -1, 225, -1);
				else waterColor = Colors.get(-1, -1, 520, -1);
			}	
			else if(15 <= tickCount % 60 && tickCount % 60 < 30){
				yOffset--;
				if(curTile.getId() == 3) waterColor = Colors.get(-1, 225, 115, -1);
				else waterColor = Colors.get(-1, 520, 200, -1);
			}	
			else if(30 <= tickCount % 60 && tickCount % 60 < 45){
				if(curTile.getId() == 3) waterColor = Colors.get(-1, 115, -1, 225);
				else waterColor = Colors.get(-1, 200, -1, 520);
			}	
			else {
				yOffset--;
				if(curTile.getId() == 3) waterColor = Colors.get(-1, 225, 115, -1);
				else waterColor = Colors.get(-1, 520, 200, -1);
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
	}

	private void effectsRenderer(Screen screen){
		if(isBlinded) screen.render(screen.xOffset, screen.yOffset, 24 + 0 * 32, Colors.get(000, 000, 000, 000), 0x00, 100);
	}
	
	public boolean isDead(){
		return isDead;
	}
}
