package gameEngine;

import entity.AbstractEntity;
import gui.GuiElement;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import Utilitys.Point;

public abstract class Screen {
	private int x, y, w, h;
	private final ScreenFactory screenFactory;
	private final ArrayList<GuiElement> guiElemens = new ArrayList<GuiElement>();

	public Screen(ScreenFactory screenFactory) {
		this.screenFactory = screenFactory;
	}

	public abstract void onCreate();

	public void onUpdate() {
		if (guiElemens.size() != 0) {
			for (GuiElement guiElem : guiElemens) {
				guiElem.onUpdate(this);
			}
		}
	}

	public void onDraw(Graphics2D g2d) {
		if (guiElemens.size() != 0) {
			try {
				for (GuiElement guiElem : guiElemens) {
					guiElem.onDraw(g2d);
				}
			} catch (ConcurrentModificationException e) {
				// I guess that's a multithreading problem.

			}
		}
	}

	public ScreenFactory getScreenFactory() {
		return screenFactory;
	}

	public abstract List<AbstractEntity> getEntitys();

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

	public static Point pointToMapConst(int x, int y) {
		return new Point(x / 25, y / 25);
	}

	public void addGuiElement(GuiElement guielem) {
		guiElemens.add(guielem);
		updateGuiElementOrder();
	}

	public void updateGuiElementOrder() {
		Collections.sort(guiElemens);
	}
}
