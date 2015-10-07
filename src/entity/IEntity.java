package entity;
import java.awt.Graphics2D;

public interface IEntity {

	public int getX();

	public int getY();

	public void setX(int x);

	public void setY(int y);

	public void update();

	public void draw(Graphics2D g2d);

}
