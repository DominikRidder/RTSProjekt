package entity;

import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Building extends AbstractEntity {

	public static final int STATUS_PREVIEW_NOT_FIXED = 0, STATUS_PREVIEW_FIXED = 1, STATUS_BUIDLING = 2, STATUS_FINISHED = 3;
	protected int current_status = 3;
	int toSpawn;

	public Building(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
		// TODO Auto-generated constructor stub
	}

	public void setStatus(int status) {
		current_status = status;
	}

	public int getStatus() {
		return current_status;
	}

	@Override
	public void update() {
	}

	public void draw(Graphics2D g2d) {
		if (current_status != STATUS_PREVIEW_FIXED && current_status != STATUS_PREVIEW_NOT_FIXED) {
			getImageBounds();// Update the image position
			drawImage(g2d);
			drawLifeBar(g2d);
		} else {
			Rectangle rect = getImageBounds();// Update the image position
			drawImage(g2d);
			g2d.setColor(new Color(1, 0, 0, 0.5f));
			g2d.fillRect(rect.x, rect.y, getImageBounds().width, getImageBounds().height);
		}
	}

	@Override
	public void update(Screen sc) {
		doTask(sc); // New Task System
	}

	@Override
	public boolean attack(int opponentID) {
		// TODO Auto-generated method stub
		return false;// wow, this is automated right, a NORMAL building should
						// not attack
	}

	@Override
	public boolean takeDamage(int dmg, AbstractEntity dmgdealer) {
		if (dmgdealer.getOwner() == getOwner()) {
			boolean ans = super.takeDamage(-dmg, dmgdealer);
			if (life > maxLife) {
				life = maxLife;
				return false;
			} else {
				return ans;
			}
		} else {
			return super.takeDamage(dmg, dmgdealer);
		}
	}

	/*************** Task Section ****************/

	public void taskWood(Screen screen) {
		// Builduings cant collect Wood (Normally)
	}

	public void taskStone(Screen screen) {
		// Builduings cant collect Stone (Normally)
	}

	public void taskMining(Screen screen) {
		// Builduings cant go Mining (Normally)
	}

	public void taskAttack(Screen screen) {
		// Not all Builduings can attack. Override to implement attacks
	}

	public void taskDefend(Screen screen) {
		// Not all Builduings can do something, to Defend. Override to implement
		// defend behaivior
	}

	public void taskSpawn(Screen screen) {
		// Override this in case your Builduing can spawn Units.
	}

	public void taskBuild(Screen screen) {
		// This task is used to build buildings.
	}

	public void taskCast(Screen screen) {
	}
}
