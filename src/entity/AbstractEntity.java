package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntity implements IEntity, Comparable<AbstractEntity>, IHealth {
	private final int entityID;
	private static int entityCounter = 0;
	private static List<AbstractEntity> l_Entities = new ArrayList<AbstractEntity>();
	private int x, y;
	
	protected static BufferedImage img;
	protected Rectangle rg;
	protected Rectangle imgrg;
	
	protected int maxLife;
	protected int life;
	protected int minDmg;
	protected int maxDmg;

	public AbstractEntity(int x, int y, String img_name) {
		this.x = x;
		this.y = y;
		entityID = entityCounter;
		entityCounter++;
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
	
	public static List<AbstractEntity> getEntities()
	{
		return l_Entities;
	}

	public abstract Rectangle getBounds();

	public abstract Rectangle getImageBounds();

	public AbstractEntity hasCollision() {//sucht automatisch die naechste hitbox
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
		return e.getBounds().intersects(getBounds());
		/*Rectangle rg1 = e.getBounds();
		Rectangle rg2 = this.getBounds();
		if ((Math.abs(e.getX() - this.getX()) < (rg1.getWidth() + rg2.getWidth())) && (Math.abs(e.getY() - this.getY()) < (rg1.getHeight() + rg2.getHeight()))) {
			return true;
		}
		return false;*/
	}

	public double distance(AbstractEntity b) {
		return Math.sqrt(Math.pow(Math.abs(this.getX() - b.getX()), 2) + Math.pow(Math.abs(this.getY() - b.getY()), 2));
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
	
	public AbstractEntity getEntity(int ID)
	{
		for(int i = 0; i < l_Entities.size(); i++)
		{
			if(l_Entities.get(i).getEntityID() == ID)
				return l_Entities.get(i);
		}
		return null;
	}
	/*Drawing Stuff*/

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(img, getX(), getY(), null);
		drawLifeBar(g2d);
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
	
	/*IHelath*/
	
	@Override
	public boolean die()
	{
		return l_Entities.remove(this);//This should be dead now!
	}
	
	@Override
	public int getRandDmg() {
		// TODO Auto-generated method stub
		return getMinDmg() + ((int) (Math.random() * ((getMaxDmg() - getMinDmg()) + 1)));
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
	public boolean checkDeath()
	{
		if(this.life <= 0)
		{
			this.die();
			return true;
		}
		return false;
	}
	

	@Override
	public boolean takeDamage(int dmg) {
		// TODO Auto-generated method stub
		if(this.getLife() <= 0)
			return false;
		this.life-=dmg;
		if(this.life < 0)
			this.life = 0;
		return true;
	}
}
