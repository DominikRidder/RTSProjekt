package gameScreens;

import entity.AbstractEntity;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.GridLayout;
import gui.Panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MainScreen extends Screen implements ActionListener {

	public MainScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		String adding[] = { "Einzelspieler","Spiel Laden", "Archievments", "Highscore",
				"Einstellungen", "Exit" };
		
		Panel p = new Panel(250, 100, 250, 250);
		p.setLayout(new GridLayout(adding.length,1,p));
		for (String toadd : adding) {
			Button newb = new Button(toadd);
			newb.setBackgroundColor(Color.BLACK);
			newb.setTextColor(Color.RED);
			newb.addActionListener(this);
			p.addElement(newb);
		}
		this.addGuiElement(p);
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
		if (e.getActionCommand() == null){
			return;
		}
		switch (e.getActionCommand()) { // name of the button
		case "Einzelspieler":
			this.getScreenFactory().showScreen(
					new GameScreen(this.getScreenFactory()));
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
