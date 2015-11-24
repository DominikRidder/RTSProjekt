package gameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyboardListener implements KeyListener {

	private final boolean[] keys = new boolean[256];

	private ArrayList<Integer> ispressed = new ArrayList<Integer>();

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
		ispressed.remove(new Integer(event.getKeyCode()));
	}

	@Override
	public void keyTyped(KeyEvent event) {

	}

	public boolean isKeyPressed(int key) {
		return keys[key];
	}

	public boolean isKeyReleased(int key) {
		return !keys[key];
	}

	public ArrayList<Integer> getPressedKeys() {
		return ispressed;
	}
}
