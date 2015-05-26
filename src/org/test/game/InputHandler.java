package org.test.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

public class InputHandler implements KeyListener{
	private final int size = 21;
	private static final String path = InputHandler.class.getResource("/cfg/keys.txt").getFile().replaceAll("%20", " ").substring(1);
	private Map<String, Key> keys;
	
	public InputHandler(Game game){
		game.addKeyListener(this);
		loadKeys();
	}
	
	public class Key{
		private int numTimesPressed = 0;
		private boolean pressed = false;
		private boolean released = false;
		private int keycode;
		
		public Key(int keycode){
			this.keycode = keycode;
		}
		
		public int getNumTimesPressed(){
			return numTimesPressed;
		}
		
		public boolean isPressed(){
			return pressed;
		}
		
		public boolean isReleased(){
			if(released){
				released = false;
				return true;
			}
			
			return false;
		}
		
		public void setKeycode(int keycode){
			this.keycode = keycode;
		}
		
		public int getKeycode(){
			return keycode;
		}
		
		public void toggle(boolean isPressed){
			pressed = isPressed;
			released = !isPressed;
			if(pressed) numTimesPressed++;
		}
	}
	
	
	
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	public void toggleKey(int keyCode, boolean isPressed){
		for(Key key : keys.values()){
			if(key.keycode == keyCode) key.toggle(isPressed);
		}
	}
	
	private int getKeyCode(Scanner sc){
		return Integer.parseInt(sc.nextLine().split("=")[1]);
	}
	
	private void loadKeys(){
		keys = new HashMap<String, Key>();
		
		try {
			File file = new File(path);
			Scanner sc = new Scanner(file);
			
			while(sc.hasNextLine()){
				String[] rawText = sc.nextLine().split("=");
				String keyName = rawText[0];
				int keyCode = Integer.parseInt(rawText[1]);
				
				keys.put(keyName, new Key(keyCode));
			}
			
			if(keys.size() != size) throw new Exception("Invalid Keys");
			
			System.out.println("Keys loaded");
			
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			keys.clear();
			keys.put("Up", new Key(KeyEvent.VK_UP));
			keys.put("Down", new Key(KeyEvent.VK_DOWN));
			keys.put("Left", new Key(KeyEvent.VK_LEFT));
			keys.put("Right", new Key(KeyEvent.VK_RIGHT));
			keys.put("Msg", new Key(KeyEvent.VK_ENTER));
			keys.put("Menu", new Key(KeyEvent.VK_ESCAPE));
			keys.put("Inventory", new Key(KeyEvent.VK_F));
			keys.put("Inventory1", new Key(KeyEvent.VK_1));
			keys.put("Inventory2", new Key(KeyEvent.VK_2));
			keys.put("Inventory3", new Key(KeyEvent.VK_3));
			keys.put("Inventory4", new Key(KeyEvent.VK_4));
			keys.put("Inventory5", new Key(KeyEvent.VK_5));
			keys.put("Abillity1", new Key(KeyEvent.VK_Q));
			keys.put("Abillity2", new Key(KeyEvent.VK_W));
			keys.put("Abillity3", new Key(KeyEvent.VK_E));
			keys.put("Abillity4", new Key(KeyEvent.VK_R));
			keys.put("Next Item", new Key(KeyEvent.VK_D));
			keys.put("Prev Item", new Key(KeyEvent.VK_A));
			keys.put("Menu NavUp", new Key(KeyEvent.VK_UP));
			keys.put("Menu NavDown", new Key(KeyEvent.VK_DOWN));
			keys.put("Enter", new Key(KeyEvent.VK_ENTER));
			
			saveKeys();
		}
	}
	
	public void saveKeys(){
		try{
			PrintWriter pw = new PrintWriter(path);
			
			for(Map.Entry<String, Key> element : keys.entrySet()){
				pw.println(element.getKey() + "=" + element.getValue().getKeycode());
			}
			
			System.out.println("Keys saved");
			
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Map<String, Key> getKeys(){
		return keys;
	}
	
	public Key get(String key){
		return keys.get(key);
	}
}
