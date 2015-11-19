package entity;

import java.awt.Rectangle;


public class OrkTest extends Unit {

	//private static BufferedImage img;
	//private final Rectangle rg;
	//private final Rectangle imgrg;
	private static final int breite = 12;//Hitboxsizes
	private static final int hoehe = 12;
	private static final String img_name = "Orc.png";
	private static final int life = 8;
	private static final int maxlife = 20;
	private static final int mindmg = 1;
	private static final int maxdmg = 3;

	public OrkTest(int x, int y) {
		super(x, y, img_name);
		rg = new Rectangle(getX(), getY(), breite, hoehe);
		setMaxLife(maxlife);
		setLife(life);
		setMinDmg(mindmg);
		setMaxDmg(maxdmg);
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
