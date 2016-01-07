package entity;

import gameEngine.Game;
import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntity implements IEntity,
		Comparable<AbstractEntity>, IHealth, ITasks {

	private final int entityID;
	private static int entityCounter = 0;
	private static List<AbstractEntity> l_Entities = new ArrayList<AbstractEntity>();
	private int x, y;
	protected task m_curtask = task.t_none;
	protected int rad;

	// static BufferedImage img;
	protected Rectangle imgrg;

	protected int maxLife = 1;// default value
	protected int life = 1;// default value
	protected int minDmg = 0;// default value
	protected int maxDmg = 0;// default value
	protected String img_name;// will maybe set, or imgnotfound.png

	protected int owner;
	static int counter = 0;

	public AbstractEntity(int x, int y, int rad, String imgname, int ownr) {
		this.x = x;
		this.y = y;
		this.rad = rad;
		owner = ownr;
		entityID = entityCounter;
		entityCounter++;
		img_name = imgname;
		imgrg = new Rectangle(getX() - getImg().getWidth() / 2, getY()
				- getImg().getHeight(), getImg().getWidth(), getImg()
				.getHeight());
		l_Entities.add(this);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	public void addX(int x) {
		this.x += x;
	}

	public void addY(int y) {
		this.y += y;
	}

	public static List<AbstractEntity> getEntities() {
		return l_Entities;
	}

	public Rectangle getBounds() {
		Rectangle rg = new Rectangle(getX() - rad / 2, getY() - rad / 2, rad,
				rad);
		return rg;
	}

	public Rectangle getImageBounds() {
		imgrg.x = this.getX() - getImg().getWidth() / 2;
		imgrg.y = this.getY() - getImg().getHeight();
		return imgrg;
	}

	public int getOwner() {
		return owner;
	}

	public AbstractEntity hasCollision() {// sucht automatisch die naechste
											// hitbox
		for (int i = 0; i < l_Entities.size(); i++) {
			if (l_Entities.get(i).entityID == this.entityID) {
				continue;
			}
			if (isCollision(l_Entities.get(i))) {
				return l_Entities.get(i);
			}
		}
		return null;
	}

	public boolean isCollision(AbstractEntity e) {
		return e.rad + this.rad > distance(e);
		/*
		 * Rectangle rg1 = e.getBounds(); Rectangle rg2 = this.getBounds(); if
		 * ((Math.abs(e.getX() - this.getX()) < (rg1.getWidth() +
		 * rg2.getWidth())) && (Math.abs(e.getY() - this.getY()) <
		 * (rg1.getHeight() + rg2.getHeight()))) { return true; } return false;
		 */
	}

	public double distance(AbstractEntity b) {
		return Math.sqrt(Math.pow(Math.abs(this.getX() - b.getX()), 2)
				+ Math.pow(Math.abs(this.getY() - b.getY()), 2));
	}

	public int getEntityID() {
		return entityID;
	}

	@Override
	/** compares for graphics only!
	 *  The one that is most to the top left corner, ist the first one who gets rendered
	 */
	public int compareTo(AbstractEntity o) {
		if (this.getY() == o.getY()) {
			return this.getX() < o.getX() ? -1 : 1;
		}
		return this.getY() < o.getY() ? -1 : 1;
	}

	public AbstractEntity getEntity(int ID) {
		for (int i = 0; i < l_Entities.size(); i++) {
			if (l_Entities.get(i).getEntityID() == ID)
				return l_Entities.get(i);
		}
		return null;
	}

	/* Drawing Stuff */
	public BufferedImage getImg() {
		return Game.getImageManager().getImage(img_name, owner);
	}

	@Override
	public void draw(Graphics2D g2d) {
		getImageBounds();// Update the image position
		drawImage(g2d);
		drawLifeBar(g2d);
	}

	public void drawImage(Graphics2D g2d) {
		// this may gets overwritten in some classes (like tree)
		// System.out.println(img_name);
		g2d.drawImage(getImg(), imgrg.x, imgrg.y, null);
	}

	public void markOwn(Graphics2D g2d) {
		getImageBounds();// Update the image position, just in case
		drawAura(g2d, imgrg.x, imgrg.y, img_name, owner);
	}

	public void drawLifeBar(Graphics2D g2d) {
		BufferedImage img = getImg();
		if (img == null) { // Sometime the images aren't loaded until here...
			return;
		}
		g2d.setColor(Color.BLACK);
		g2d.drawRect(imgrg.x, imgrg.y + img.getHeight() + 3, img.getWidth(), 4);
		g2d.setColor(Color.GREEN);
		g2d.fillRect(imgrg.x + 1, imgrg.y + img.getHeight() + 4,
				img.getWidth() - 1, 3);
		g2d.setColor(Color.RED);
		double lost = (getLife() * 1.) / getMaxLife() * img.getWidth();
		g2d.fillRect(imgrg.x + 1 + (int) lost, imgrg.y + img.getHeight() + 4,
				img.getWidth() - 1 - (int) lost, 3);
	}

	public static void drawAura(Graphics2D g2d, int x, int y, String imgname,
			int owner) {
		if (owner == -1)// no owner? has no aura
			return;
		g2d.drawImage(Game.getImageManager().getImageAura(imgname, owner), x,
				y, null);
	}

	public static Color getOwnerColor(int owner) {
		switch (owner) {
		case 0:
			return Color.red;
		case 1:
			return Color.blue;
		case 2:
			return Color.green;
		case 3:
			return Color.black;
		case 4:
			return Color.cyan;
		case 5:
			return Color.yellow;
		case 6:
			return Color.orange;
		case 7:
			return Color.magenta;
		default:
			return Color.white;
		}
	}

	/* IHelath */

	@Override
	public boolean die() {
		return l_Entities.remove(this);// This should be dead now!
	}

	@Override
	public int getRandDmg() {
		// TODO Auto-generated method stub
		return getMinDmg()
				+ ((int) (Math.random() * ((getMaxDmg() - getMinDmg()) + 1)));
	}

	@Override
	public int getMinDmg() {
		return minDmg;
	}

	@Override
	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}

	@Override
	public int getMaxDmg() {
		return maxDmg;
	}

	@Override
	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int life) {
		this.maxLife = life;
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public void setLife(int life) {
		this.life = life;
	}

	@Override
	public boolean checkDeath() {
		if (this.life <= 0) {
			this.die();
			return true;
		}
		return false;
	}

	@Override
	public boolean takeDamage(int dmg, AbstractEntity dmgdealer) {
		// TODO Auto-generated method stub
		if (this.getLife() <= 0)
			return false;
		this.life -= dmg;
		if (this.life < 0)
			this.life = 0;
		return true;
	}

	protected void doTask(Screen screen) {
		switch (m_curtask) {
		case t_none:
			taskNone(screen);
			break;
		case t_wood:
			taskWood(screen);
			break;
		case t_stone:
			taskStone(screen);
			break;
		case t_mining:
			taskMining(screen);
			break;
		case t_attack:
			taskAttack(screen);
			break;
		case t_defend:
			taskDefend(screen);
			break;
		case t_spawn:
			taskSpawn(screen);
			break;
		case t_build:
			taskBuild(screen);
			break;
		default:
			System.out.println("Not handeld task: " + m_curtask);
			break;
		}
	}

	public void taskNone(Screen screen) {

	}
}
