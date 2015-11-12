package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;

public class Field extends GuiElement {
	public Field(int x, int y) {
		setX(x);
		setY(y);
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		g2d.drawLine(getX(), getY(), getX() + 16, getY());
		g2d.drawLine(getX(), getY(), getX(), getY() + 16);
		g2d.drawLine(getX() + 16, getY(), getX() + 16, getY() + 16);
		g2d.drawLine(getX(), getY() + 16, getX() + 16, getY() + 16);
	}

	@Override
	public void onUpdate(Screen screen) {
		// TODO Auto-generated method stub

	}

}
