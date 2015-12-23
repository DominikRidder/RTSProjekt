package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.KeyboardListener;
import gameEngine.MousepadListener;
import gameEngine.Screen;

public class EntityOptions extends GuiElement{
	private ArrayList<Button> options;
	private AbstractEntity calledby;
	private final String img_name;
	private final Rectangle m_bounds = new Rectangle(0,0,0,0);
	private boolean marked;
	
	public static EntityOptions singleton = new EntityOptions("menubutton.png");
	
	private EntityOptions(String imgname) {
		img_name = imgname;
	}
	
	public BufferedImage getImg()
	{
		return Game.getImageManager().getImage(img_name);
	}
	
	public void setOptions(ArrayList<Button> options, AbstractEntity entity) {
		this.options = options;
		calledby = entity;
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		if (options == null || options.size() == 0){
			return;
		}
		g2d.setColor(new Color(0, 0, 0, 0.5f));
		
		Button last = options.get(options.size()-1);
		Button first = options.get(0);
		m_bounds.width = last.getX()-first.getX()+last.getWidth()+40;
		m_bounds.height =  last.getY()-first.getY()+last.getHeight()+40;
		m_bounds.x = getX()+getWidth()-m_bounds.width;
		m_bounds.y = getY()+getHeight()-m_bounds.height;
		//g2d.fillRoundRect(x, y, width, height, last.getWidth()/2+10, last.getHeight()/2+10);
		g2d.drawImage(getImg(), m_bounds.x, m_bounds.y, m_bounds.width, m_bounds.height, null);
		for (int i=0; i<options.size(); i++){
			options.get(i).setX(m_bounds.x+i*50+20);
			options.get(i).setY(m_bounds.y+20);
			options.get(i).onDraw(g2d);
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		// TODO Auto-generated method stub
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();
		
		if (mpl.isLeftClicked()) {
			marked = m_bounds.contains(mpl.getX(), mpl.getY()) ? true : false;
		}
		
		if (options == null){
			return;
		}
		for (int i=0; i<options.size(); i++){
			options.get(i).onUpdate(screen);
		}
		
		KeyboardListener kbl = screen.getScreenFactory().getGame().getKeyboardListener();

		for (int i=0; i<options.size(); i++){
			if (kbl.isOnPress("btn_"+(i+1))){
				options.get(i).callActions();
			}
		}
	}
	
	public boolean isMarked() {
		return marked;
	}
}
