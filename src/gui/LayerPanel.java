package gui;

import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class LayerPanel extends APanel implements CoordinateMapping {

	private ArrayList<ILayout> layouts;

	private int actuallayer;

	public LayerPanel(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
		layouts = new ArrayList<ILayout>();
	}

	public LayerPanel(int x, int y, int width, int height, boolean border) {
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
		setBorder(border);
		layouts = new ArrayList<ILayout>();
	}

	public void addLayout(ILayout layout) {
		this.layouts.add(layout);
	}

	public void removeAllLayouts() {
		layouts = new ArrayList<ILayout>();
	}

	public void addElement(GuiElement element) {
		layouts.get(actuallayer).addElement(element);
	}

	public void addAbsoluteElement(GuiElement element) {
		((GridLayout) layouts.get(actuallayer)).addAbsoluteElement(element);
	}

	public void onDraw(Graphics2D g2d) {
		for (ILayout layout : layouts) {
			layout.onDraw(g2d);
		}
		drawBorder(g2d);
	}

	@Override
	public void onUpdate(Screen screen) {
		for (ILayout layout : layouts) {
			layout.onUpdate(screen);
		}
		this.setUpdate(false); // Udpating the layouts should be finished here
	}

	public Point getCoordinate(int x, int y) {
		ILayout layout = layouts.get(actuallayer);
		if (getBounds().contains(x, y)) {
			if (layout instanceof CoordinateMapping) {
				return ((CoordinateMapping) layout).getCoordinate(x, y);
			} else {
				throw new RuntimeException("Layout don't support the CoordinateMapping!");
			}
		}
		return null;
	}

	public GridLayout getLayout() {
		return (GridLayout) layouts.get(actuallayer);
	}

	public void setActualLayer(int i) {
		actuallayer = i;
	}

	public int getActualLayer(int i) {
		return actuallayer;
	}

	public int numberOfLayouts() {
		return layouts.size();
	}
}