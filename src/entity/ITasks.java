package entity;

import gameEngine.Screen;

public interface ITasks {

	public enum task {
		t_none, t_wood, t_stone, t_attack, t_defend, t_mining, t_spawn, t_build, t_cast, t_upgrading
	};

	public void taskNone(Screen screen); // Do Nothing. Should still be a
											// method, in case a
	// Unit wants to inform the Player, that he is
	// avaible.

	public void taskWood(Screen screen);

	public void taskStone(Screen screen);

	public void taskMining(Screen screen);

	public void taskAttack(Screen screen);

	public void taskDefend(Screen screen);

	public void taskSpawn(Screen screen); // Spawns a Unit (common for
											// Builduings)

	public void taskBuild(Screen screen);

	public void taskCast(Screen screen);

	public void taskUpgrading(Screen screen);
}
