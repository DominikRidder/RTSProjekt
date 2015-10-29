package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;

interface ILayout {
	public void onDraw(Graphics2D g2d);
	
	public void onUpdate(Screen screen);
	
	public void addElement(GuiElement element);
}
