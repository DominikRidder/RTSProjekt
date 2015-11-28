package gui;

import gameEngine.Game;
import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import dataManagement.ImageManager;

public class Field extends GuiElement {
	private BufferedImage img;
	private String imgname;
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
			if (imgname != null){
				img = Game.getImageManager().getImage(imgname, getWidth(), getHeight());
			}
			this.setUpdate(false);
		}
	}

	public void setImg(String imgname) {
		img = Game.getImageManager().getImage(imgname, getWidth(), getHeight());
		this.imgname = imgname;
	}
	
	@Deprecated
	public void setImg(BufferedImage img) {
		if (img.getWidth() != getWidth() || img.getHeight() != getHeight()){
			try {
				this.img = ImageManager.getScaledImage(img, getWidth(), getHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			this.img = img;
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
