package entity;

import gameEngine.Screen;

import java.util.ArrayList;

import Utilitys.Point;

public class FireBall extends AbstractEntity {

	private final ArrayList<Point> wayPoints = new ArrayList<Point>();

	public FireBall(int x, int y, int rad, String imgname, int ownr) {
		super(x, y, rad, imgname, ownr);
		wayPoints.add(new Point(x + 100, y + 100));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Screen sc) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean attack(int opponentID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void taskWood(Screen screen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskStone(Screen screen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskMining(Screen screen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskAttack(Screen screen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskDefend(Screen screen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskSpawn(Screen screen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskBuild(Screen screen) {
		// TODO Auto-generated method stub

	}

	public void move(Screen screen) {
		Point next;
		if (wayPoints.size() == 0) {
			return;
		} else {
			next = new Point(wayPoints.get(0).getX(), wayPoints.get(0).getY());
		}

		AbstractEntity e = hasCollision();
		if (e != null && (getX() != next.getX() || getY() != next.getY())) {
			// unused set
			// next.setX(getX());
			// next.setY(getY());
			return;
		}

		if (getX() != next.getX()) {// moving?
			addX(getX() < next.getX() ? 1 : -1);
			e = hasCollision();
			if (e != null) {
				if (e.getOwner() != getOwner()) {
					explode(e);
				}
			}
		}
		if (getY() != next.getY()) {
			addY(getY() < next.getY() ? 1 : -1);
			e = hasCollision();
			if (e != null) {
				if (e.getOwner() != getOwner()) {
					explode(e);
				}
			}
		}

		if (getX() == next.getX() && getY() == next.getY() && wayPoints.size() > 0) {
			wayPoints.remove(0);
		}
	}

	private void explode(AbstractEntity e) {
		if (this.getLife() > 0) {
			e.attack(e.getEntityID());
			setLife(0);
		}
	}

	@Override
	public void taskCast(Screen screen) {
	}

	@Override
	public void taskUpgrading(Screen screen) {
		// TODO Auto-generated method stub

	}
}