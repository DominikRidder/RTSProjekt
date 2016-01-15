package entity;

import gameEngine.Screen;

public class Soldier extends Unit {

	private static final int rad = 6;
	private static final String img_name = "Soldier.png";
	private static final int maxlife = 150;
	private static final int life = maxlife;
	private static final int mindmg = 10;
	private static final int maxdmg = 19;

	public Soldier(int x, int y, int owner) {
		super(x, y, rad, img_name, owner);
		setMaxLife(maxlife);
		setLife(life);
		setMinDmg(mindmg);
		setMaxDmg(maxdmg);
	}

	@Override
	public void taskMining(Screen screen) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
