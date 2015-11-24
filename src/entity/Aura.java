package entity;

import gameEngine.Game;

import java.awt.Graphics2D;

public class Aura {	
	private float rotation;
	public Aura() {
		// TODO Auto-generated constructor stub
	}
	
	public void update()
	{
		rotation+=0.01f;
		rotation%=2*Math.PI;
	}

	public void drawAura(Graphics2D g2d, int x, int y, int scale, int owner, int id)
	{
		if(owner == -1)//no owner? has no aura
			return;
		float temp = rotation;//fix a synchronizing bug 
		g2d.rotate(temp+id, x+16, y+16);//The id just makes every aura drawing a bit different
		g2d.drawImage(Game.getImageLoader().getImage("aura_destroyed.png", owner), x, y, null);
		g2d.rotate(-temp-id, x+16, y+16);
	}
}
