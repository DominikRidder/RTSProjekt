package entity;

import gameEngine.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Aura {
	static int counter;
	
	public Aura() {
		// TODO Auto-generated constructor stub
	}
	
	public void update()
	{
		counter++;
	}

	public void drawAura(Graphics2D g2d, int x, int y, int scale, int owner, int id)
	{
		if(owner == -1)//no owner? has no aura
			return;
		g2d.setColor(Color.RED);
		
		BufferedImage img = Game.getImageLoader().getImage("aura_destroyed.png");
		g2d.rotate(counter/10000., x+16, y+16);
		g2d.drawImage(img, x, y, null);
		g2d.rotate(-counter/10000., x+16, y+16);
	}
}
