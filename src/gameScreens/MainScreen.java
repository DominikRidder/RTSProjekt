package gameScreens;

import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.GridLayout;
import gui.Panel;

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

		String adding[] = { "Einzelspieler","Quick Start", "Spiel Laden", "Archievments", "Highscore", "Einstellungen", "WorldEditor", "Exit" };

		while(getScreenFactory().getGame().getWindow().getWidth() < 50 || getScreenFactory().getGame().getWindow().getHeight( ) <50){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		int Wwidth = getScreenFactory().getGame().getWindow().getWidth();
		int Wheight = getScreenFactory().getGame().getWindow().getHeight();
		int width = Wwidth/5;
		int height = Wheight*2/5;
		Panel p = new Panel(Wwidth/2-width/2, Wheight/2-height/2, width, height);
		p.setLayout(new GridLayout(adding.length, 1, p));
		for (String toadd : adding) {
			Button newb = new Button(toadd);
			newb.addActionListener(this);
			p.addElement(newb);
		}
		
		
		Button placeholder = new Button(0, 0, Game.getImageManager().getImage("Hauptmenue.png"));
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
			this.getScreenFactory().createScreen(new StoryScreen(this.getScreenFactory()));
			break;
		case "Quick Start":
			this.getScreenFactory().createScreen(new GameScreen(this.getScreenFactory()));
			break;
		case "WorldEditor":
			this.getScreenFactory().createScreen(new WorldEditor(this.getScreenFactory()));
			break;
		case "Einstellungen":
			this.getScreenFactory().createScreen(new SettingsScreen(this.getScreenFactory(), this));
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
