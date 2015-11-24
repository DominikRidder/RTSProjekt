package entity;

import gameEngine.Screen;

public abstract class Building extends AbstractEntity {

	public Building(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
		// TODO Auto-generated constructor stub
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
		return false;//wow, this is automated right, a NORMAL building should not attack
	}
}
