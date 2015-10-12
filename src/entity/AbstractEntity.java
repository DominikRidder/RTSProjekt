package entity;

import java.awt.Rectangle;

public abstract class AbstractEntity implements IEntity {
	private static int entityID = 0;
	private int x, y;

	public AbstractEntity(int x, int y) {
		this.x = x;
		this.y = y;
		entityID++;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public abstract Rectangle getBounds();

	public static int getEntityID() {
		return entityID;
	}

}
