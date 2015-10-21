package gui;

import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Button extends GuiElement {

	private int width, height;
	private String text;
	private boolean ispressed = false;

	public Button(int x, int y, int width, int height, String text) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.text = text;
	}

	public Button(int x, int y, int width, int height, String text, Button relativto) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.text = text;
		setRelativTo(relativto);
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void onDraw(Graphics2D g2d) {
		int stringwidth = g2d.getFontMetrics().stringWidth(text);
		int stringheight = g2d.getFontMetrics().getHeight();

		g2d.setColor(Color.BLACK);
		g2d.fill3DRect(getX(), getY(), width, height, true);
		g2d.setColor(Color.WHITE);
		g2d.drawString(text, getX() + width / 2 - stringwidth / 2, getY()
				+ height / 2 + stringheight/2);
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
