package gameEngine;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import entity.AbstractEntity;

public abstract class Screen {
	private int x, y, w, h;
	private final ScreenFactory screenFactory;

	public Screen(ScreenFactory screenFactory) {
		this.screenFactory = screenFactory;
	}

	public abstract void onCreate();

	public abstract void onUpdate();

	public abstract void onDraw(Graphics2D g2d);

	public ScreenFactory getScreenFactory() {
		return screenFactory;
	}

	public abstract ArrayList<AbstractEntity> getEntitys();

	public Rectangle getDraggingZone() {
		return new Rectangle(x, y, w, h);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
}
