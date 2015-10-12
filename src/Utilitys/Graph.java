package Utilitys;

import java.util.LinkedList;

public class Graph {
	private LinkedList<Node> nodes;
	private int[][] matrix;

	public Graph() {
		nodes = new LinkedList<Node>();
	}

	public void addBorder(int from, int to) {
		matrix[from][to] = 1;
		matrix[to][from] = 1;
	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	public int getNodeID(int x, int y) {
		for (int i = 0; i < nodes.size(); i++) {
			if (x == nodes.get(i).getX() && y == nodes.get(i).getY())
				return i;
		}
		return -1;
	}

	public Node getNode(int x, int y) {
		for (int i = 0; i < nodes.size(); i++) {
			if (x == nodes.get(i).getX() && y == nodes.get(i).getY())
				return nodes.get(i);
		}
		return null;
	}

	public void createMartix() {
		matrix = new int[nodes.size()][nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				int absx = nodes.get(i).getX() - nodes.get(j).getX();
				int absy = nodes.get(i).getY() - nodes.get(j).getY();
				int abs = absx * absx + absy * absy;
				if (abs == 1)
					addBorder(i, j);
			}
		}

	}

	public int getNextNode() {
		int id = -1;
		int value = Integer.MAX_VALUE;
		for (int i = 0; i < nodes.size(); i++) {
			if (!nodes.get(i).isVisited() && nodes.get(i).getValue() < value) {
				id = i;
				value = nodes.get(i).getValue();
			}
		}
		return id;
	}

	public LinkedList<Node> astar(int startnode, int endnode) {
		int id, newDis;
		int maxValue = (int) (Integer.MAX_VALUE - Math.sqrt(800 * 800 + 600 * 600)); // Breite und Hoehe
		for (int i = 0; i < nodes.size(); i++) {
			int absx = nodes.get(i).getX() - nodes.get(endnode).getX();
			int absy = nodes.get(i).getY() - nodes.get(endnode).getY();
			nodes.get(i).setHeuristic((int) Math.sqrt(absx * absx + absy * absy));
			nodes.get(i).setVisited(false);
			nodes.get(i).setRange(Integer.MAX_VALUE);
		}
		nodes.get(startnode).setRange(0);
		outer: for (int i = 0; i < nodes.size(); i++) {
			id = getNextNode();
			if (id == -1)
				break;
			nodes.get(i).setVisited(true);
			for (int ab = 0; ab < nodes.size(); ab++) {
				if (nodes.get(ab).isVisited() && matrix[ab][i] > 0) {
					newDis = nodes.get(i).getRange() + matrix[ab][id];
					if (newDis < nodes.get(ab).getRange()) {
						nodes.get(ab).setRange(newDis);
						nodes.get(ab).setFrom(id);

						if (ab == endnode)
							break outer;
					}
				}
			}
		}

		LinkedList<Node> way = new LinkedList<Node>();
		id = endnode;
		way.add(nodes.get(id));
		while (id != startnode) {
			id = nodes.get(id).getFrom();
			way.add(nodes.get(id));
		}
		return way;
	}
}
