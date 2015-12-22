package gui;

import entity.AbstractEntity;
import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class EntityOptions extends GuiElement{
	private ArrayList<Button> options;
	private AbstractEntity calledby;
	
	public static EntityOptions singleton = new EntityOptions();
	
	private EntityOptions() {
		
	}
	
	public void setOptions(ArrayList<Button> options, AbstractEntity entity) {
		this.options = options;
		calledby = entity;
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		if (options == null){
			return;
		}
		g2d.setColor(new Color(0, 0, 0, 0.5f));
		
		
		for (int i=0; i<options.size(); i++){
			options.get(i).onDraw(g2d);
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		// TODO Auto-generated method stub
		
	}
	
}
