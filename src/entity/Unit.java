package entity;

import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameScreens.MainScreen;

import java.util.ArrayList;
import java.util.HashMap;

import Utilitys.Point;

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

		checkCollision();

		if (getX() == next.getX() && getY() == next.getY() && wayPoints.size() > 0) {
			wayPoints.remove(0);
		}
	}

	public void checkCollision() {
		HashMap<AbstractEntity, Integer> entitys = ((MainScreen) sc.getScreenFactory().getCurrentScreen()).getEntityWithMap();

		int field = entitys.get(this);
		int rad = 1;
		while (entitys.containsValue(field)) {
			field = MainScreen.pointToMapConst(x, y);
		}
		// ArrayList<AbstractEntity> inField = new ArrayList<AbstractEntity>();
		// for (Entry<AbstractEntity, Integer> entry : entitys.entrySet()) {
		// if (entry.getValue() == field) {
		// inField.add(entry.getKey());
		// }
		// }
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
}
