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

public class NodeTrackingScreen extends Screen {

	private Graph g = new Graph();
	private ArrayList<Node> zeichnen = new ArrayList<Node>();

	public NodeTrackingScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		System.out.println("Start Creating");
		for (int i = 0; i < getScreenFactory().getGame().getWindow().getWidth(); i += 16) {
			for (int j = 0; j < getScreenFactory().getGame().getWindow().getHeight(); j += 16) {
				Node n = new Node();
				n.setX(i);
				n.setY(j);
				n.setBegehbar(true);
				//if (g.getNode(n.getX(), n.getY()) == null) {
				Random r = new Random();
				//if (n.getX() == 0 && n.getY() == 0) {
				//} else if (n.getX() == 4 && n.getY() == 4) {

				//} else {
				if (i % (r.nextInt(9) + 1) == 0) {
					n.setBegehbar(false);
				}

				//}
				//}
				g.addNode(n);
			}
		}

		g.createMatrix();
		System.out.println("End Creating ");

	}

	@Override
	public void onUpdate() {

	}

	@Override
	public void onDraw(Graphics2D g2d) {
		for (int i = 0; i < g.getSize(); i++) {
			//if (g.getNodeWithID(i).isBegehbar()) {
			g2d.drawRect((g.getNodeWithID(i).getX()) * 16, (g.getNodeWithID(i).getY()) * 16, 16, 16);
			/*} else {
				g2d.drawRect((g.getNodeWithID(i).getX()) * 16, (g.getNodeWithID(i).getY()) * 16, 16, 16);
				g2d.setColor(Color.BLUE);
				g2d.drawLine((g.getNodeWithID(i).getX() - 1) * 16, (g.getNodeWithID(i).getY() - 1) * 16, (g.getNodeWithID(i).getX()) * 16, (g.getNodeWithID(i).getY()) * 16);
				g2d.setColor(Color.RED);
				g2d.drawLine((g.getNodeWithID(i).getX()) * 16, (g.getNodeWithID(i).getY() - 1) * 16, (g.getNodeWithID(i).getX() - 1) * 16, (g.getNodeWithID(i).getY()) * 16);
				g2d.setColor(Color.BLACK);
			}*/
		}
		g2d.setColor(Color.BLACK);

		/*for (int i = zeichnen.size() - 2; i > 0; i--) {
			int j = i - 1;
			g2d.drawLine((zeichnen.get(i).getX() - 1) * 25 + 12, (zeichnen.get(i).getY() - 1) * 25 + 12, (zeichnen.get(j).getX() - 1) * 25 + 12, (zeichnen.get(j).getY() - 1) * 25 + 12);
		}*/

	}

	@Override
	public ArrayList<AbstractEntity> getEntitys() {
		return null;
	}

}
