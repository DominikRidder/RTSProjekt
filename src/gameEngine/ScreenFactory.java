package gameEngine;
public class ScreenFactory {

	private final Game game;
	private Screen screen = null;
	private Screen lastScreen = null;

	public ScreenFactory(Game game) {
		this.game = game;
	}

	public void showScreen(Screen newscreen) {
		this.lastScreen = screen;
		this.screen = newscreen;
		this.screen.onCreate();
	}
	
	/**
	 * tauscht lastScreen und Screen, DARF KEIN ONCREATE AUFRUFEN!
	 */
	public void swap()
	{
		Screen tmp = screen;
		screen = lastScreen;
		lastScreen = tmp;
	}
	
	public Screen getPrevScreen() {
		return lastScreen;
	}

	public Screen getCurrentScreen() {
		return screen;
	}

	public Game getGame() {
		return game;
	}

}
