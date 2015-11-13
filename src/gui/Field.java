package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Field extends GuiElement {
	private BufferedImage img;

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
		if (img != null) {
			g2d.drawImage(img, getX(), getY(), null);
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		// TODO Auto-generated method stub

	}

}
