package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Field extends GuiElement {
	private BufferedImage img;
	private String tileID;

	public Field(int x, int y) {
		setX(x);
		setY(y);
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		g2d.drawLine(getX(), getY(), getX() + getWidth(), getY());
		g2d.drawLine(getX(), getY(), getX(), getY() + getHeight());
		g2d.drawLine(getX() + getWidth(), getY(), getX() + getWidth(), getY() + getHeight());
		g2d.drawLine(getX(), getY() + getHeight(), getX() + getHeight(), getY() + getHeight());
		if (img != null) {
			g2d.drawImage(img, getX(), getY(), null);
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		if (this.needUpdate()){
			if (img != null){
				try {
					img = GuiElement.getScaledImage(img, getWidth(), getHeight());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.setUpdate(false);
		}
	}

	public void setImg(BufferedImage img) {
		try {
			this.img = GuiElement.getScaledImage(img, getWidth(), getHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setTileID(String tileID) {
		this.tileID = tileID;
	}

	public String getTileID() {
		return tileID;
	}

}
