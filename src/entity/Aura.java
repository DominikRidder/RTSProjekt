package entity;

import gameEngine.Game;

import java.awt.Graphics2D;

public class Aura {	
	public Aura() {
		// TODO Auto-generated constructor stub
	}

	public void drawAura(Graphics2D g2d, int x, int y, String imgname, int owner, int id)
	{
		if(owner == -1)//no owner? has no aura
			return;
		//g2d.rotate(temp+id, x+16, y+16);//The id just makes every aura drawing a bit different
		g2d.drawImage(Game.getImageLoader().getImage(imgname, owner), x, y, null);
		//g2d.rotate(-temp-id, x+16, y+16);
	}
}
