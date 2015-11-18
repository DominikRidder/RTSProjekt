package gui;

import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Label extends GuiElement {
	private String text;
	private Color textcolor = Color.WHITE, backgroundcolor = Color.BLACK;
	private BufferedImage image;
	private boolean ImageSet = false;

	public Label(int x, int y, BufferedImage image) {
		setX(x);
		setY(y);
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		this.image = image;
		ImageSet = true;
	}

	public Label(int x, int y, int width, int height, String text) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.text = text;
	}

	public Label(String text) {
		this.text = text;
	}

	public Label(BufferedImage img) {
		this.image = img;
		ImageSet = true;
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}

	public void setImage(BufferedImage img) {
		this.image = img;
		ImageSet = true;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTextColor(Color c) {
		textcolor = c;
	}

	public void setBackgroundColor(Color c) {
		backgroundcolor = c;
	}

	public void onDraw(Graphics2D g2d) {
		int width = getWidth();
		int height = getHeight();

		if (ImageSet) {
			g2d.drawImage(image, getX(), getY(), width, height, null);
		} else {
			int stringwidth = g2d.getFontMetrics().stringWidth(text);
			int stringheight = g2d.getFontMetrics().getHeight();
			g2d.setColor(backgroundcolor);
			g2d.fill3DRect(getX(), getY(), width, height, true);
			g2d.setColor(textcolor);
			g2d.drawString(text, getX() + width / 2 - stringwidth / 2, getY() + height / 2 + stringheight / 2);
		}
	}

	public void onUpdate(Screen screen) {
	}

	public BufferedImage getImage() {
		return image;
	}

}
