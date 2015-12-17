package gui;

import gameEngine.Game;
import gameEngine.KeyboardListener;
import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;

public class ControlSettingField extends GuiElement {

	private char text;
	private boolean hasfocus;
	private String label = "";
	private final String setting;
	private final Color textcolor = Color.WHITE, backgroundcolor = Color.BLACK;

	public ControlSettingField(int x, int y, int width, int height, String stng) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		text = (char)Game.getSetting().getValueInt(stng);
		setting = stng;
	}
	
	public ControlSettingField(String l, String stng)
	{
		label = l;
		text = (char)Game.getSetting().getValueInt(stng);
		setting = stng;
		setWidth(50);
		setHeight(20);
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.setColor(backgroundcolor);
		g2d.fill3DRect(getX(), getY(), getWidth(), getHeight(), false);
		if(label.length() > 0)
		{
			g2d.setColor(textcolor);
			g2d.drawString("'"+label+"'-Knopf:", getX(), getY() + getHeight() / 2);
			if(hasfocus)
				g2d.setColor(Color.GREEN);
			g2d.drawString(""+text, getX()+100, getY() + getHeight() / 2);
		}
		else
			g2d.drawString(""+text, getX(), getY() + getHeight() / 2);
		this.drawBorder(g2d);
	}

	@Override
	public void onUpdate(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();
		KeyboardListener kbl = screen.getScreenFactory().getGame()
				.getKeyboardListener();
		
		if (mpl.isLeftClicked()) {
			if (this.getBounds().contains(mpl.getX(), mpl.getY())) {
				hasfocus = true;
			}
			else
			{
				hasfocus = false;//abwählen der Textbox
			}
		}
		if(mpl.isRightClicked() || kbl.isKeyPressed("btn_ESC"))
		{
			hasfocus = false;
		}
		if (kbl.getPressedKeys().size() != 0 && hasfocus){
			text = (char)kbl.getPressedKeys().get(kbl.getPressedKeys().size()-1).intValue();
			Game.getSetting().setValue(setting, text);
			hasfocus = false;
		}
	}

}
