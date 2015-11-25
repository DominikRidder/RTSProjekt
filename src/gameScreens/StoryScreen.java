package gameScreens;

import java.util.List;

import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;

public class StoryScreen extends Screen {
	
	private static final int RACE_HUMAN = 0, RACE_SHADOW=1, RACE_ANTIK=2;
	
	private String imglocation[] = {"story_hum.png", "story_sha.png", "story_ant.png"};
	private String humMission[] = {"a","b"};
	private String shaMission[] = {"a","b","c"};
	private String bothMission[] = {"a","b","c","d"};
	
	public StoryScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		int maxunique = humMission.length > shaMission.length ? humMission.length : shaMission.length;
		
		int misheight = maxunique + bothMission.length;
		int relHeight = getScreenFactory().getGame().getWindow().getHeight()/ (misheight+2);
		
		int width = getScreenFactory().getGame().getWindow().getWidth(); 
		
		for (int i=0; i<bothMission.length; i++){
			Button b = new Button(width/2-50, relHeight*(i+1), 100, relHeight/2, "Both");
			this.addGuiElement(b);
		}
		
		for (int i=humMission.length-1; i>=0; i--){
			Button b = new Button(width/5-50+width/5*(humMission.length-1-i), relHeight*(i+1+bothMission.length), 100, relHeight/2, "Human");
			this.addGuiElement(b);
		}
		
		for (int i=shaMission.length-1; i>=0; i--){
			Button b = new Button(width/5*4-width/5*(shaMission.length-1-i), relHeight*(i+1+bothMission.length), 100, relHeight/2, "Shadow");
			this.addGuiElement(b);
		}
	}

	@Override
	public List<AbstractEntity> getEntitys() {
		return null;
	}
}
