package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;

public class GridLayout implements ILayout {

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
					element.setX(parent.getX() + element.getX());// container.length * i);
					element.setY(parent.getY() + element.getY());// container[0].length * j);
					return;
				}
			}
		}
		throw new RuntimeException("The Layout container is already full!");
	}
}
