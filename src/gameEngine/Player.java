package gameEngine;

import java.util.ArrayList;

public class Player {
	
	private static ArrayList<Player> allplayer = new ArrayList<Player>();
	
	private boolean ishuman;
	private int owner;
	private int wood;
	
	public static Player getPlayer(int owner) {
		for (Player sp : allplayer){
			if (sp.getOwner() == owner){
				return sp;
			}
		}
		return null;
	}
	
	public static void dropAllPlayer() {
		allplayer = new ArrayList<Player>();
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
	
	public Player(int owner){
		this.owner = owner;
		
		if (getPlayer(owner) == null){
			allplayer.add(this);
		}else{
			throw new RuntimeException("There cant exist 2 Player with the same owner identyfier!");
		}
	}
}
