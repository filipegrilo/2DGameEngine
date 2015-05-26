package org.test.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import org.test.game.entities.Player;
import org.test.gfx.Colors;
import org.test.gfx.Screen;
import org.test.gfx.SpriteSheet;
import org.test.items.Item;
import org.test.level.Level;
import org.test.menu.MainMenu;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 310;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 4;
	public static final String NAME = "Game";
	
	private JFrame frame;
	
	private boolean isRunning = false;
	public int tickCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colors = new int[6*6*6];
	
	private Screen screen;
	public InputHandler input;
	public MainMenu mainMenu;
	public static Level level;
	public Player player;
	
	public Game(){
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init() throws CloneNotSupportedException{
		int index = 0;
		
		for(int r=0; r < 6; r++){
			for(int g=0; g < 6; g++){
				for(int b=0; b < 6; b++){
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					
					colors[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}
		
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/SpriteSheet.png"));
		input = new InputHandler(this);
		mainMenu = new MainMenu("Menu", 20, 20, Game.WIDTH - 40, Game.HEIGHT - 40, Colors.get(-1, -1, -1, 333), Colors.get(-1, -1, -1, 300));
		mainMenu.createMenuItems(input);
		level = new Level("/Levels/WaterLevelTest.png");
		player = new Player(level, 10, 10, input);
		level.addEntity(player);
		level.addItem((Item)Item.chest.clone(), 20, 20);
	}
	
	public synchronized void start() {
		isRunning = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		isRunning = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int frames = 0;
		int ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		try {
			init();
		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1){
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(shouldRender){
				frames++;
				render();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				System.out.println(frames+" frames, "+ticks+" ticks");
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		int xOffset = player.x - (screen.width / 2);
		int yOffset = player.y - (screen.height / 2);
		
		level.renderTiles(screen, xOffset, yOffset);
		level.renderItems(screen);
		level.renderEntities(screen);
		
		if(mainMenu.show) mainMenu.render(screen);
		
		for(int y=0; y < screen.height; y++){
			for(int x=0; x < screen.width; x++){
				int colorCode = screen.pixels[x+y * screen.width];
				if(colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public void tick(){
		tickCount++;
		
		if(input.get("Menu").isReleased()){
			mainMenu.toggle();
			mainMenu.resetSelected();
		}
		
		if(mainMenu.show) mainMenu.tick(input);
		else{
			level.tick();
		}
	}
	
	public static void main(String[] args){
		new Game().start();
	}
}
