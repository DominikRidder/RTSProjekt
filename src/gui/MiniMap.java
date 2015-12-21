package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class MiniMap extends GuiElement {
	private LayerPanel source;

	public MiniMap(int x, int y, int height, int width, LayerPanel source){
		this.source = source;
		setX(x);
		setY(y);
		setSize(width, height);
	}
	
	@Override
	public void onDraw(Graphics2D g2d) {
		AffineTransform transform = new AffineTransform();
		transform.translate(getX(), getY());
		transform.scale(((double)getWidth())/source.getWidth(), ((double)getHeight())/source.getHeight());
		g2d.transform(transform);
		
		source.onDraw(g2d);
		
		try {
			g2d.transform(transform.createInverse());//transform back to normal
		} catch (NoninvertibleTransformException e) {//THIS WILL NEVER NEVER NEVER ... happen!
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		// TODO Auto-generated method stub
		
	}
	
	
}
