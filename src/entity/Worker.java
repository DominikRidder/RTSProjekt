package entity;

import gameEngine.Screen;

public class Worker extends Unit {

	private static final int rad = 6;
	private static final String img_name = "worker.png";
	private static final int maxlife = 35;
	private static final int life = maxlife;
	private static final int mindmg = 2;
	private static final int maxdmg = 6;

	public Worker(int x, int y, int owner) {
		super(x, y, rad, img_name, owner);
		setMaxLife(maxlife);
		setLife(life);
		setMinDmg(mindmg);
		setMaxDmg(maxdmg);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskUpgrading(Screen screen) {
		// TODO Auto-generated method stub

	}

}
