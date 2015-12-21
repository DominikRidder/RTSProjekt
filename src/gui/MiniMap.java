package gui;

import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class MiniMap extends GuiElement {
	private final LayerPanel source;
	private int widht, height;
	private final int starty;

	public MiniMap(int x, int y, int height, int width, LayerPanel source){
		this.source = source;
		setX(x);
		setY(y);
		setSize(width, height);
		starty = y;
		this.widht = 0;
		this.height = 0;
	}
	
	@Override
	public void onDraw(Graphics2D g2d) {
		AffineTransform transform = new AffineTransform();
		transform.translate(getX(), getY());
		transform.scale(((double)getWidth())/source.getWidth(), ((double)getHeight())/source.getHeight());
		
		g2d.transform(transform);
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, source.getWidth(), source.getHeight());
		g2d.setClip(getX(), getY()-starty, widht, height);
		
		source.onDraw(g2d);
		g2d.setClip(null);
		g2d.setColor(Color.RED);
		for(int i = 0; i > -20; i--)
			g2d.draw3DRect(getX()+i, getY()-starty+i, widht-2*i, height-2*i, false);
		
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
		widht = screen.getScreenFactory().getGame().getWidth();
		height = screen.getScreenFactory().getGame().getHeight();
	}
	
	
}
