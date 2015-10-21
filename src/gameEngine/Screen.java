package gameEngine;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;

import Utilitys.Point;
import entity.AbstractEntity;
import gui.GuiElement;

public abstract class Screen {
	private int x, y, w, h;
	private final ScreenFactory screenFactory;
	private ArrayList<GuiElement> guiElems = new ArrayList<GuiElement>();
	
	public Screen(ScreenFactory screenFactory) {
		this.screenFactory = screenFactory;
	}

	public abstract void onCreate();

	public void onUpdate(){		
		Collections.sort(guiElems);
		for (GuiElement guiElem : guiElems){
			guiElem.onUpdate(this);
		}
	}

	public void onDraw(Graphics2D g2d){
		try{
			for (GuiElement guiElem : guiElems){
				guiElem.onDraw(g2d);
			}
		}catch(ConcurrentModificationException e){
			// I guess that's a multithreading problem.

		}
	}

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

	public static Point pointToMapConst(int x, int y) {
		return new Point(x / 25, y / 25);
	}
	
	public void addGuiElement(GuiElement guielem){
		guiElems.add(guielem);
	}
}
