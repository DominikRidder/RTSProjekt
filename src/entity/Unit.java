package entity;

import gameEngine.Game; 
import gameEngine.MousepadListener;
import gameEngine.Player;
import gameEngine.Screen;
import gameScreens.GameScreen;
import gui.Button;
import gui.EntityOptions;
import gui.Monolog;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import Utilitys.Point;

public abstract class Unit extends AbstractEntity implements ActionListener {

	private boolean wasnotmarked = true;
	private boolean menueisopen;
	private int opponent = -1;
	private boolean fight;
	private int m_dmg = 0, m_dmgtimer = 0;
	private Building target;
	private int toBuild;

	private final ArrayList<Point> wayPoints = new ArrayList<Point>();
	// private int w, h;
	private int lastAttack = 0;

	public Unit(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
		if (!Player.getPlayer(owner).hasSpace(1)) {
			die();
		}

	}

	@Override
	public void update(Screen screen) {
		doTask(screen); // New Task System

		testMarked(screen);

		rightClickAction(screen);

		move(screen);
		move(screen); // twice for a tmp solution
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (this.marked) {
			g2d.setColor(Color.YELLOW);
			g2d.draw(new Ellipse2D.Double(this.getX() - rad * 3. / 2, this.getY() - rad * 3. / 2, this.rad * 3, this.rad * 3));
		}

		super.draw(g2d);

		if (m_dmgtimer > 0) {
			String dmg = "-" + m_dmg;
			int stringwidth = g2d.getFontMetrics().stringWidth(dmg);
			Color d = g2d.getColor();
			g2d.setColor(Color.RED);
			g2d.drawString(dmg, getX() - stringwidth / 2, (int) this.getImageBounds().getY() - 5 + m_dmgtimer / 2);
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
		if (opponent != -1 && e != null && opponent != this.getEntityID() && Math.abs(e.getX() - getX()) < 64 && Math.abs(e.getY() - getY()) < 64)
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
		taskAttack(screen);
	}

	public void taskCast(Screen screen) {
		new FireBall(this.getX(), this.getY(), m_dmg, "notfound.png", this.getOwner());
		m_curtask = task.t_none;
	}

	public void taskAttack(Screen screen) {
		if (getEntity(opponent) == null) {
			opponent = -1;
			fight = false;
			m_curtask = task.t_none; // searching new target is missing
		}

		if (opponent != -1) {
			AbstractEntity enemy = getEntity(opponent);
			if (wayPoints.size() > 0) {
				wayPoints.remove(0);
			}
			wayPoints.add(new Point(enemy.getX(), enemy.getY()));
		}

		if (fight == true) {
			AbstractEntity enemy = getEntity(opponent);
			boolean inrange = Math.abs(enemy.getX() - getX()) < 64 && Math.abs(enemy.getY() - getY()) < 64;
			if (lastAttack == 0 && inrange) {
				fight = attack(opponent);
				if (!fight)
					opponent = -1;// reset
			} else {
				if (lastAttack > 0) {
					lastAttack--;
				}
			}

		}
	}

	public void taskDefend(Screen screen) {
		// TODO: Implement Defending
	}

	public void taskSpawn(Screen screen) {
		// Unit can't spawn anything normally
		// For builduing a House we should consider to make a new Task
	}

	public void taskBuild(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();
		GameScreen gamesc = (GameScreen) screen;

		if (target == null) {
			if (toBuild == 0) {
				if (Player.getPlayer(getOwner()).hasResources(MainBuilding.res)) {
					target = new MainBuilding(mpl.getCurrentX(), mpl.getCurrentY(), 10, "M_MainBuilding_1.png", getOwner());
				} else {
					m_curtask = task.t_none;
					return;
				}
			} else if (toBuild == 1) {
				if (Player.getPlayer(getOwner()).hasResources(Farm.res)) {
					target = new Farm(mpl.getCurrentX(), mpl.getCurrentY(), 10, "M_Farm.png", getOwner());
				} else {
					m_curtask = task.t_none;
					return;
				}
			}
			target.setLife(1);
			target.setStatus(Building.STATUS_PREVIEW_NOT_FIXED);
		} else if (target.getStatus() == Building.STATUS_PREVIEW_NOT_FIXED) {
			target.setX(mpl.getCurrentX() + gamesc.getViewX());
			target.setY(mpl.getCurrentY() + gamesc.getViewY());
			if (mpl.isLeftClicked()) {
				target.setStatus(Building.STATUS_PREVIEW_FIXED);
				if (toBuild == 0) {
					if (Player.getPlayer(getOwner()).getWood() >= 250 && Player.getPlayer(getOwner()).getStone() >= 100) {
						Player.getPlayer(getOwner()).setWood(Player.getPlayer(getOwner()).getWood() - 250);
						Player.getPlayer(getOwner()).setStone(Player.getPlayer(getOwner()).getStone() - 100);
					} else {
						m_curtask = task.t_none;
						target.die();
						return;
					}
				} else if (toBuild == 1) {
					if (Player.getPlayer(getOwner()).getWood() >= 50 && Player.getPlayer(getOwner()).getStone() >= 10) {
						Player.getPlayer(getOwner()).setWood(Player.getPlayer(getOwner()).getWood() - 50);
						Player.getPlayer(getOwner()).setStone(Player.getPlayer(getOwner()).getStone() - 10);
					} else {
						m_curtask = task.t_none;
						target.die();
						return;
					}
				}
			}
		}

		if (target.getStatus() == Building.STATUS_PREVIEW_NOT_FIXED) {
			return;
		}

		boolean inrange = Math.abs(target.getX() - getX()) < 64 && Math.abs(target.getY() - getY()) < 64;
		if (!inrange) {
			if (wayPoints.size() > 0) {
				wayPoints.remove(0);
			}
			wayPoints.add(new Point(target.getX(), target.getY()));
		} else if (target.getStatus() == Building.STATUS_PREVIEW_FIXED) {
			target.setStatus(Building.STATUS_BUIDLING);
			opponent = target.getEntityID();
			m_curtask = task.t_attack;
			target = null;
			fight = true;
		}

	}

	private void testMarked(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();

		if (mpl.isLeftClicked()) {
			if (mpl.isDragging()) {
				if (screen.getDraggingZone().intersects(getImageBounds()) && getOwner() == Player.MAIN_PLAYER) {
					marked = true;
					wasnotmarked = true;
				} else {
					marked = false;
					wasnotmarked = true;
				}
			} else {
				if (getImageBounds().contains(mpl.getX(), mpl.getY()) && getOwner() == Player.MAIN_PLAYER) { // 50
					// dynamisch
					// machen
					marked = true;
					wasnotmarked = true;
				} else {
					marked = false;
					wasnotmarked = true;
				}
			}
		}

		if (marked && wasnotmarked) {
			openMenue();
			wasnotmarked = false;
		} else if (!marked) {
			wasnotmarked = true;

			if (menueisopen && mpl.isLeftClicked() && !EntityOptions.singleton.isMarked()) {
				EntityOptions.singleton.setOptions(null, target);
				menueisopen = false;
			}
		}
	}

	public void openMenue() {
		ArrayList<Option> options = new ArrayList<Option>();

		options.add(new Option(Game.getImageManager().getImage("M_MainBuilding_1.png"), false, false));
		options.get(0).addActionListener(this);
		options.get(0).setText("MainBuilding");
		options.get(0).setToolTip(Monolog.createToolTip(200, 100, MainBuilding.produceInformation));
		options.add(new Option(Game.getImageManager().getImage("M_Farm.png"), false, false));
		options.get(1).addActionListener(this);
		options.get(1).setText("Farm");
		options.get(1).setToolTip(Monolog.createToolTip(200, 100, Farm.produceInformation));

		EntityOptions.singleton.setOptions(options, this);

		menueisopen = true;

	}

	private void rightClickAction(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();

		if (mpl.isRightClicked()) {
			if (marked == true) {
				wayPoints.clear();
				int i = 0;
				for (i = 0; i < getEntities().size(); i++) {

					if (!fight && getEntities().get(i).getImageBounds().contains(mpl.getX(), mpl.getY()) && this.getLife() > 0 && getEntities().get(i).getLife() > 0 && (getEntities().get(i).getOwner() != getOwner() || repairable(getEntities().get(i)))) {
						fight = true;
						opponent = getEntities().get(i).getEntityID();
						if (getEntities().get(i) instanceof Materials) {
							m_curtask = task.t_mining;
						}
						break;
					}
					fight = false;
					opponent = -1;
					m_curtask = task.t_attack;
				}

				wayPoints.add(new Point(mpl.getX(), mpl.getY()));
			}
		}
	}

	private boolean repairable(AbstractEntity ent) {
		return ent.getOwner() == getOwner() && ent instanceof Building;
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "MainBuilding":
			m_curtask = task.t_build;
			toBuild = 0;
			break;
		case "Farm":
			m_curtask = task.t_build;
			toBuild = 1;
			break;
		case "Cast":
			m_curtask = task.t_cast;
			break;
		default:
			System.out.println("Action: " + e.getActionCommand());
		}
	}
}
