package gui;

import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class ScrollPane extends GuiElement {

	private GuiElement scrollableclient;

	private int rx, ry;
	
	private ScrollBar scrollx, scrolly;

	/**
	 * Creates a Viewpoint in the given guielement. Note, that the total width
	 * and height of the ScrollPane is extended by 15 Pixels, because of the
	 * Scrollbars.
	 */
	public ScrollPane(GuiElement guielement, int x, int y, int width, int height) {
		scrollableclient = guielement;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		scrollx = new ScrollBar(ScrollBar.X_ORIENTATION);
		scrolly = new ScrollBar(ScrollBar.Y_ORIENTATION);
	}

	public void onDraw(Graphics2D g2d) {
		AffineTransform saveAT = g2d.getTransform();
		AffineTransform transform = new AffineTransform();
		transform.translate(-rx, -ry);
		g2d.transform(transform);

		g2d.setClip(getX() + rx, getY() + ry, getWidth(), getHeight());
		scrollableclient.onDraw(g2d);
		g2d.setClip(null);
		g2d.setTransform(saveAT);

		scrollx.onDraw(g2d);
		scrolly.onDraw(g2d);
	}

	public void onUpdate(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();

		scrollx.onUpdate(screen);
		scrolly.onUpdate(screen);

		if (getBounds().contains(mpl.getX(), mpl.getY())) {
			mpl.setX(mpl.getX() + rx);
			mpl.setY(mpl.getY() + ry + 20);

			scrollableclient.onUpdate(screen);

			mpl.setX(mpl.getX() - rx);
			mpl.setY(mpl.getY() - ry + 20);
		} else {
			boolean wasclicked = mpl.isLeftClicked();
			mpl.setLeftClicked(false);
			scrollableclient.onUpdate(screen);
			mpl.setLeftClicked(wasclicked);
		}
	}

	class ScrollBar {
		private static final int Y_ORIENTATION = 0, X_ORIENTATION = 1;
		private boolean dragOn;
		private Rectangle bar;
		private int orientation;

		public ScrollBar(int orientation){
			this.orientation = orientation;
		}
		
		public ScrollBar(int x, int y, int width, int height, ScrollPane parent) {
			bar = new Rectangle(x, y, width, height);
		}

		public void onUpdate(Screen screen) {
			MousepadListener mpl = screen.getScreenFactory().getGame()
					.getMousepadListener();
			if (!dragOn) {
				Point p = null;
				if (orientation == X_ORIENTATION) {
					p = new Point(getX() + rx + 5, getY() + getHeight() + 5);
				} else {
					p = new Point(getX() + getWidth() + 5, getY() + ry + 5);
				}
				if (p.distance(mpl.getX(), mpl.getY()) <= 10) {
					dragOn = true;
				}
			}
			if (dragOn) {
				if (mpl.isDragging()) {
					if (orientation == X_ORIENTATION) {
						rx = mpl.getMarkX() - getX() - 5;
					} else {
						ry = mpl.getMarkY() - getY() - 5;
					}
				} else {
					if (!mpl.isLeftClicked()) {
						dragOn = false;
					}
				}
			}
		}

		public void onDraw(Graphics2D g2d) {
			// scrollbar under the ScrollPane
			g2d.setColor(Color.GRAY);
			if (orientation == X_ORIENTATION) {
				g2d.draw3DRect(getX() + getWidth(), getY(), 10, getHeight(),
						false);
				g2d.fillRoundRect(getX() + getWidth(), getY(), 10, getHeight(),
						5, 5);
			} else {
				g2d.draw3DRect(getX(), getY() + getHeight(), getWidth(), 10,
						false);
				g2d.fillRoundRect(getX(), getY() + getHeight(), getWidth(), 10,
						5, 5);
			}

			g2d.setColor(Color.RED);
			if (orientation == X_ORIENTATION) {
				g2d.fillOval(getX() + getWidth(), getY() + ry, 10, 10);
			} else {
				g2d.fillOval(getX() + rx, getY() + getHeight(), 10, 10);
			}
		}
	}
}