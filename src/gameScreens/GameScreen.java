package gameScreens;

import entity.AbstractEntity;
import entity.OrkTest;
import entity.Tree;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import Utilitys.Point;

public class GameScreen extends Screen implements ActionListener {

	// private final List<AbstractEntity> entitys = new
	// LinkedList<AbstractEntity>();
	private final HashMap<AbstractEntity, Point> entitysOnMap = new HashMap<AbstractEntity, Point>();

	private int viewX, viewY;

	private Button exit;
	private int lastRightClick = 0;
	private int lastRightClickX = 0;
	private int lastRightClickY = 0;

	private int savedX;
	private int savedY;
	private int savedMarkX;
	private int savedMarkY;

	/**************************/
	/****** Dummy Targets *******/

	private final int mapwidth = 2000;
	private final int mapheight = 2000;

	/*************************/

	public GameScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		Random rnd = new Random();
		System.out.println("Main Creating!");
		List<AbstractEntity> entitys = AbstractEntity.getEntities();
		for (int i = 0; i < 10; i++) {
			entitys.add(new OrkTest(rnd.nextInt(700) + 40,
					rnd.nextInt(500) + 40, i % 8));// TODO get(i) funktion
													// vermeiden, weil
													// LinkedList
			entitys.add(new Tree(rnd.nextInt(700) + 40, rnd.nextInt(500) + 40,
					rnd.nextInt(2)));
			entitysOnMap.put(
					entitys.get(i),
					pointToMapConst(entitys.get(i).getX(), entitys.get(i)
							.getY()));
		}
		exit = new Button(this.getScreenFactory().getGame().getWindow()
				.getWidth() - 50, 0, 50, 50, "X");
		exit.setBackgroundColor(Color.BLACK);
		exit.setTextColor(Color.RED);
		exit.addActionListener(this);
		addGuiElement(exit);
	}

	@Override
	public void onUpdate() {
		MousepadListener mpl = this.getScreenFactory().getGame()
				.getMousepadListener();
		if (mpl.getCurrentX() > getScreenFactory().getGame().getWindow()
				.getWidth() - 30
				|| mpl.getCurrentX() - 30 < 0) {
			viewX += mpl.getCurrentX() > getScreenFactory().getGame()
					.getWindow().getWidth() / 2 ? 6 : -6;
		}
		if (mpl.getCurrentY() > getScreenFactory().getGame().getWindow()
				.getHeight() - 60
				|| mpl.getCurrentY() - 30 < 0) {
			viewY += mpl.getCurrentY() > getScreenFactory().getGame()
					.getWindow().getWidth() / 2 ? 6 : -6;
		}
		int wwidth = getScreenFactory().getGame().getWindow().getWidth();
		int wheight = getScreenFactory().getGame().getWindow().getHeight();

		if (viewX > mapwidth - wwidth) {
			viewX = mapwidth - wwidth;
		} else if (viewX < 0) {
			viewX = 0;
		}
		if (viewY > mapheight - wheight) {
			viewY = mapheight - wheight;
		} else if (viewY < 0) {
			viewY = 0;
		}

		mpl.setX(mpl.getX() + viewX);
		mpl.setY(mpl.getY() + viewY);
		mpl.setMarkX(mpl.getMarkX() + viewX);
		mpl.setMarkY(mpl.getMarkY() + viewY);

		savedX = mpl.getX();
		savedY = mpl.getY();
		if (mpl.isDragging()) {
			savedMarkX = mpl.getMarkX();
			savedMarkY = mpl.getMarkY();
		} else {
			savedMarkX = savedX;
			savedMarkY = savedY;
		}

		super.onUpdate();

		Collections.sort(getEntitys());// In diese Zeile hab ich so viel
										// hirnschmalz verbraten
		for (int i = 0; i < getEntitys().size(); i++) {
			getEntitys().get(i).update(this);
		}
		for (int i = 0; i < getEntitys().size(); i++) {
			if (getEntitys().get(i).checkDeath())// Toete tote
				i--;// liste wird kleiner
		}
		for (Entry<AbstractEntity, Point> entry : entitysOnMap.entrySet()) {
			AbstractEntity key = entry.getKey();
			entry.setValue(pointToMapConst(key.getX(), key.getY()));
		}

		mpl.setX(mpl.getX() - viewX);
		mpl.setY(mpl.getY() - viewY);
		mpl.setMarkX(mpl.getMarkX() - viewX);
		mpl.setMarkY(mpl.getMarkY() - viewY);
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		MousepadListener mpl = this.getScreenFactory().getGame()
				.getMousepadListener();

		if (exit != null) { // Later we will just move a Panel, containing the
							// HUD
			exit.setX(this.getScreenFactory().getGame().getWindow().getWidth()
					- 50 + viewX);
			exit.setY(viewY);
		}

		AffineTransform transform = new AffineTransform();
		transform.translate(-viewX, -viewY);
		g2d.transform(transform);

		/*
		 * for (AbstractEntity e : AbstractEntity.getEntities()) {//linkedList
		 * performance plus e.draw(g2d); }
		 */
		for (int i = 0; i < getEntitys().size(); i++) {
			getEntitys().get(i).draw(g2d);
		}
		for (int i = 0; i < getEntitys().size(); i++) {
			getEntitys().get(i).markOwn(g2d);
		}

		if (mpl.isDragging()) {
			if (savedMarkX < savedX) {
				setX(savedMarkX);
			} else {
				setX(savedX);
			}
			if (savedMarkY < savedY) {
				setY(savedMarkY);
			} else {
				setY(savedY);
			}
			setW(Math.abs(savedX - savedMarkX));
			setH(Math.abs(savedY - savedMarkY));
			drawDraggingZone(g2d);
		}
		if (mpl.isRightClicked()) {
			lastRightClick = 100;
			lastRightClickX = savedX;
			lastRightClickY = savedY;
		}
		if (lastRightClick != 0) {
			drawRightClick(g2d, lastRightClickX, lastRightClickY);
			lastRightClick--;
		}
		super.onDraw(g2d);
	}

	public void drawRightClick(Graphics2D g2d, int x, int y) {
		g2d.drawLine(x - (int)(6*lastRightClick/100.), y - (int)(3*lastRightClick/100.), x + (int)(6*lastRightClick/100.), y + (int)(3*lastRightClick/100.));
		g2d.drawLine(x + (int)(6*lastRightClick/100.), y - (int)(3*lastRightClick/100.), x - (int)(6*lastRightClick/100.), y + (int)(3*lastRightClick/100.));
	}

	public void drawDraggingZone(Graphics2D g2d) {
		if (this.getScreenFactory().getGame().getMousepadListener()
				.isDragging()
				&& this.getScreenFactory().getGame().getMousepadListener()
						.isLeftClicked()) {
			if (this.getScreenFactory().getGame().getMousepadListener()
					.getMarkX() != -1
					&& this.getScreenFactory().getGame().getMousepadListener()
							.getMarkY() != -1) {
				g2d.drawRect(getX(), getY(), getW(), getH());
			}
		}
	}

	@Override
	public List<AbstractEntity> getEntitys() {
		// return entitys;
		return AbstractEntity.getEntities();
	}

	public HashMap<AbstractEntity, Point> getEntityWithMap() {
		return entitysOnMap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) { // name of the button
		case "X":
			this.getScreenFactory().showScreen(new SettingsScreen(this.getScreenFactory()));
			break;
		default:
			System.out.println("Unknown ActionEvent: " + e.getActionCommand());
			break;
		}
	}

}
