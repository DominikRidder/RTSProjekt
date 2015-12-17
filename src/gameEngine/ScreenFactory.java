package gameEngine;
public class ScreenFactory {

	private final Game game;
	private Screen screen = null;

	public ScreenFactory(Game game) {
		this.game = game;
	}
	
	public void activeScreen(Screen alreadyexistingscreen)
	{
		this.screen = alreadyexistingscreen;
	}

	public void createScreen(Screen newscreen) {
		newscreen.onCreate(); // Create
		this.screen = newscreen;  // than draw
	}

	public Screen getCurrentScreen() {
		return screen;
	}

	public Game getGame() {
		return game;
	}

}
