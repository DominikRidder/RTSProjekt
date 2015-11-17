package gui;

import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.Point;

public class GridLayout implements ILayout, CoordinateMapping {

	private GuiElement[][] container;

	private Panel parent;

	public GridLayout(int columns, int rows, Panel parent) {
		container = new GuiElement[rows][columns];
		this.parent = parent;
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < container[0].length; j++) {
				if (container[i][j] != null) {
					container[i][j].onDraw(g2d);
				}
			}
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();
		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < container[0].length; j++) {
				if (container[i][j] != null) {
					container[i][j].onUpdate(screen);
				}
			}
		}
	}

	@Override
	public void addElement(GuiElement element) {
		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < container[0].length; j++) {
				if (container[i][j] == null) {
					container[i][j] = element;
					element.setX(parent.getX() + parent.getWidth() / container.length * i);
					element.setY(parent.getY() + parent.getHeight() / container[0].length * j);
					element.setWidth(parent.getWidth() / container.length);
					element.setHeight(parent.getHeight() / container[0].length);
					return;
				}
			}
		}
		throw new RuntimeException("The Layout container is already full!");
	}

	public void addAbsoluteElement(GuiElement element) {
		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < container[0].length; j++) {
				if (container[i][j] == null) {
					container[i][j] = element;
					element.setX(parent.getX() + element.getX());// container.length
																	// * i);
					element.setY(parent.getY() + element.getY());// container[0].length
																	// * j);
					return;
				}
			}
		}
		throw new RuntimeException("The Layout container is already full!");
	}

	public Point getCoordinate(int x, int y) {
		int px = (x - parent.getX()) / (parent.getWidth() / container.length);
		int py = (y - parent.getY()) / (parent.getHeight() / container[0].length);

		if (px >= 0 && px < container.length && py >= 0 && py < container[0].length) {
			return new Point(px, py);
		} else {
			return null;
		}
	}

	public GuiElement getElement(int x, int y) {
		return container[x][y];
	}

}
