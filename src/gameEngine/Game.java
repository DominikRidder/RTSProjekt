package gameEngine;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import dataManagement.ImageManager;
import dataManagement.InfoManager;
import dataManagement.SettingsManager;

public class Game {
	private BufferStrategy strat;
	private final JFrame window = new JFrame();
	private ScreenFactory screenFactory;
	private GameThread gameThread;
	private MusikThread musikThread;
	private KeyboardListener keyboardListener;
	private MousepadListener mousepadListener;
	
	private static ImageManager imagemanager;
	private static InfoManager infomanager;
	private static SettingsManager settings;
	
	public Game(int windowX, int windowY, String title) {
		window.setSize(windowX, windowY);
		window.setTitle(title);
		init();
		new Thread(gameThread).start();
		new Thread(musikThread).start();
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

		imagemanager = new ImageManager();
		infomanager = new InfoManager();
		settings = new SettingsManager();
		
		screenFactory = new ScreenFactory(this);
		gameThread = new GameThread(this);
		
		
			musikThread = new MusikThread();
			window.add(musikThread);
		
		keyboardListener = new KeyboardListener();
		mousepadListener = new MousepadListener();
		
		window.add(gameThread);
		window.addKeyListener(keyboardListener);

		gameThread.addMouseListener(mousepadListener);
		gameThread.addMouseMotionListener(mousepadListener);

		window.setVisible(true);
	}

	public MousepadListener getMousepadListener() {
		return mousepadListener;
	}

	public KeyboardListener getKeyboardListener() {
		return keyboardListener;
	}

	public ScreenFactory getScreenFactory() {
		return screenFactory;
	}

	public JFrame getWindow() {
		return window;
	}
	
	public static ImageManager getImageManager(){
		return imagemanager;
	}

	public static InfoManager getInfoManager(){
		return infomanager;
	}
	
	public static SettingsManager getSetting(){
		return settings;
	}
}
