package gameScreens;

import entity.AbstractEntity;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainScreen extends Screen implements ActionListener {
	
	public MainScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		Button start = new Button(325, 60, 150, 50, "Einzelspieler");
		start.addActionListener(this);
		addGuiElement(start);
		
		Button load = new Button(0, 60, 150, 50, "Spiel Laden", start);
		load.addActionListener(this);
		addGuiElement(load);
		
		Button archievments = new Button(0 , 60, 150, 50, "Archievments", load);
		archievments.addActionListener(this);
		addGuiElement(archievments);
		
		Button highscore = new Button(0, 60, 150, 50, "Highscore", archievments);
		highscore.addActionListener(this);
		addGuiElement(highscore);
		
		Button einstellungen = new Button(0, 60, 150, 50, "Einstellungen", highscore);
		einstellungen.addActionListener(this);
		addGuiElement(einstellungen);
		
		Button exit = new Button(0, 60, 150, 50, "Exit", einstellungen);
		exit.addActionListener(this);
		addGuiElement(exit);
	}

	@Override
	public void onUpdate() {
		super.onUpdate(); // updating Gui Elements

	}

	@Override
	public void onDraw(Graphics2D g2d) {
		super.onDraw(g2d); // Drawing gui elements
	}

	@Override
	public ArrayList<AbstractEntity> getEntitys() {
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){ // name of the button
		case "Einzelspieler": this.getScreenFactory().showScreen(new GameScreen(this.getScreenFactory())); break;
		case "Exit":System.exit(0);break;
		default:System.out.println("Unknown ActionEvent: "+e.getActionCommand()); break;
		}
	}

}
