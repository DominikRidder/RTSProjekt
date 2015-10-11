package gameEngine;

import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Game {

	private JFrame window = new JFrame();
	private ScreenFactory screenFactory;
	private GameThread gameThread;
	private KeyboardListener keyboardListener;
	private MousepadListener mousepadListener;

	public Game(int windowX, int windowY, String title) {
		window.setSize(windowX, windowY);
		window.setTitle(title);
		init();
		new Thread(gameThread).start();
	}

	public Game(String title) {
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setUndecorated(true);
		window.setTitle(title);
		init();
		new Thread(gameThread).start();
	}

	private void init() {
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setFocusable(true);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		screenFactory = new ScreenFactory(this);
		gameThread = new GameThread(this);
		keyboardListener = new KeyboardListener();
		mousepadListener = new MousepadListener();

		window.add(gameThread);
		window.addKeyListener(keyboardListener);
		window.addMouseListener(mousepadListener);
		window.addMouseMotionListener(mousepadListener);
	}

	public MousepadListener getMousepadListener() {
		return mousepadListener;
	}

	public KeyListener getKeyboardListener() {
		return keyboardListener;
	}

	public ScreenFactory getScreenFactory() {
		return screenFactory;
	}

	public JFrame getWindow() {
		return window;
	}

}
