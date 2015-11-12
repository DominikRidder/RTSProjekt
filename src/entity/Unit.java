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
	private int w, h;
	private int maxLife;
	private int life;
	private int minDmg;
	private int maxDmg;
	private int lastAttack = 0;
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
				for (i = 0; i < screen.getEntitys().size(); i++) {
					if (screen.getEntitys().get(i).getImageBounds().contains(mpl.getX(), mpl.getY()) && this != screen.getEntitys().get(i)) {
						fight = true;
						opponent = i;
						System.out.println("Gefunden");
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
		if (fight == true && opponent != -1 && Math.abs(screen.getEntitys().get(opponent).getX() - getX()) < 64 && Math.abs(screen.getEntitys().get(opponent).getY() - getY()) < 64) {
			if (lastAttack == 0) {
				attack(opponent, screen);
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

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int life) {
		this.maxLife = life;
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public void setLife(int life) {
		if (life < 0) {
			this.life = 0;
		} else {
			this.life = life;
		}
	}

	public int getMinDmg() {
		return minDmg;
	}

	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}

	public int getMaxDmg() {
		return maxDmg;
	}

	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}

	void attack(int opponent, Screen screen) {
		int dmg = minDmg + ((int) (Math.random() * ((maxDmg - minDmg) + 1)));
		screen.getEntitys().get(opponent).setLife(screen.getEntitys().get(opponent).getLife() - dmg);
		lastAttack = 50;
	}
}
