package entity;

import gameEngine.MousepadListener;
import gameEngine.Player;
import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import Utilitys.Point;

public abstract class Unit extends AbstractEntity {

	private boolean marked;
	private int opponent = -1;
	private boolean fight;
	private int m_dmg = 0, m_dmgtimer = 0;

	private final ArrayList<Point> wayPoints = new ArrayList<Point>();
	// private int w, h;
	private int lastAttack = 0;

	public Unit(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
	}

	@Override
	public void update(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();

		doTask(screen); // New Task System

		if (mpl.isLeftClicked()) {
			if (mpl.isDragging()) {
				if (screen.getDraggingZone().intersects(getBounds())
						&& getOwner() == Player.MAIN_PLAYER) {
					marked = true;
				} else {
					marked = false;
				}
			} else {
				if ((mpl.getX() >= getX() && mpl.getX() <= getX()
						+ getImageBounds().getWidth())
						&& (mpl.getY() >= getY() && mpl.getY() <= getY()
								+ getImageBounds().getHeight())
						&& getOwner() == Player.MAIN_PLAYER) { // 50
					// dynamisch
					// machen
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

					if (!fight
							&& getEntities().get(i).getImageBounds()
									.contains(mpl.getX(), mpl.getY())
							&& this.getEntityID() != getEntities().get(i)
									.getEntityID() && this.getLife() > 0
							&& getEntities().get(i).getLife() > 0
							&& getEntities().get(i).getOwner() != getOwner()) {
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
			// System.out.println("i stucked at spawn :C");
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

		if (getX() == next.getX() && getY() == next.getY()
				&& wayPoints.size() > 0) {
			wayPoints.remove(0);
		}
		if (fight == true) {
			if (lastAttack == 0) {
				fight = attack(opponent);
				if (!fight)
					opponent = -1;// reset
			} else {
				lastAttack--;
			}

		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (this.marked) {
			g2d.setColor(Color.YELLOW);
			g2d.draw(new Ellipse2D.Double(this.getX() - rad * 3. / 2, this
					.getY() - rad * 3. / 2, this.rad * 3, this.rad * 3));
		}

		super.draw(g2d);

		if (m_dmgtimer > 0) {
			String dmg = "-" + m_dmg;
			int stringwidth = g2d.getFontMetrics().stringWidth(dmg);
			Color d = g2d.getColor();
			g2d.setColor(Color.RED);
			g2d.drawString(dmg, getX() - stringwidth / 2, (int) this
					.getImageBounds().getY() - 5 + m_dmgtimer / 2);
			g2d.setColor(d);
			m_dmgtimer--;
		}
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	/* HP Amor and Damage interface stuff */

	@Override
	public boolean takeDamage(int dmg, AbstractEntity dmgdealer) {
		boolean a = super.takeDamage(dmg, dmgdealer);
		m_dmg = dmg;
		m_dmgtimer = 10;
		return a;
	}

	@Override
	public boolean attack(int opponent) {
		lastAttack = 50;
		AbstractEntity e = getEntity(opponent);
		if (opponent != -1 && e != null && opponent != this.getEntityID()
				&& Math.abs(e.getX() - getX()) < 64
				&& Math.abs(e.getY() - getY()) < 64)
			return e.takeDamage(getRandDmg(), this);
		return false;

	}

	/*************** Task Section ****************/

	public void taskWood(Screen screen) {
		// TODO: Implement Auto Wood search+hit or add a tmp gui to screen for
		// selection (some kind of Crooshair Cursor)
	}

	public void taskStone(Screen screen) {
		// TODO: Implement Auto Stone search+hit or add a tmp gui to screen for
		// selection (some kind of Crooshair Cursor)
	}

	public void taskMining(Screen screen) {
		// TODO: Implement Auto Mining search+hit or add a tmp gui to screen for
		// selection (some kind of Crooshair Cursor)
	}

	public void taskAttack(Screen screen) {
		// TODO: Implement Auto Attack search+hit
	}

	public void taskDefend(Screen screen) {
		// TODO: Implement Defending
	}

	public void taskSpawn(Screen screen) {
		// Unit can't spawn anything normally
		// For builduing a House we should consider to make a new Task
	}

}
