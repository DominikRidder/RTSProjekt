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
		
		String adding[] = {"Spiel Laden", "Archievments", "Highscore", "Einstellungen", "Exit"};
		
		Button lastbutton = start;
		for (String toadd : adding){
			Button newb = new Button(0, 60, 150, 50, toadd, lastbutton);
			newb.setBackgroudnColor(Color.BLACK);
			newb.setTextColor(Color.RED);
			newb.addActionListener(this);
			addGuiElement(newb);
			
			lastbutton = newb;
		}
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
