package entity;

public class OrkTest extends Unit {

	// private static BufferedImage img;
	// private final Rectangle rg;
	// private final Rectangle imgrg;
	private static final int rad = 6;
	private static final String img_name = "Orc.png";
	private static final int life = 8;
	private static final int maxlife = 20;
	private static final int mindmg = 1;
	private static final int maxdmg = 3;

	public OrkTest(int x, int y, int owner) {
		super(x, y, rad, img_name, owner);
		setMaxLife(maxlife);
		setLife(life);
		setMinDmg(mindmg);
		setMaxDmg(maxdmg);
	}

	@Override
	public void update() {

	}
}
