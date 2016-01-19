package entity;

public class Worker extends Unit {

	protected static final ResourceInfo res = new ResourceInfo(50, 0, 0);
	protected static final ProduceInfomation produceInformation = new ProduceInfomation(res, description());

	private static final int rad = 6;
	private static final String img_name = "worker.png";
	private static final int maxlife = 35;
	private static final int life = maxlife;
	private static final int mindmg = 2;
	private static final int maxdmg = 6;

	public Worker(int x, int y, int owner) {
		super(x, y, rad, img_name, owner);
		setMaxLife(maxlife);
		setLife(life);
		setMinDmg(mindmg);
		setMaxDmg(maxdmg);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public static String description() {
		return "Worker";
	}

}
