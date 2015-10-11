package gameScreens;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import entity.Unit;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;

public class MainScreen extends Screen {

	private ArrayList<Unit> units = new ArrayList<Unit>();

	public MainScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		Random rnd = new Random();
		System.out.println("Main Creating!");
		for (int i = 0; i < 10; i++) {
			units.add(new OrkTest(rnd.nextInt(700) + 40, rnd.nextInt(500) + 40));
		}

	}

	@Override
	public void onUpdate() {
		for (int i = 0; i < units.size(); i++) {
			units.get(i).update(this);
		}
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		for (int i = 0; i < units.size(); i++) {
			units.get(i).draw(g2d);
		}
		MousepadListener mpl = this.getScreenFactory().getGame().getMousepadListener();
		if (mpl.isDragging()) {
			if (mpl.getMarkX() < mpl.getX()) {
				setX(mpl.getMarkX());
			} else {
				setX(mpl.getX());
			}
			if (mpl.getMarkY() < mpl.getY()) {
				setY(mpl.getMarkY());
			} else {
				setY(mpl.getY());
			}
			setW(Math.abs(mpl.getX() - mpl.getMarkX()));
			setH(Math.abs(mpl.getY() - mpl.getMarkY()));
			drawDraggingZone(g2d);
		}

	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void drawDraggingZone(Graphics2D g2d) {
		if (this.getScreenFactory().getGame().getMousepadListener().isDragging() && this.getScreenFactory().getGame().getMousepadListener().isLeftClicked()) {
			if (this.getScreenFactory().getGame().getMousepadListener().getMarkX() != -1 && this.getScreenFactory().getGame().getMousepadListener().getMarkY() != -1) {
				g2d.drawRect(getX(), getY(), getW(), getH());
			}
		}
	}

}
