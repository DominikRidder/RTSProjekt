package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;

public class Panel extends GuiElement {

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

}
