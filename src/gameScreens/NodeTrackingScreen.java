package gameScreens;

import entity.AbstractEntity;
import gameEngine.Screen;
import gameEngine.ScreenFactory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import Utilitys.Graph;
import Utilitys.Node;
import Utilitys.Point;

public class NodeTrackingScreen extends Screen {

	private Graph g = new Graph();
	private Node shortWay;
	private ArrayList<Node> zeichnen = new ArrayList<Node>();

	public NodeTrackingScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		System.out.println("Start Creating");
		int id = 0;
		for (int i = 0; i < getScreenFactory().getGame().getWindow().getWidth(); i += 25) {
			for (int j = 0; j < getScreenFactory().getGame().getWindow().getHeight(); j += 25) {
				Point map = pointToMapConst(i, j);
				Node n = new Node(map.getX(), map.getY(), 0, 1, null);
				if (g.getNode(n.getX(), n.getY()) == null) {
					Random r = new Random();
					if (n.getX() == 0 && n.getY() == 0) {
					} else if (n.getX() == 9 && n.getY() == 14) {
						id = g.getSize();
					} else {
						if (i % (r.nextInt(9) + 1) == 0) {
							n.setBegehbar(false);
						}
					}
					g.addNode(n);
				}
			}
		}
		System.out.println("End Creating");
		System.out.println("Start Searching");
		shortWay = g.search(0, 0, 11, 16);
		System.out.println("End Searching");

	}

	@Override
	public void onUpdate() {

	}

	@Override
	public void onDraw(Graphics2D g2d) {
		for (int i = 0; i < g.getSize(); i++) {
			if (g.getNodeWithID(i).isBegehbar()) {
				g2d.drawRect((g.getNodeWithID(i).getX() - 1) * 25, (g.getNodeWithID(i).getY() - 1) * 25, 25, 25);
			} else {
				g2d.drawRect((g.getNodeWithID(i).getX() - 1) * 25, (g.getNodeWithID(i).getY() - 1) * 25, 25, 25);
				g2d.setColor(Color.BLUE);
				g2d.drawLine((g.getNodeWithID(i).getX() - 1) * 25, (g.getNodeWithID(i).getY() - 1) * 25, (g.getNodeWithID(i).getX()) * 25, (g.getNodeWithID(i).getY()) * 25);
				g2d.setColor(Color.RED);
				g2d.drawLine((g.getNodeWithID(i).getX()) * 25, (g.getNodeWithID(i).getY() - 1) * 25, (g.getNodeWithID(i).getX() - 1) * 25, (g.getNodeWithID(i).getY()) * 25);
				g2d.setColor(Color.BLACK);
			}
		}
		g2d.setColor(Color.BLACK);

		if (shortWay != null) {
			while (shortWay.getFrom() != null) {
				zeichnen.add(shortWay);
				shortWay = shortWay.getFrom();
			}
		}
		for (int i = zeichnen.size() - 1; i > 0; i--) {
			int j = i - 1;
			g2d.drawLine((zeichnen.get(i).getX() - 1) * 25 + 12, (zeichnen.get(i).getY() - 1) * 25 + 12, (zeichnen.get(j).getX() - 1) * 25 + 12, (zeichnen.get(j).getY() - 1) * 25 + 12);
		}

	}

	@Override
	public ArrayList<AbstractEntity> getEntitys() {
		return null;
	}

}
