package gui;

import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Panel extends GuiElement implements CoordinateMapping {

	private ILayout layout;
	private boolean border = false;

	public Panel(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
	}

	public Panel(int x, int y, int width, int height, boolean border) {
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
		setBorder(border);
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public void setLayout(ILayout layout) {
		this.layout = layout;
	}

	public void addElement(GuiElement element) {
		layout.addElement(element);
	}

	public void addAbsoluteElement(GuiElement element) {
		((GridLayout) layout).addAbsoluteElement(element);
	}

	public void onDraw(Graphics2D g2d) {
		layout.onDraw(g2d);
		if (border) {
			g2d.setColor(Color.BLACK);
			g2d.drawLine(getX(), getY(), getX() + getWidth(), getY());
			g2d.drawLine(getX(), getY(), getX(), getY() + getHeight());
			g2d.drawLine(getX() + getWidth(), getY(), getX() + getWidth(), getY() + getHeight());
			g2d.drawLine(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight());
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		layout.onUpdate(screen);
	}

	public Point getCoordinate(int x, int y) {
		if (getBounds().contains(x, y)) {
			if (layout instanceof CoordinateMapping) {
				return ((CoordinateMapping) layout).getCoordinate(x, y);
			} else {
				throw new RuntimeException("Layout don't support the CoordinateMapping!");
			}
		}
		return null;
	}

	public GridLayout getLayout() {
		return (GridLayout) layout;
	}
}
