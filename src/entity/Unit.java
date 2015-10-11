package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import Utilitys.Point;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameScreens.MainScreen;

public abstract class Unit extends AbstractEntity {

	private boolean marked;

	private ArrayList<Point> wayPoints = new ArrayList<Point>();
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
			System.out.println("RECHTS");
			if (marked == true) {
				wayPoints.clear();
				wayPoints.add(new Point(mpl.getX(), mpl.getY()));
			}
		}

		// int collisionArt = checkCollision();
		Point next;
		if (wayPoints.size() == 0) {
			next = new Point(getX(), getY());
		} else {
			next = new Point(wayPoints.get(0).getX(), wayPoints.get(0).getY());
		}

		if (getX() != next.getX()) {
			if (getX() < next.getX()) {
				setX(getX() + 1);
			} else {
				setX(getX() - 1);
			}
		}
		if (getY() != next.getY()) {
			if (getY() < next.getY()) {
				setY(getY() + 1);
			} else {
				setY(getY() - 1);
			}
		}
		if (getX() == next.getX() && getY() == next.getY() && wayPoints.size() > 0) {
			wayPoints.remove(0);
		}
	}

	public int checkCollision() {
		ArrayList<Unit> units = ((MainScreen) sc.getScreenFactory().getCurrentScreen()).getUnits();
		for (int i = 0; i < units.size(); i++) {
			Unit unit = units.get(i);
			if (getBounds().intersects(unit.getBounds()) && !getBounds().equals(unit.getBounds())) {
				if (unit.getX() < getX()) {
				}
			}
		}
		return 0;
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
