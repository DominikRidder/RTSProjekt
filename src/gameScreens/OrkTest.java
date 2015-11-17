package gameScreens;

import java.awt.Color;
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
	private final int breite = 12;//Hitboxsizes
	private final int hoehe = 12;

	public OrkTest(int x, int y) {
		super(x, y);
		rg = new Rectangle(getX(), getY(), breite, hoehe);
		setMaxLife(10);
		setLife(8);
		setMinDmg(1);
		setMaxDmg(3);
		try {
			img = ImageIO.read(new File("Orc.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		imgrg = new Rectangle(getX(), getY(), img.getWidth(), img.getHeight());
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (img != null) {
			g2d.drawImage(img, getX(), getY(), null);
			drawLifeBar(g2d);
		}
	}

	public void drawLifeBar(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.drawRect(getX(), getY() + img.getHeight() - 4, img.getWidth(), 4);
		g2d.setColor(Color.GREEN);
		g2d.fillRect(getX() + 1, getY() + img.getHeight() - 3, img.getWidth() - 1, 3);
		g2d.setColor(Color.RED);
		double lost = (getLife() * 1.) / getMaxLife() * img.getWidth();
		g2d.fillRect(getX() + 1 + (int) lost, getY() + img.getHeight() - 3, img.getWidth() - 1 - (int) lost, 3);
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

	@Override
	public Rectangle getImageBounds() {
		imgrg.x = this.getX();
		imgrg.y = this.getY();
		return imgrg;
	}
}
