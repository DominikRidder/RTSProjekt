package entity;

import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.util.ArrayList;

import Utilitys.Point;

public abstract class Unit extends AbstractEntity {

	private boolean marked;

	private final ArrayList<Point> wayPoints = new ArrayList<Point>();
	private int x, y, w, h;
	private Screen sc;

	public Unit(int x, int y) {
		super(x, y);
	}

	@Override
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

		Point next;
		if (wayPoints.size() == 0) {
			next = new Point(getX(), getY());
		} else {
			next = new Point(wayPoints.get(0).getX(), wayPoints.get(0).getY());
		}

		AbstractEntity e = hasCollision();
		if (e != null && e instanceof Unit) {// TODO: FIXME
			int x = e.getX();// tausche auftraege (teleportier dich mit deinem nachbarn xD)
			int y = e.getY();
			e.setX(this.getX());
			e.setY(this.getY());
			this.setX(x);
			this.setY(y);
		}

		if (getX() != next.getX()) {// moving?
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

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
}
