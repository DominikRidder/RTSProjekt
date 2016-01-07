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
		doTask(sc); // New Task System
	}

	@Override
	public boolean attack(int opponentID) {
		// TODO Auto-generated method stub
		return false;// wow, this is automated right, a NORMAL building should
						// not attack
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
}
