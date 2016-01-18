package entity;

import gameEngine.Screen;

public class Soldier extends Unit {

	private static final int rad = 6;
	private static final String img_name = "Soldier.png";
	private static final int maxlife = 100;
	private static final int life = maxlife;
	private static final int mindmg = 7;
	private static final int maxdmg = 13;

	public Soldier(int x, int y, int owner) {
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
	public void openMenue() {
	}

	@Override
	public void taskMining(Screen screen) { //SOMIT KANN DER SOLDAT NICHT NICHT NICHT ABBAUEN !!!
		// TODO Auto-generated method stub
	}

}
