package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.KeyboardListener;
import gameEngine.MousepadListener;
import gameEngine.Screen;

public class EntityOptions extends GuiElement {
	private ArrayList<Button> options;
	private AbstractEntity calledby;
	private final String img_name;
	private final Rectangle m_bounds = new Rectangle(0, 0, 0, 0);
	private boolean marked;

	public static EntityOptions singleton = new EntityOptions("menubutton.png");

	private EntityOptions(String imgname) {
		img_name = imgname;
	}

	public BufferedImage getImg() {
		return Game.getImageManager().getImage(img_name);
	}

	public synchronized void setOptions(ArrayList<Button> options, AbstractEntity entity) {
		if (options != null) {
			for (int i = 0; i < options.size(); i++) {
				options.get(i).setSize(30, 30);
			}
		} else if (entity != calledby ){
			return; // only delete options when entity is the owner
		}

		calledby = entity;
		this.options = options;
	}

	@Override
	public synchronized void onDraw(Graphics2D g2d) {
		if (options == null || options.size() == 0) {
			return;
		}
		
		g2d.setColor(new Color(0, 0, 0, 0.5f));

		BufferedImage quadr = new BufferedImage(30, 30,
				BufferedImage.TYPE_3BYTE_BGR);

		Button last = options.get(options.size() - 1);
		Button first = options.get(0);
		m_bounds.width = last.getX() - first.getX() + last.getWidth() + 40;
		m_bounds.height = last.getY() - first.getY() + last.getHeight() + 40;
		m_bounds.x = getX() + getWidth() - m_bounds.width;
		m_bounds.y = getY() + getHeight() - m_bounds.height;
		// g2d.fillRoundRect(x, y, width, height, last.getWidth()/2+10,
		// last.getHeight()/2+10);
		g2d.drawImage(getImg(), m_bounds.x, m_bounds.y, m_bounds.width,
				m_bounds.height, null);
		for (int i = 0; i < options.size(); i++) {
			Button next = options.get(i);
			next.setX(m_bounds.x + i * 50 + 20);
			next.setY(m_bounds.y + 20);

			g2d.drawImage(quadr, next.getX() - next.getWidth() / 2 + 3,
					next.getY() - next.getHeight() / 2 + 3,
					next.getWidth() * 7 / 4 + 3, next.getHeight() * 7 / 4, null);
			options.get(i).onDraw(g2d);
		}

		for (int i = 0; i < options.size() - 1; i++) {
			Button next = options.get(i);
			g2d.setColor(Color.red);
			int x = next.getX() - next.getWidth() / 2 + 3 + next.getHeight()
					* 7 / 4;
			g2d.drawLine(x, next.getY() - next.getHeight() / 2 + 3, x,
					next.getY() - next.getHeight() / 2 + next.getHeight() * 2
							- 6);
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		// TODO Auto-generated method stub
		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();

		if (mpl.isLeftClicked()) {
			marked = m_bounds.contains(mpl.getX(), mpl.getY()) ? true : false;
		}

		if (options == null) {
			return;
		}
		for (int i = 0; i < options.size(); i++) {
			options.get(i).onUpdate(screen);
		}

		KeyboardListener kbl = screen.getScreenFactory().getGame()
				.getKeyboardListener();

		for (int i = 0; i < options.size(); i++) {
			if (kbl.isOnPress("btn_" + (i + 1))) {
				options.get(i).callActions();
			}
		}
	}

	public boolean isMarked() {
		return marked;
	}
}
