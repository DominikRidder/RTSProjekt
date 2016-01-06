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
	// private Color textcolor = Color.YELLOW, backgroundcolor = Color.GRAY;
	private boolean ispressed = false;
	private BufferedImage image = Game.getImageManager().getImage("menubutton.png");
	private boolean changed = true;
	private boolean bghighlight = false;
	private boolean drawstring = true;

	public Button(int x, int y, BufferedImage image) {
		setX(x);
		setY(y);
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		this.image = image;
	}

	public Button(int x, int y, int width, int height, String text) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.text = text;
	}

	public Button(int x, int y, int width, int height, String text, boolean drawstring, boolean bghighlighting) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.text = text;
		this.drawstring = drawstring;
		this.bghighlight = bghighlighting;
	}

	public Button(String text, boolean drawstring, boolean bghighlighting) {
		this.drawstring = drawstring;
		this.bghighlight = bghighlighting;
		this.text = text;
	}

	public Button(BufferedImage img, boolean drawstring, boolean bghighligthing) {
		this.image = img;
		if (image != null) {
			setWidth(image.getWidth());
			setHeight(image.getHeight());
		}
		this.drawstring = drawstring;
		this.bghighlight = bghighligthing;
	}

	public Button(BufferedImage img) {
		this.image = img;
		if (image != null) {
			setWidth(image.getWidth());
			setHeight(image.getHeight());
		}
		this.bghighlight = true;
	}
	
	public void setBgHighlight(boolean bgh) {
		this.bghighlight = bgh;
	}

	public void setDrawText(boolean dt) {
		this.drawstring = dt;
	}
	
	public String getText() {
		return text;
	}

	public void setImage(BufferedImage img) {
		this.image = img;
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

		if (bghighlight) {
			if (ispressed) {
				g2d.fillRoundRect(getX(), getY(), width, height, 5, 5);
			}
			g2d.drawImage(image, getX(), getY(), width, height, null);
		} else {
			if (image == null) {
				g2d.fill3DRect(getX(), getY(), width, height, true);
			} else {
				if (ispressed) {
					g2d.fill3DRect(getX(), getY(), width, height, true);
					g2d.drawImage(image, getX() + 2, getY() + 2, width - 4, height - 4, null);
				} else {
					g2d.drawImage(image, getX(), getY(), width, height, null);
				}
				g2d.setColor(textcolor);
				if (text != null && text != "" && drawstring) {
					int stringwidth = g2d.getFontMetrics().stringWidth(text);
					int stringheight = g2d.getFontMetrics().getHeight();
					g2d.drawString(text, getX() + width / 2 - stringwidth / 2, getY() + height / 2 + stringheight / 2);
				}
			}
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

	public void setTextShowing(boolean drawingText) {
		drawstring = drawingText;
	}

}
