package gui;

import gameEngine.Game;
import gameEngine.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Label extends GuiElement {
	private String text;
	//private Color textcolor = Color.YELLOW, backgroundcolor = Color.GRAY;
	private BufferedImage image = Game.getImageManager().getImage("menubutton.png");
	private boolean ImageSet = false;
	private Font font;

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
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void turnOffDecorator() {
		image = null;
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		int width = getWidth();
		int height = getHeight();
		Font swap = null;
		
		if (ImageSet) {
			g2d.drawImage(image, getX(), getY(), width, height, null);
		} else {
			if (font != null) {
				swap = g2d.getFont();
				g2d.setFont(font);
			}
			int stringwidth = g2d.getFontMetrics().stringWidth(text);
			int stringheight = g2d.getFontMetrics().getHeight();
			g2d.setColor(backgroundcolor);
			if(image != null){
				g2d.drawImage(image, getX(), getY(), width, height, null);
			}else{
				g2d.fill3DRect(getX(), getY(), width, height, true);
			}
			g2d.setColor(textcolor);
			g2d.drawString(text, getX() + width / 2 - stringwidth / 2,
						getY() + height / 2 + stringheight / 2);
			if (swap != null){
				g2d.setFont(swap);
			}
		}
	}

	@Override
	public void onUpdate(Screen screen) {
	}

	public BufferedImage getImage() {
		return image;
	}

}
