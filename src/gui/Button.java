package gui;

import gameEngine.Game;
import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Button extends GuiElement {

	private String text;
	//private Color textcolor = Color.YELLOW, backgroundcolor = Color.GRAY;
	private boolean ispressed = false;
	private BufferedImage image = Game.getImageManager().getImage("menubutton.png");
	private boolean ImageSet = false;
	private boolean changed = true;

	public Button(int x, int y, BufferedImage image) {
		setX(x);
		setY(y);
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		this.image = image;
		ImageSet = true;
	}

	public Button(int x, int y, int width, int height, String text) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.text = text;
	}

	public Button(String text) {
		this.text = text;
	}

	public Button(BufferedImage img) {
		this.image = img;
		if (image != null) {
			ImageSet = true;
			setWidth(image.getWidth());
			setHeight(image.getHeight());
		}
	}

	public String getText() {
		return text;
	}

	public void setImage(BufferedImage img) {
		this.image = img;
		ImageSet = true;
	}

	@Override
	public void setX(int x) {
		super.setX(x);
		changed = true;
	}

	@Override
	public void setY(int y) {
		super.setY(y);
		changed = true;
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		changed = true;
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		changed = true;
	}

	public void setText(String text) {
		this.text = text;
		changed = true;
	}

	public void setTextColor(Color c) {
		textcolor = c;
		changed = true;
	}

	public void setBackgroundColor(Color c) {
		backgroundcolor = c;
		changed = true;
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		if (!this.isVisible()) {
			return;
		}
		int width = getWidth();
		int height = getHeight();
		
		g2d.setColor(backgroundcolor);

		if (ImageSet) {
			if(ispressed)
			{
				g2d.fillRoundRect(getX(), getY(), width, height, 5, 5);
			}
			g2d.drawImage(image, getX(), getY(), width, height, null);
		} else {
			int stringwidth = g2d.getFontMetrics().stringWidth(text);
			int stringheight = g2d.getFontMetrics().getHeight();
			if(text != null && text != "")
			{
				if(ispressed)
				{
					g2d.fill3DRect(getX(), getY(), width, height, true);
					g2d.drawImage(image, getX()+2, getY()+2, width-4, height-4, null);
				}
				else
					g2d.drawImage(image, getX(), getY(), width, height, null);
				
			}
			else
				g2d.fill3DRect(getX(), getY(), width, height, true);
			//g2d.fillRoundRect(getX(), getY(), width, height, width, height);
			
			g2d.setColor(textcolor);
			g2d.drawString(text, getX() + width / 2 - stringwidth / 2, getY() + height / 2 + stringheight / 2);
	}
		drawBorder(g2d);
	}

	@Override
	public void onUpdate(Screen screen) {
		if (!this.isVisible()) {
			return;
		}
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();

		if (mpl.isLeftClicked()) {
			if (!ispressed) {
				if (inButton(mpl.getX(), mpl.getY())) {
					ispressed = true;
				}
			}
		} else {
			if (ispressed) { // pressed -> released
				ispressed = false;
				if (inButton(mpl.getX(), mpl.getY())) {
					callActions();
				}
			}
		}
	}

	public boolean inButton(int x, int y) {
		return (x >= getX() && y >= getY() && x <= getX() + getWidth() && y <= getY() + getHeight());
	}

	public void callActions() {
		ActionEvent ace = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, text);
		for (ActionListener performing : getActionListener()) {
			performing.actionPerformed(ace);
		}
	}

	public BufferedImage getImage() {
		return image;
	}

}
