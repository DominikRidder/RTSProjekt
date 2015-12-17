package gameScreens;

import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.KeyboardListener;
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
	private int tickcounter = 20;
	
	public SettingsScreen(ScreenFactory screenFactory, Screen prevscreen) {
		super(screenFactory, prevscreen);
	}

	@Override
	public void onCreate() {

		String adding[] = { "Zurück", "Game-Settings", "Steuerrung", "Audio", "Video", "Hauptmenü" , "Beenden"};
		
		int Wwidth = getScreenFactory().getGame().getWindow().getWidth();
		int Wheight = getScreenFactory().getGame().getWindow().getHeight();
		int width = Wwidth/5;
		int height = Wheight*2/5;
			
		Panel p = new Panel(Wwidth/2-width/2, Wheight/2-height/2, width, height);
		p.setLayout(new GridLayout(adding.length, 1, p));
		for (String toadd : adding) {
			
			Button newb = new Button(toadd);
			newb.setTextColor(Color.RED);
			newb.addActionListener(this);
			p.addElement(newb);
		}
		
		Button placeholder = new Button(0, 0, Wwidth, Wheight, "");
		placeholder.setBackgroundColor(new Color(0, 0, 0, 0.5f));
		addGuiElement(placeholder);
		
		addGuiElement(p);
	}

	@Override
	public void onUpdate() {
		KeyboardListener kbl = this.getScreenFactory().getGame().getKeyboardListener();
		if(kbl.isOnPress("btn_ESC") && tickcounter == 0)//ESC button
		{
			Button b = new Button("Zurück");
			b.addActionListener(this);
			b.callActions();
		}
		if(tickcounter != 0)
		{
			tickcounter--;
		}	
		super.onUpdate(); // updating Gui Elements

	}

	@Override
	public void onDraw(Graphics2D g2d) {
		if(prevscreen instanceof SettingsScreen)
		{
			super.onDraw(g2d);
			return;
		}
		if(prevscreen != null)
			prevscreen.onDraw(g2d);
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
			this.getScreenFactory().activeScreen(prevscreen);
			break;
		case "Audio":
			String adding[] = { "Zurück", "Ton"};
			
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
			addGuiElement(subpanel);
			break;
			
		case "WorldEditor":
			this.getScreenFactory().createScreen(new WorldEditor(this.getScreenFactory()));
			break;
		case "Hauptmenü":
			this.getScreenFactory().createScreen(new MainScreen(this.getScreenFactory()));
			break;
		case "Beenden":
			System.exit(0);
			break;
		case "Steuerrung":
			this.getScreenFactory().createScreen(new ControlScreen(this.getScreenFactory(), this));
			break;
		//GameSettings:
			
		//Steuerrung:
		
		//Sound:
		case "Ton":
			Game.getSetting().switchValue("cl_s_sound");
			getScreenFactory().getGame().needSound(Game.getSetting().getValueBool("cl_s_sound"));
			break;
		//Video:
		
		default:
			System.out.println("Unknown ActionEvent: " + e.getActionCommand());
			break;
		}
	}

}
