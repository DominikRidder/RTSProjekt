package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.AbstractEntity;
import entity.Option;
import entity.ResourceInfo;
import gameEngine.Game;
import gameEngine.KeyboardListener;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameScreens.GameScreen;

public class EntityOptions extends GuiElement {
	private ArrayList<Option> options;
	private AbstractEntity calledby;
	private final String img_name;
	private final Rectangle m_bounds = new Rectangle(0, 0, 0, 0);
	private boolean marked;
	private int drawToolTip = -1;

	public static EntityOptions singleton = new EntityOptions("menubutton.png");

	private EntityOptions(String imgname) {
		img_name = imgname;
	}

	public BufferedImage getImg() {
		return Game.getImageManager().getImage(img_name);
	}

	public synchronized void setOptions(ArrayList<Option> options,
			AbstractEntity entity) {
		if (calledby != null && !calledby.isMarked() && !marked) {
			calledby = null;
			options = null;
		} else if (calledby != null) {
			return;
		}
		if (options != null) {
			for (int i = 0; i < options.size(); i++) {
				options.get(i).setSize(30, 30);
			}
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

		if (drawToolTip != -1) {
			Option option = options.get(drawToolTip);
			Monolog tooltip = option.getToolTip();
			if (tooltip == null) {
				return;
			}
			tooltip.setX((int) m_bounds.getX() + (int)m_bounds.getWidth() - tooltip.getWidth());
			tooltip.setY((int) m_bounds.getY() - tooltip.getHeight());
			tooltip.onDraw(g2d);
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		// TODO Auto-generated method stub
		GameScreen sc = (GameScreen) screen;
		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();
		
		if (mpl.isLeftClicked()) {
			marked = m_bounds.contains(mpl.getX(), mpl.getY()) ? true : false;
		}
		
		if (calledby != null && !calledby.isMarked() && !marked) {
			calledby = null;
			options = null;
		}

		if (options == null) {
			return;
		}
		if (options != null) {

			for (int i = 0; i < options.size(); i++) {
				options.get(i).onUpdate(screen);
			}

			KeyboardListener kbl = screen.getScreenFactory().getGame()
					.getKeyboardListener();

			int drawtip = -1;
			for (int i = 0; i < options.size(); i++) {
				if (kbl.isOnPress("btn_" + (i + 1))) {
					options.get(i).callActions();
				}

				if (options.get(i).getBounds().contains(mpl.getCurrentX()+sc.getViewX(), mpl.getCurrentY()+sc.getViewY())) {
					drawtip = i;
				}
			}
			drawToolTip = drawtip;
		}
	}

	public boolean isMarked() {
		return marked;
	}
}
