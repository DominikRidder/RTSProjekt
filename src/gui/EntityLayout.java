package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.List;

import entity.AbstractEntity;
import gameEngine.Screen;

/**
 * This Class is used, to draw the Entitys between some other Layers.
 *
 */
public class EntityLayout implements ILayout{
	private List<AbstractEntity> entitys;

	public EntityLayout(List<AbstractEntity> entitys) {
		this.entitys = entitys;
	}
	
	@Override
	public void onDraw(Graphics2D g2d) {
		
		for (int i = 0; i < entitys.size(); i++) {
			entitys.get(i).draw(g2d);
		}
		for (int i = 0; i < entitys.size(); i++) {
			entitys.get(i).markOwn(g2d);
		}
	}


	@Override
	public void onUpdate(Screen screen) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addElement(GuiElement element) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void repack() {
		// TODO Auto-generated method stub
		
	}
}
