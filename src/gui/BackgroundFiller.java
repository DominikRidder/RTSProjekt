package gui;

import gameEngine.Game;
import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BackgroundFiller extends GuiElement {
	//private Color textcolor = Color.YELLOW, backgroundcolor = Color.GRAY;
	private final BufferedImage image = Game.getImageManager().getImage("muster.png");

	public BackgroundFiller(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		int width = getWidth();
		int height = getHeight();
		
		for(int x = getX(); x < getX()+width; x+=30)
		{
			for(int y = getY(); y < getY()+height; y+=30)
			{
				g2d.drawImage(image, x, y, 30, 30, null);
			}
		}
	}

	@Override
	public void onUpdate(Screen screen) {
	}
}

