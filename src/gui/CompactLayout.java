package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * This Layout only secures, that GuiElements, which are added to this Layout,
 * are in the Borders of the given Panel.
 */
public class CompactLayout implements ILayout {

	private ArrayList<GuiElement> container;
	private APanel parent;

	private int lastx,lasty;
	
	public CompactLayout(APanel parent) {
		container = new ArrayList<GuiElement>();
		this.parent = parent;
		lastx = parent.getX();
		lasty = parent.getY();
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		for (GuiElement elem : container){
			elem.onDraw(g2d);
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		repack();
		for (GuiElement elem : container){
			elem.onUpdate(screen);
		}

	}

	@Override
	public void addElement(GuiElement element) {
		container.add(element);
		if (element.getWidth() > parent.getWidth()){
			element.setWidth(parent.getWidth());
		}
		if (element.getHeight() > parent.getHeight()){
			element.setHeight(parent.getHeight());
		}
		
		if (element.getX() < parent.getX()){
			element.setX(parent.getX());
		}else if (element.getX()+element.getWidth() > parent.getX()+parent.getWidth()){
			element.setX(parent.getX()+parent.getWidth()-element.getWidth());
		}
		
		if (element.getY() < parent.getY()) {
			element.setY(parent.getY());
		} else if (element.getY()+element.getHeight() > parent.getY()+parent.getHeight()) {
			element.setY(parent.getY()+parent.getHeight() - element.getHeight());
		}
	}

	@Override
	public void repack() {
		if (parent.needUpdate()){
			for (GuiElement elem : container){
				elem.setX(elem.getX()+parent.getX()-lastx);
				elem.setY(elem.getY()+parent.getY()-lasty);
			}
			lastx = parent.getX();
			lasty = parent.getY();
		}
	}
}
