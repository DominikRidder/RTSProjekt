package gameScreens;

import entity.AbstractEntity;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MainScreen extends Screen {

	public MainScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onUpdate() {
		MousepadListener mpl = this.getScreenFactory().getGame().getMousepadListener();
		if (mpl.isLeftClicked()) {
			if (mpl.getX() > 325 && mpl.getX() < 475 && mpl.getY() > 275 && mpl.getY() < 325) {
				this.getScreenFactory().showScreen(new GameScreen(this.getScreenFactory()));
			}
		}

	}

	@Override
	public void onDraw(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fill3DRect(325, 275, 150, 50, true);
		g2d.setColor(Color.WHITE);
		g2d.drawString("Spiel Starten", 350, 300);
	}

	@Override
	public ArrayList<AbstractEntity> getEntitys() {
		return null;
	}

}
