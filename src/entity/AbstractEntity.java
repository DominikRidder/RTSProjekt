package entity;

import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class AbstractEntity implements IEntity, Comparable<AbstractEntity> {
	private final int entityID;
	private static int entityCounter = 0;
	private static ArrayList<AbstractEntity> l_Entities = new ArrayList<AbstractEntity>(20);
	private int x, y;

	public AbstractEntity(int x, int y) {
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
	
	public void addX(int x){
		this.x +=x;
	}
	
	public void addY(int y){
		this.y +=y;
	}

	public abstract Rectangle getBounds();
	
	public abstract Rectangle getImageBounds();
	
	public AbstractEntity hasCollision() {//sucht automatisch die naechste hitbox
		for(AbstractEntity e : l_Entities) {
			if(e.entityID == this.entityID){
				continue;
			}
			if(isCollision(e)){
				return e;
			}
		}
		return null;
	}
	
	public boolean isCollision(AbstractEntity e) {
		Rectangle rg1 = e.getBounds();
		Rectangle rg2 = this.getBounds();
		if((Math.abs(e.getX() - this.getX()) < (rg1.getWidth() + rg2.getWidth()) )
				&& (Math.abs(e.getY() - this.getY()) < (rg1.getHeight() + rg2.getHeight()))) {
			return true;
		}
		return false;
	}
	
	public double distance(AbstractEntity b) {
		return Math.sqrt(Math.pow(Math.abs(this.getX()-b.getX()), 2)+Math.pow(Math.abs(this.getY()-b.getY()), 2));
	}
	
	public int getEntityID() {
		return entityID;
	}

	@Override
	/** compares for graphics only!
	 *  The one that is most to the top left corner, ist the first one who gets rendered
	 */
	public int compareTo(AbstractEntity o) {
		if(this.getY() == o.getY()){
			return this.getX() < o.getX() ? -1 : 1;
		}
		return this.getY() < o.getY() ? -1 : 1;
	}

}
