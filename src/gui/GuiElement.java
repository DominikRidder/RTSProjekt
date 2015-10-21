package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class GuiElement implements Comparable<GuiElement> {

	private ArrayList<ActionListener> actionlistener = new ArrayList<ActionListener>(
			1);
	private int layer;
	private int x, y;
	private GuiElement relativto;

	public void setRelativTo(GuiElement relativto){
		this.relativto = relativto;
	}
	
	public GuiElement getRelativTo(){
		return relativto;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getLayer() {
		return layer;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return relativto == null ? x : x+relativto.getX();
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return relativto == null ? y : y+relativto.getY();
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
}
