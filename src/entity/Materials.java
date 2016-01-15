package entity;

import gameEngine.Player;
import gameEngine.Screen;

import java.awt.Graphics2D;

public class Materials extends AbstractEntity {

	private float m_hit;
	public static final int WOOD = 1;
	public static final int STONE = 2;
	public static final int IRON = 3;
	private int res;

	public Materials(int x, int y, int rad, String imgname, int maxLife, int res) {
		super(x, y, rad, imgname, -1);
		setMaxLife(maxLife);
		setLife(maxLife);
		setRes(res);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLifeBar(Graphics2D g2d) {
		if (getMaxLife() != getLife())
			super.drawLifeBar(g2d);
	}

	@Override
	public void drawImage(Graphics2D g2d) {
		g2d.drawImage(getImg(), imgrg.x + (int) m_hit, imgrg.y, null);
		if (m_hit >= 0) {
			m_hit -= 0.1f;
		}
	}

	@Override
	public boolean takeDamage(int dmg, AbstractEntity dmgdealer) {
		if (m_hit <= 0)
			m_hit += 4;

		int before = this.getLife();
		boolean isalive = super.takeDamage(dmg, dmgdealer);

		if (dmgdealer.getOwner() != -1) {
			Player togift = Player.getPlayer(dmgdealer.getOwner());
			togift.setRes(res, togift.getRes(res) + before - getLife());
		}

		return isalive;
	}

	@Override
	public void update(Screen sc) {
		// TODO Auto-generated method stub

	}

	public void setRes(int res) {
		this.res = res;
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

	@Override
	public void taskCast(Screen screen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskUpgrading(Screen screen) {
		// TODO Auto-generated method stub

	}

}
