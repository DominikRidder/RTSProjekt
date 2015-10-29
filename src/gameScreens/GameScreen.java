package gameScreens;

import entity.AbstractEntity;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import Utilitys.Point;

public class GameScreen extends Screen implements ActionListener{

	private final ArrayList<AbstractEntity> entitys = new ArrayList<AbstractEntity>();
	private final HashMap<AbstractEntity, Point> entitysOnMap = new HashMap<AbstractEntity, Point>();

	public GameScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		Random rnd = new Random();
		System.out.println("Main Creating!");
		for (int i = 0; i < 10; i++) {
			entitys.add(new OrkTest(rnd.nextInt(700) + 40, rnd.nextInt(500) + 40));
			entitysOnMap.put(entitys.get(i), pointToMapConst(entitys.get(i).getX(), entitys.get(i).getY()));
		}
		Button exit = new Button(this.getScreenFactory().getGame().getWindow().getWidth()-50, 0, 50, 50, "X");
		exit.setBackgroundColor(Color.BLACK);
		exit.setTextColor(Color.RED);
		exit.addActionListener(this);
		addGuiElement(exit);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		Collections.sort(entitys);// In diese Zeile hab ich so viel hirnschmalz verbraten
		for (int i = 0; i < entitys.size(); i++) {
			entitys.get(i).update(this);
		}
		for (Entry<AbstractEntity, Point> entry : entitysOnMap.entrySet()) {
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
		super.onDraw(g2d);
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

	public HashMap<AbstractEntity, Point> getEntityWithMap() {
		return entitysOnMap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){ // name of the button
		case "X":System.exit(0);break;
		default:System.out.println("Unknown ActionEvent: "+e.getActionCommand()); break;
		}
	}

}
