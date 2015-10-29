package gameScreens;

import gameEngine.Game;

public class RTS {

	private final Game game;

	public RTS() {
		game = new Game(840, 640, "Real-Time Strategy of Doom");
		game.getScreenFactory().showScreen(new MainScreen(game.getScreenFactory()));
	}

	public static void main(String[] args) {
		new RTS();
	}
}
