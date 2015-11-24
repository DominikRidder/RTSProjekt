package gui;

import gameEngine.KeyboardListener;
import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.awt.Color;
import java.awt.Graphics2D;

public class TextField extends GuiElement {

	private String text;
	private boolean hasfocus;
	private long wait;
	private char lastchar;

	public TextField(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		text = "";
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.fill3DRect(getX(), getY(), getWidth(), getHeight(), false);
		g2d.drawString(text, getX(), getY() + getHeight() / 2);
		this.drawBorder(g2d);
	}

	@Override
	public void onUpdate(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();
		KeyboardListener kbl = screen.getScreenFactory().getGame()
				.getKeyboardListener();

		if (mpl.isLeftClicked()) {
			if (!hasfocus) {
				if (this.getBounds().contains(mpl.getX(), mpl.getY())) {
					hasfocus = true;
				}
			}
		}

		if (kbl.getPressedKeys().size() != 0){
		if (lastchar != (char)kbl.getPressedKeys().get(kbl.getPressedKeys().size()-1).intValue()){
			lastchar = (char)kbl.getPressedKeys().get(kbl.getPressedKeys().size()-1).intValue();
			if (Character.isBmpCodePoint(lastchar)){
				text+=lastchar;
			}
		}
		}else{
			lastchar = '-';
		}
	}

}
