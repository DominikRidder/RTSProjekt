package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.Point;

public class Panel extends GuiElement implements CoordinateMapping {

	private ILayout layout;

	public Panel(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
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
				throw new RuntimeException(
						"Layout don't support the CoordinateMapping!");
			}
		}
		return null;
	}
}
