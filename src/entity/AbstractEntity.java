package entity;

import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class AbstractEntity implements IEntity {
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

	public abstract Rectangle getBounds();
	
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
		//if((Math.abs(e.getX() - this.getX()) < e.getBounds().x+this.getBounds().x)
			//&& (Math.abs(e.getY() - this.getY()) < e.getBounds().y+this.getBounds().y)){
		if((Math.abs(e.getX() - this.getX()) < (e.getBounds().getWidth() + this.getBounds().getWidth()) )
				&& (Math.abs(e.getY() - this.getY()) < (e.getBounds().getHeight() + this.getBounds().getHeight()))) {
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

}
