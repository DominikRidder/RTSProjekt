package entity;

import gameEngine.Game;
import gameEngine.Player;

public class Farm extends Building {

	private final int FOOD = 5;

	public Farm(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
		setMaxLife(50);
		setLife(50);
		Game.getImageManager().getImage(img_name);
		this.img_name = img_name;
	}

	public boolean die() {
		Player.getPlayer(getOwner()).freeSpace(FOOD);
		return l_Entities.remove(this);// This should be dead now!
	}

	@Override
	public void changeStatus(int status) {
		if (status == STATUS_FINISHED && current_status != status) {
			Player.getPlayer(owner).addToUnitLimit(FOOD);
		}
		current_status = status;
	}

}
