package entity;

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
				if (screen.getDraggingZone().intersects(getBounds())) {
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
				wayPoints.clear();
				wayPoints.add(new Point(mpl.getX(), mpl.getY()));
			}
		}

		int collisionArt = checkCollision();
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
			}
		}
		return 0;

	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
}
