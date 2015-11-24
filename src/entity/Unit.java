package entity;

import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.util.ArrayList;

import Utilitys.Point;

public abstract class Unit extends AbstractEntity {

	private boolean marked;
	private int opponent = -1;
	private boolean fight;

	private final ArrayList<Point> wayPoints = new ArrayList<Point>();
	//private int w, h;
	private int lastAttack = 0;

	public Unit(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
	}

	@Override
	public void update(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();
		if (mpl.isLeftClicked()) {
			if (mpl.isDragging()) {
				if (screen.getDraggingZone().intersects(getBounds())) {
					marked = true;
				} else {
					marked = false;
				}
			} else {
				if ((mpl.getX() >= getX() && mpl.getX() <= getX() + getImageBounds().getWidth()) && (mpl.getY() >= getY() && mpl.getY() <= getY() + getImageBounds().getHeight())) { // 50 dynamisch machen
					marked = true;
				} else {
					marked = false;
				}
			}
		} else if (mpl.isRightClicked()) {
			if (marked == true) {
				wayPoints.clear();
				int i = 0;
				for (i = 0; i < getEntities().size(); i++) {
					
					if (!fight && getEntities().get(i).getImageBounds().contains(mpl.getX(), mpl.getY()) 
							&& this.getEntityID() != getEntities().get(i).getEntityID()
							&& this.getLife() > 0
							&& getEntities().get(i).getLife() > 0) {
						fight = true;
						opponent = getEntities().get(i).getEntityID();
						break;
					}
					fight = false;
					opponent = -1;
				}

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
		if (e != null && (getX() != next.getX() || getY() != next.getY())) {
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
			if (e != null) {
				setX(prevx);
			}
		}
		if (getY() != next.getY()) {
			addY(getY() < next.getY() ? 1 : -1);
			e = hasCollision();
			if (e != null) {
				setY(prevy);
			}
		}

		if (getX() == next.getX() && getY() == next.getY() && wayPoints.size() > 0) {
			wayPoints.remove(0);
		}
		if (fight == true ) {
			if (lastAttack == 0) {
				fight = attack(opponent);
				if(!fight)
					opponent = -1;//reset
			} else {
				lastAttack--;
			}

		}
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	
	/* HP Amor and Damage interface stuff*/
	@Override
	public boolean attack(int opponent) {
		lastAttack = 50;
		AbstractEntity e = getEntity(opponent);
		if(opponent != -1 && e != null
				&& opponent != this.getEntityID()
				&& Math.abs(e.getX() - getX()) < 64 
				&& Math.abs(e.getY() - getY()) < 64)
			return e.takeDamage(getRandDmg());
		return false;
		
	}
}
