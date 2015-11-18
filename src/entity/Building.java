package entity;

import gameEngine.Screen;

import java.awt.Rectangle;

public class Building extends AbstractEntity {

	public Building(int x, int y, String img_name) {
		super(x, y, img_name);
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
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getImageBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean attack(int opponentID) {
		// TODO Auto-generated method stub
		return false;//wow, this is automated right, a NORMAL building should not attack
	}

}
