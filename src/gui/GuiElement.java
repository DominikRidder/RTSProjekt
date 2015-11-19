package gui;

import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GuiElement implements Comparable<GuiElement> {

	private ArrayList<ActionListener> actionlistener = new ArrayList<ActionListener>(1);
	private int layer;
	private Rectangle bounds = new Rectangle(0,0,0,0);
	private boolean update;
	private boolean border = false;
	
	
	public Rectangle getBounds(){
		return bounds;
	}
	
	public void setSize(int width, int height){
		setWidth(width);
		setHeight(height);
	}
	
	public void setUpdate(boolean update){
		this.update = update;
	}

	public boolean needUpdate(){
		return update;
	}

	public void setWidth(int width){
		bounds.width = width;
		update = true;
	}

	public int getWidth() {
		return bounds.width;
	}
	
	public void setHeight(int height){
		bounds.height = height;
		update = true;
	}
	
	public int getHeight(){
		return bounds.height;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
		update = true;
	}


	public int getLayer() {
		return layer;
	}

	public void setX(int x) {
		bounds.x = x;
		update = true;
	}

	public int getX() {
		return bounds.x;
	}

	public void setY(int y) {
		bounds.y = y;
		update = true;
	}

	public int getY() {
		return bounds.y;
	}


	public void addActionListener(ActionListener actionl) {
		actionlistener.add(actionl);
	}

	public ArrayList<ActionListener> getActionListener() {
		return actionlistener;
	}

	public abstract void onDraw(Graphics2D g2d);

	public abstract void onUpdate(Screen screen);

	public int compareTo(GuiElement arg0) {
		if (this.layer > arg0.layer) {
			return 1;
		} else if (this.layer < arg0.layer) {
			return -1;
		} else {
			return 0;
		}
	}

	public void drawBorder(Graphics2D g2d) {
		if (border) {
			g2d.setColor(Color.BLACK);
			g2d.drawLine(getX(), getY(), getX() + getWidth(), getY());
			g2d.drawLine(getX(), getY(), getX(), getY() + getHeight());
			g2d.drawLine(getX() + getWidth(), getY(), getX() + getWidth(), getY() + getHeight());
			g2d.drawLine(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight());
		}
	}

	public void setBorder(boolean border) {
		this.border = border;
	}
}
