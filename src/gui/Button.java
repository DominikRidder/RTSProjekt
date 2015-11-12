package gui;

import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Button extends GuiElement {

	private String text;
	private Color textcolor = Color.WHITE, backgroundcolor = Color.BLACK;
	private boolean ispressed = false;
	private BufferedImage image;
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
		ImageSet = true;
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}

	public void setImage(BufferedImage img) {
		this.image = img;
		ImageSet = true;
	}

	public void setX(int x) {
		super.setX(x);
		changed = true;
	}

	public void setY(int y) {
		super.setY(y);
		changed = true;
	}

	public void setHeight(int height) {
		super.setHeight(height);
		changed = true;
	}

	public void setWidth(int width) {
		super.setWidth(width);
		changed = true;
	}

	public void setText(String text) {
		this.text = text;
		changed = true;
	}

	// private void drawImage() {
	// int width = getWidth();
	// int height = getHeight();
	//
	// if (!ImageSet) { // not working
	// // image = new BufferedImage(width, height,
	// // BufferedImage.TYPE_3BYTE_BGR);
	// // Graphics2D g2d = image.createGraphics();
	// // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	// // RenderingHints.VALUE_ANTIALIAS_ON);
	// // int stringwidth = g2d.getFontMetrics().stringWidth(text);
	// // int stringheight = g2d.getFontMetrics().getHeight();
	// // // g2d.setFont(new Font("Arial",Font.BOLD, 20));
	// // g2d.setColor(backgroundcolor);
	// // g2d.fill3DRect(getX(), getY(), width, height, true);
	// // g2d.setColor(textcolor);
	// // g2d.drawString(text, getX() + width / 2 - stringwidth / 2, getY()
	// // + height / 2 + stringheight / 2);
	// } else {
	// try {
	// image = getScaledImage(image, getWidth(), getHeight());
	// } catch (IOException e) {
	// System.out.println("Error while scaling Button Image.");
	// }
	// }
	// }

	public BufferedImage getScaledImage(BufferedImage image, int width,
			int height) throws IOException {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(
				scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(
				scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter(image, new BufferedImage(width, height,
				image.getType()));
	}

	public void setTextColor(Color c) {
		textcolor = c;
		changed = true;
	}

	public void setBackgroundColor(Color c) {
		backgroundcolor = c;
		changed = true;
	}

	public void onDraw(Graphics2D g2d) {
		int width = getWidth();
		int height = getHeight();

		// if (changed) {
		// changed = false;
		// drawImage();
		// }

		if (ImageSet) {
			g2d.drawImage(image, getX(), getY(), width, height, null);
		} else {
			int stringwidth = g2d.getFontMetrics().stringWidth(text);
			int stringheight = g2d.getFontMetrics().getHeight();
			g2d.setColor(backgroundcolor);
			g2d.fill3DRect(getX(), getY(), width, height, true);
			g2d.setColor(textcolor);
			g2d.drawString(text, getX() + width / 2 - stringwidth / 2, getY()
					+ height / 2 + stringheight / 2);

		}
	}

	public void onUpdate(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();
		
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
		return (x >= getX() && y >= getY() && x <= getX() + getWidth() && y <= getY()
				+ getHeight());
	}

	public void callActions() {
		ActionEvent ace = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				text);
		for (ActionListener performing : getActionListener()) {
			performing.actionPerformed(ace);
		}
	}

}
