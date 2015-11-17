package gui;

import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class GuiElement implements Comparable<GuiElement> {

	private ArrayList<ActionListener> actionlistener = new ArrayList<ActionListener>(1);
	private int layer;
	private int x, y;
	private int width, height;
	private boolean border = false;

	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getLayer() {
		return layer;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void addActionListener(ActionListener actionl) {
		actionlistener.add(actionl);
	}

	public ArrayList<ActionListener> getActionListener() {
		return actionlistener;
	}

	public abstract void onDraw(Graphics2D g2d);

	public abstract void onUpdate(Screen screen);

	public int compareTo(GuiElement arg0) {
		if (this.layer > arg0.layer) {
			return 1;
		} else if (this.layer < arg0.layer) {
			return -1;
		} else {
			return 0;
		}
	}

	public void drawBorder(Graphics2D g2d) {
		if (border) {
			g2d.setColor(Color.BLACK);
			g2d.drawLine(getX(), getY(), getX() + getWidth(), getY());
			g2d.drawLine(getX(), getY(), getX(), getY() + getHeight());
			g2d.drawLine(getX() + getWidth(), getY(), getX() + getWidth(), getY() + getHeight());
			g2d.drawLine(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight());
		}
	}

	public void setBorder(boolean border) {
		this.border = border;
	}
}
