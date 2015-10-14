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
		if(e != null && (getX() != next.getX() || getY() != next.getY())) {
			next.setX(getX());
			next.setY(getY());
			System.out.println("i stucked at spawn :C");
			return;
		}
		
		int prevx = getX();
		int prevy = getY();
		if (getX() != next.getX()) {// moving?
			addX(getX() < next.getX() ? 1 : -1);
			e = hasCollision();
			if(e != null){
				setX(prevx);
			}
		}
		if (getY() != next.getY()) {
			addY(getY() < next.getY() ? 1 : -1);
			e = hasCollision();
			if(e != null){
				setY(prevy);
			}
		}

		if (getX() == next.getX() && getY() == next.getY() && wayPoints.size() > 0) {
			wayPoints.remove(0);
		}
	}
		
	public void move(int direction) {
		int startdirection = direction;
		do {
			if(direction%4 == 0){
				setX(getX() + 1);
				AbstractEntity e = hasCollision();
				if(e != null) {
					setX(getX() -1);//move back
					direction++;//try another way;
				}
			}
			else if(direction%4 == 1){
				setY(getY() + 1);
				AbstractEntity e = hasCollision();
				if(e != null) {
					setY(getY() - 1);//move back
					direction++;//try another way;
				}
			}
			else if(direction%4 == 2){
				setX(getX() - 1);
				AbstractEntity e = hasCollision();
				if(e != null) {
					setX(getX() +1);//move back
					direction++;//try another way;
				}
			}
			else if(direction%4 == 3){
				setY(getY() - 1);
				AbstractEntity e = hasCollision();
				if(e != null) {
					setY(getY() +1);//move back
					direction++;//try another way;
				}
			}
		} while(startdirection != direction%4);
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
}
