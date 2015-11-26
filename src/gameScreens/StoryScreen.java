package gameScreens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;

public class StoryScreen extends Screen {
	
	private static final int RACE_HUMAN = 0, RACE_SHADOW=1, RACE_ANTIK=2;
	
	private String imglocation[] = {"story_hum.png", "story_sha.png", "story_ant.png"};
	private String humMission[] = {"a","b", "c"};
	private String shaMission[] = {"a","b"};
	private String bothMission[] = {"a","b","c","d"};
	
	private ArrayList<Button> bothm;
	private ArrayList<Button> humm;
	private ArrayList<Button> sham;
	
	public StoryScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		int maxunique = humMission.length > shaMission.length ? humMission.length : shaMission.length;
		
		int misheight = maxunique + bothMission.length;
		int relHeight = getScreenFactory().getGame().getWindow().getHeight()/ (misheight+2);
		int width = getScreenFactory().getGame().getWindow().getWidth(); 
		
		bothm = new ArrayList<Button>();
		humm = new ArrayList<Button>();
		sham = new ArrayList<Button>();
		
		for (int i=0; i<bothMission.length; i++){
			Button b = new Button(width/2-50, relHeight*(bothMission.length-i), 100, relHeight/2, "Both "+i);
			this.addGuiElement(b);
			bothm.add(b);
		}
		
		for (int i=humMission.length-1; i>=0; i--){
			Button b = new Button((int) (width/4-50+width/4*(((double)i)/(humMission.length))),(int) (relHeight*(bothMission.length)+relHeight*maxunique*((double)humMission.length-i)/(humMission.length)), 100, relHeight/2, "Human "+i);
			this.addGuiElement(b);
			humm.add(b);
		}
		
		for (int i=shaMission.length-1; i>=0; i--){
			Button b = new Button((int) (width/4*3-50-width/4*(((double)i)/(shaMission.length))), (int) (relHeight*(bothMission.length)+relHeight*maxunique*((double)shaMission.length-i)/(shaMission.length)), 100, relHeight/2, "shadow "+i);
			this.addGuiElement(b);
			sham.add(b);
		}
	}

	@Override
	public List<AbstractEntity> getEntitys() {
		return null;
	}
	
	public void onDraw(Graphics2D g2d){
		Button b1 = humm.get(0);
		Button b2 = null;
		
		g2d.setColor(Color.black);
		
		for (int i=1; i<humm.size(); i++){
			b2 = humm.get(i);
			drawLine(b1, b2, g2d);
			b1 = b2;
		}
		b2 = bothm.get(0);
		drawLine(b1, b2, g2d);
		
		b1 = sham.get(0);
		for (int i=1; i<sham.size(); i++){
			b2 = sham.get(i);
			drawLine(b1, b2, g2d);
			b1 = b2;
		}
		b2 = bothm.get(0);
		drawLine(b1, b2, g2d);
		
		b1 = bothm.get(0);
		for (int i=1; i<bothm.size(); i++){
			b2 = bothm.get(i);
			drawLine(b1, b2, g2d);
			b1 = b2;
		}
		
		
		
		super.onDraw(g2d);
	}
	
	public void drawLine(Button b1, Button b2, Graphics2D g2d){
		g2d.drawLine(b1.getX()+b1.getWidth()/2, b1.getY(), b2.getX()+b2.getWidth()/2, b2.getY());
	}
}
