package gameScreens;

import entity.AbstractEntity;
import gameEngine.KeyboardListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.ControlSettingField;
import gui.GridLayout;
import gui.Panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControlScreen extends Screen implements ActionListener {

	public ControlScreen(ScreenFactory screenFactory, Screen prevScreen) {
		super(screenFactory, prevScreen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		int width = getScreenFactory().getGame().getWindow().getWidth();

		Button placeholder = new Button(0, 0, width, getScreenFactory().getGame().getWindow().getHeight(), "", false, false);
		placeholder.setImage(null);
		placeholder.setBackgroundColor(new Color(0, 0, 0, 0.5f));
		addGuiElement(placeholder);

		Panel p = null;
		for (int j = 0; j < 4; j++)//Columns
		{
			p = new Panel(j * width / 4 + 5, 30, width / 4 - 10, 90 * 20);
			p.setLayout(new GridLayout(90, 1, p));
			for (int i = 33 + j * 25; i <= 58 + j * 25 && i <= 122; i++)//from space to z in the ascii table//some default button, at least you can add them :D
			{
				ControlSettingField newb = new ControlSettingField("" + (char) i, "btn_" + (char) i);
				newb.addActionListener(this);
				p.addElement(newb);
			}
			addGuiElement(p);
		}

		Button back = new Button(0, 0, 50, 20, "Zurueck", true, false);
		back.addActionListener(this);
		addGuiElement(back);

	}

	@Override
	public void onUpdate() {
		super.onUpdate();//update gui elements first!
		KeyboardListener kbl = this.getScreenFactory().getGame().getKeyboardListener();
		if (kbl.isOnPress("btn_ESC"))//ESC button
		{
			Button b = new Button("Zurueck", false, false);
			b.addActionListener(this);
			b.callActions();
		}

	}

	@Override
	public void onDraw(Graphics2D g2d) {
		if (prevscreen != null)
			prevscreen.onDraw(g2d);
		super.onDraw(g2d); // Drawing gui elements
	}

	@Override
	public List<AbstractEntity> getEntitys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == null) {
			return;
		}
		switch (e.getActionCommand()) { // name of the button
		//MainSettings
		case "Zurueck":
			this.getScreenFactory().activeScreen(prevscreen);
			break;
		}
	}

}
