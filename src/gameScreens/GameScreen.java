package gameScreens;

import entity.AbstractEntity;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class GameScreen extends Screen {

	private final ArrayList<AbstractEntity> entitys = new ArrayList<AbstractEntity>();
	private final HashMap<AbstractEntity, Integer> entitysOnMap = new HashMap<AbstractEntity, Integer>();

	public GameScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		Random rnd = new Random();
		System.out.println("Main Creating!");
		for (int i = 0; i < 20; i++) {
			entitys.add(new OrkTest(rnd.nextInt(700) + 40, rnd.nextInt(500) + 40));
			entitysOnMap.put(entitys.get(i), pointToMapConst(entitys.get(i).getX(), entitys.get(i).getY()));
		}
	}

	@Override
	public void onUpdate() {
		for (int i = 0; i < entitys.size(); i++) {
			entitys.get(i).update(this);
		}
		for (Entry<AbstractEntity, Integer> entry : entitysOnMap.entrySet()) {
			AbstractEntity key = entry.getKey();
			entry.setValue(pointToMapConst(key.getX(), key.getY()));
		}

	}

	@Override
	public void onDraw(Graphics2D g2d) {
		for (int i = 0; i < entitys.size(); i++) {
			entitys.get(i).draw(g2d);
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

	public void drawDraggingZone(Graphics2D g2d) {
		if (this.getScreenFactory().getGame().getMousepadListener().isDragging() && this.getScreenFactory().getGame().getMousepadListener().isLeftClicked()) {
			if (this.getScreenFactory().getGame().getMousepadListener().getMarkX() != -1 && this.getScreenFactory().getGame().getMousepadListener().getMarkY() != -1) {
				g2d.drawRect(getX(), getY(), getW(), getH());
			}
		}
	}

	@Override
	public ArrayList<AbstractEntity> getEntitys() {
		return entitys;
	}

	public HashMap<AbstractEntity, Integer> getEntityWithMap() {
		return entitysOnMap;
	}

	public static int pointToMapConst(int x, int y) {
		int a = x / 25;
		int b = y / 25;
		return a + b * 10;
	}

}