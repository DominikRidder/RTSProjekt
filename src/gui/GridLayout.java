package gui;

import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.awt.Graphics2D;
import Utilitys.Point;

public class GridLayout implements ILayout, CoordinateMapping {

	private GuiElement[][] container;

	private APanel parent;

	public GridLayout(int rows, int columns, APanel parent) {
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
		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < container[0].length; j++) {
				if (container[i][j] != null) {
					container[i][j].drawBorder(g2d);
				}
			}
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();

		if (parent.needUpdate()) {
			resizeElements();
		}

		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < container[0].length; j++) {
				if (container[i][j] != null) {
					container[i][j].onUpdate(screen);
				}
			}
		}
	}

	private void resizeElements() {
		GuiElement[][] copy = container;
		container = new GuiElement[copy.length][copy[0].length];

		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[0].length; j++) {
				if (copy[i][j] != null) {
					this.addElement(copy[i][j]);
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
					element.setX(parent.getX() + parent.getWidth() / container[0].length * j);
					element.setY(parent.getY() + parent.getHeight() / container.length * i);
					element.setWidth(parent.getWidth() / container[0].length);
					element.setHeight(parent.getHeight() / container.length);
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
		int px = (x - parent.getX()) / (parent.getWidth() / container[0].length);
		int py = (y - parent.getY()) / (parent.getHeight() / container.length);

		if (px >= 0 && px < container[0].length && py >= 0 && py < container.length) {
			return new Point(px, py);
		} else {
			return null;
		}
	}

	public GuiElement getElement(int x, int y) {
		return container[y][x];
	}

	public int getColumnSize() {
		return container[0].length;
	}

	public int getRowSize() {
		return container.length;
	}

	public void setElement(GuiElement elem, int x, int y) {
		container[y][x] = elem;
	}

}
