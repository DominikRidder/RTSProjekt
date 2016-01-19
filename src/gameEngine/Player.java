package gameEngine;

import java.util.ArrayList;

import entity.Materials;
import entity.ResourceInfo;

public class Player {

	private static ArrayList<Player> allplayer = new ArrayList<Player>();

	public static final int MAIN_PLAYER = 0;

	private boolean ishuman;
	private int owner;
	private int wood;
	private int stone;
	private int iron;
	private int unitLimit = 10;
	private int realUnitLimit = 10;
	private int actualUnits = 0;

	public static Player getPlayer(int owner) {
		for (Player sp : allplayer) {
			if (sp.getOwner() == owner) {
				return sp;
			}
		}
		return null;
	}

	public static void dropAllPlayer() {
		allplayer = new ArrayList<Player>();
	}

	public int getRes(int which) {
		switch (which) {
		case Materials.WOOD:
			return wood;
		case Materials.STONE:
			return stone;
		case Materials.IRON:
			return iron;
		default:
			return -1;
		}
	}

	public int getOwner() {
		return owner;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int i) {
		wood = i;
	}

	public int getStone() {
		return stone;
	}

	public void setStone(int i) {
		stone = i;
	}

	public int getIron() {
		return iron;
	}

	public void setIron(int i) {
		iron = i;
	}

	public Player(int owner) {
		this.owner = owner;

		if (getPlayer(owner) == null) {
			allplayer.add(this);
		} else {
			throw new RuntimeException(
					"There cant exist 2 Player with the same owner identyfier!");
		}
	}

	public void setRes(int res, int i) {
		switch (res) {
		case Materials.WOOD:
			wood = i;
			break;
		case Materials.STONE:
			stone = i;
			break;
		case Materials.IRON:
			iron = i;
			break;
		default:
			;
		}

	}

	public int getUnitLimit() {
		return unitLimit;
	}

	public void addToUnitLimit(int toAdd) {
		realUnitLimit += toAdd;
		if (realUnitLimit < 50) {
			unitLimit = realUnitLimit;
		} else {
			unitLimit = 50;
		}
	}

	public boolean hasSpace(int i) {
		if (actualUnits + i <= unitLimit) {
			actualUnits += i;
			return true;
		} else {
			return false;
		}
	}

	public void freeSpace(int i) {
		actualUnits -= i;
	}

	public int getActualUnitCOunter() {
		return actualUnits;
	}

	public boolean hasResources(ResourceInfo res) {
		return wood >= res.wood && iron >= res.iron && stone >= res.stone;
	}

	public void consumeResources(ResourceInfo amount) {
		if (hasResources(amount)) {
			wood -= amount.wood;
			iron -= amount.iron;
			stone -= amount.stone;
		} else {
			System.out.println("Illegal consume call in Class Player!");
		}
	}
}
