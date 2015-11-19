package gameScreens;

import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.GridLayout;
import gui.Panel;

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

		String adding[] = { "Einzelspieler", "Spiel Laden", "Archievments", "Highscore", "Einstellungen", "WorldEditor", "Exit" };

		Panel p = new Panel(345, 175, 150, 40 * adding.length);
		p.setLayout(new GridLayout(adding.length, 1, p));
		for (String toadd : adding) {
			Button newb = new Button(toadd);
			newb.setBackgroundColor(Color.BLACK);
			newb.setTextColor(Color.RED);
			newb.addActionListener(this);
			p.addElement(newb);
		}
		Button placeholder = new Button(0, 0, Game.getImageLoader().getImage("Hauptmenue.png"));
		placeholder.setWidth(getScreenFactory().getGame().getWindow().getWidth());
		placeholder.setHeight(getScreenFactory().getGame().getWindow().getHeight());
		addGuiElement(placeholder);

		addGuiElement(p);
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
		if (e.getActionCommand() == null) {
			return;
		}
		switch (e.getActionCommand()) { // name of the button
		case "Einzelspieler":
			this.getScreenFactory().showScreen(new GameScreen(this.getScreenFactory()));
			break;
		case "WorldEditor":
			this.getScreenFactory().showScreen(new WorldEditor(this.getScreenFactory()));
			break;
		case "Exit":
			System.exit(0);
			break;
		default:
			System.out.println("Unknown ActionEvent: " + e.getActionCommand());
			break;
		}
	}

}
