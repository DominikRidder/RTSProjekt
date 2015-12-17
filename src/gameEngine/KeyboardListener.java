package gameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyboardListener implements KeyListener {

	private final boolean[] keys = new boolean[256];
	private final boolean[] postfire = new boolean[256];

	private final ArrayList<Integer> ispressed = new ArrayList<Integer>();

	@Override
	public void keyPressed(KeyEvent event) {
		keys[event.getKeyCode()] = true;
		if (!ispressed.contains(event.getKeyCode())) {
			ispressed.add(event.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		keys[event.getKeyCode()] = false;
		postfire[event.getKeyCode()] = true;
		ispressed.remove(new Integer(event.getKeyCode()));
	}

	@Override
	public void keyTyped(KeyEvent event) {

	}
	
	/**
	 * @param Gets the Button Name as input, returns if it's pressed or not: example: btn_ESC
	 * returns true if is On Press, otherwise false
	 * @return
	 */
	public boolean isOnPress(String s)
	{
		return isOnPress(Game.getSetting().getValueInt(s));
	}
	
	public boolean isOnPress(int key)
	{
		if(postfire[key])
		{
			postfire[key] = false;
			return true;
		}
		return false;
	}

	public boolean isKeyPressed(String s)
	{
		return isKeyPressed(Game.getSetting().getValueInt(s));
	}
	public boolean isKeyPressed(int key) {
		return keys[key];
	}
	
	public boolean isKeyReleased(String s)
	{
		return isKeyReleased(Game.getSetting().getValueInt(s));
	}

	public boolean isKeyReleased(int key) {
		return !keys[key];
	}

	public ArrayList<Integer> getPressedKeys() {
		return ispressed;
	}
}
