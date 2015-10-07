package entity;

import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Unit extends AbstractEntity {

	private boolean marked;

	private int nextX = getX(), nextY = getY();
	private int x, y, w, h;
	private Screen sc;

	public Unit(int x, int y) {
		super(x, y);
	}

	public void update(Screen screen) {
		sc = screen;
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();
		if (mpl.isLeftClicked()) {
			if (mpl.isDragging()) {
				if (mpl.getMarkX() < mpl.getX()) {
					x = mpl.getMarkX();
				} else {
					x = mpl.getX();
				}
				if (mpl.getMarkY() < mpl.getY()) {
					y = mpl.getMarkY();
				} else {
					y = mpl.getY();
				}
				w = Math.abs(mpl.getX() - mpl.getMarkX());
				h = Math.abs(mpl.getY() - mpl.getMarkY());
				Rectangle draggingZone = new Rectangle(x, y, w, h);
				if (draggingZone.intersects(getBounds())) {
					marked = true;
				} else {
					marked = false;
				}
			} else {
				if ((mpl.getX() >= getX() && mpl.getX() <= getX() + getBounds().getWidth()) && (mpl.getY() >= getY() && mpl.getY() <= getY() + getBounds().getHeight())) { // 50 dynamisch machen
					marked = true;
				} else {
					marked = false;
				}
			}
		} else if (mpl.isRightClicked()) {
			if (marked == true) {
				nextX = mpl.getX();
				nextY = mpl.getY();
			}
		}

		if (getX() != nextX) {
			if (getX() < nextX) {
				setX(getX() + 1);
			} else {
				setX(getX() - 1);
			}
		}
		if (getY() != nextY) {
			if (getY() < nextY) {
				setY(getY() + 1);
			} else {
				setY(getY() - 1);
			}
		}
	}

	public void checkCollision() {
	}

	public void drawDraggingZone(Graphics2D g2d) {
		if (sc.getScreenFactory().getGame().getMousepadListener().isDragging() && sc.getScreenFactory().getGame().getMousepadListener().isLeftClicked()) {
			if (sc.getScreenFactory().getGame().getMousepadListener().getMarkX() != -1 && sc.getScreenFactory().getGame().getMousepadListener().getMarkY() != -1) {
				g2d.drawRect(x, y, w, h);
			}
		}
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
}
