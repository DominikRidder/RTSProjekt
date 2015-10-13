package gameScreens;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Unit;

public class OrkTest extends Unit {

	private BufferedImage img;

	public OrkTest(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g2d) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File("Orc.png"));
			g2d.drawImage(img, getX(), getY(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 30, 40);
	}

}
