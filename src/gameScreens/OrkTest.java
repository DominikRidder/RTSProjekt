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
	private final Rectangle rg;
	private final Rectangle imgrg;

	public OrkTest(int x, int y) {
		super(x, y);
		rg = new Rectangle(getX(), getY(), 3, 4);

		try {
			img = ImageIO.read(new File("Orc.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		imgrg = new Rectangle(getX(), getY(), img.getWidth(), img.getHeight());
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(img, getX(), getY(), null);
	}

	@Override
	public void update() {

	}

	@Override
	public Rectangle getBounds() {
		rg.x = this.getX();
		rg.y = this.getY();
		return rg;
	}

	public Rectangle getImageBounds() {
		imgrg.x = this.getX();
		imgrg.y = this.getY();
		return imgrg;
	}
}
