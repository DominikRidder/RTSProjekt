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
import java.util.ArrayList;

public class SettingsScreen extends Screen implements ActionListener {
	private Panel subpanel;
	private int startx, starty;
	
	public SettingsScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {

		String adding[] = { "Zurück", "Game-Settings", "Steuerrung", "Audio", "Video", "Hauptmenü" , "Beenden"};
		
		int Wwidth = getScreenFactory().getGame().getWindow().getWidth();
		int Wheight = getScreenFactory().getGame().getWindow().getHeight();
		int width = Wwidth/5;
		int height = Wheight*2/5;
		
		startx = 0;
		starty = 0;
		if(getScreenFactory().getPrevScreen() instanceof GameScreen)
		{
			GameScreen e = (GameScreen)getScreenFactory().getPrevScreen();
			startx = e.viewX();
			starty = e.viewY();
		}
		
		
		Panel p = new Panel(Wwidth/2-width/2, Wheight/2-height/2, width, height);
		p.setLayout(new GridLayout(adding.length, 1, p));
		for (String toadd : adding) {
			
			Button newb = new Button(toadd);
			newb.setTextColor(Color.RED);
			newb.addActionListener(this);
			p.addElement(newb);
		}
		
		Button placeholder = new Button(startx, starty, Wwidth, Wheight, "");
		placeholder.setWidth(getScreenFactory().getGame().getWindow().getWidth());
		placeholder.setHeight(getScreenFactory().getGame().getWindow().getHeight());
		placeholder.setBackgroundColor(new Color(0, 0, 0, 0.5f));
		addGuiElement(placeholder);
		
		p.setX(p.getX()+startx);
		p.setY(p.getY()+starty);
		
		addGuiElement(p);
	}

	@Override
	public void onUpdate() {
		MousepadListener mpl = this.getScreenFactory().getGame()
				.getMousepadListener();
		
		mpl.setX(mpl.getX() + startx);
		mpl.setY(mpl.getY() + starty);
		
		super.onUpdate(); // updating Gui Elements
		
		mpl.setX(mpl.getX() + startx);
		mpl.setY(mpl.getY() + starty);
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		this.getScreenFactory().getPrevScreen().onDraw(g2d);
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
		//MainSettings
		case "Zurück":
			if(subpanel != null)
			{
				rmGuiElement(subpanel);
				subpanel = null;
				break;
			}
			this.getScreenFactory().swap();
			break;
		case "Audio":
			String adding[] = { "Zurück", "Volume"};
			
			int Wwidth = getScreenFactory().getGame().getWindow().getWidth();
			int Wheight = getScreenFactory().getGame().getWindow().getHeight();
			int width = Wwidth/5;
			int height = Wheight*2/5;
			subpanel = new Panel(Wwidth/2+width/2, Wheight/2-height/2, width, height);
			subpanel.setLayout(new GridLayout(adding.length, 1, subpanel));
			for (String toadd : adding) {
				Button newb = new Button(toadd);
				newb.setTextColor(Color.RED);
				newb.addActionListener(this);
				subpanel.addElement(newb);
			}
			
			subpanel.setX(subpanel.getX()+startx);
			subpanel.setY(subpanel.getY()+starty);
			
			addGuiElement(subpanel);
			break;
			
		case "WorldEditor":
			this.getScreenFactory().showScreen(new WorldEditor(this.getScreenFactory()));
			break;
		case "Hauptmenü":
			this.getScreenFactory().showScreen(new MainScreen(this.getScreenFactory()));
			break;
		case "Beenden":
			System.exit(0);
			break;
		//GameSettings:
			
		//Steuerrung:
		//Sound:
		case "Volume":
			System.out.println("Volume subpanel button test erfolgreich");
			break;
		//Video:
		
		default:
			System.out.println("Unknown ActionEvent: " + e.getActionCommand());
			break;
		}
	}

}
