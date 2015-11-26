package gui;

import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class ScrollPane extends GuiElement implements CoordinateMapping {

	private GuiElement scrollableclient;

	private int rx, ry;

	private ScrollBar scrollx, scrolly;

	/**
	 * Creates a Viewpoint in the given guielement. Note, that the total width
	 * and height of the ScrollPane is extended by 10 Pixels, because of the
	 * Scrollbars.
	 */
	public ScrollPane(GuiElement guielement, int width, int height) {
		scrollableclient = guielement;
		setX(scrollableclient.getX());
		setY(scrollableclient.getY());
		setWidth(width);
		setHeight(height);
		scrollx = new ScrollBar(ScrollBar.X_ORIENTATION);
		scrolly = new ScrollBar(ScrollBar.Y_ORIENTATION);
	}

	public void onDraw(Graphics2D g2d) {
		if (!this.isVisible()){
			return;
		}
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
		if (!this.isVisible()){
			return;
		}
		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();

		if (this.needUpdate()) {
			scrollx.resizeBar();
			scrolly.resizeBar();
			this.setUpdate(false);
		}

		if (scrollableclient.needUpdate()) {
			setX(scrollableclient.getX());
			setY(scrollableclient.getY());
		}

		scrollx.onUpdate(screen);
		scrolly.onUpdate(screen);

		if (getBounds().contains(mpl.getX(), mpl.getY())) {
			mpl.setX(mpl.getX() + rx);
			mpl.setY(mpl.getY() + ry);

			scrollableclient.onUpdate(screen);

			mpl.setX(mpl.getX() - rx);
			mpl.setY(mpl.getY() - ry);
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
		private int startdrag;

		public ScrollBar(int orientation) {
			this.orientation = orientation;

			if (orientation == X_ORIENTATION) {
				bar = new Rectangle(
						getX(),
						getY() + getHeight(),
						(int) (getWidth() * ((double) getWidth()) / scrollableclient
								.getWidth()), 10);
				if (bar.width > getWidth()) {
					bar.width = getWidth();
				}
			} else {
				bar = new Rectangle(
						getX() + getWidth(),
						getY(),
						10,
						(int) (getHeight() * ((double) getHeight() / scrollableclient
								.getHeight())));
				if (bar.height > getHeight()) {
					bar.height = getHeight();
				}
			}
		}

		public void onUpdate(Screen screen) {
			MousepadListener mpl = screen.getScreenFactory().getGame()
					.getMousepadListener();

			if (!dragOn) {
				if (bar.contains(mpl.getX(), mpl.getY())) {
					dragOn = true;
					if (orientation == X_ORIENTATION) {
						startdrag = mpl.getX() - bar.x;
					} else {
						startdrag = mpl.getY() - bar.y;
					}
				}
			}
			if (dragOn) {
				if (mpl.isDragging()) {
					if (orientation == X_ORIENTATION) {
						bar.x = mpl.getMarkX() - startdrag;
					} else {
						bar.y = mpl.getMarkY() - startdrag;
					}
				} else {
					if (!mpl.isLeftClicked()) {
						dragOn = false;
					}
				}
			}

			if (orientation == X_ORIENTATION) {
				if (bar.x < getX()) {
					bar.x = getX();
				} else if (bar.x > getX() + getWidth() - bar.width) {
					bar.x = getX() + getWidth() - bar.width;
				}
			} else {
				if (bar.y < getY()) {
					bar.y = getY();
				} else if (bar.y > getY() + getHeight() - bar.height) {
					bar.y = getY() + getHeight() - bar.height;
				}
			}

			if (orientation == X_ORIENTATION) {
				rx = (int) ((scrollableclient.getWidth() - getWidth())
						* ((double) bar.x - getX()) / (getWidth() - bar.width));
			} else {
				ry = (int) ((scrollableclient.getHeight() - getHeight())
						* ((double) bar.y - getY()) / (getHeight() - bar.height));
				;
			}
		}

		public void onDraw(Graphics2D g2d) {
			// scrollbar under the ScrollPane
			g2d.setColor(Color.GRAY);
			if (orientation == X_ORIENTATION) {
				g2d.draw3DRect(getX(), getY() + getHeight(), getWidth(), 10,
						false);
				g2d.fillRoundRect(getX(), getY() + getHeight(), getWidth(), 10,
						5, 5);
			} else {
				g2d.draw3DRect(getX() + getWidth(), getY(), 10, getHeight(),
						false);
				g2d.fillRoundRect(getX() + getWidth(), getY(), 10, getHeight(),
						5, 5);
			}

			g2d.setColor(Color.RED);
			g2d.fill3DRect(bar.x, bar.y, bar.width, bar.height, false);
		}

		public void resizeBar() {
			if (orientation == X_ORIENTATION) {
				bar.width = (int) (getWidth() * ((double) getWidth()) / scrollableclient
						.getWidth());
				bar.x = rx * (getWidth() - bar.width)
						/ (scrollableclient.getWidth() - getWidth()) + getX();
				if (bar.width > getWidth()) {
					bar.width = getWidth();
				}
			} else {
				bar.height = (int) (getHeight() * ((double) getHeight() / scrollableclient
						.getHeight()));
				bar.y = ry * (getHeight() - bar.height)
						/ (scrollableclient.getHeight() - getHeight()) + getY();
				if (bar.height > getHeight()) {
					bar.height = getHeight();
				}
			}
		}
	}

	public Point getCoordinate(int x, int y) {
		if (getBounds().contains(x, y)) {
			if (scrollableclient instanceof CoordinateMapping) {
				return ((CoordinateMapping) scrollableclient).getCoordinate(x
						+ rx, y + ry);
			} else {
				throw new RuntimeException(
						"scrollableclient don't support the CoordinateMapping!");
			}
		}
		return null;
	}
}