package Utilitys;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
	private LinkedList<Node> nodes;
	private int[][] matrix;

	public Graph() {
		nodes = new LinkedList<Node>();
	}

	public int getSize() {
		return nodes.size();
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
			if (nodes.get(i).getX() == (x / 16) * 16 && nodes.get(i).getY() == (y / 16) * 16)
				return i;
		}
		System.out.println("X " + (x / 16) + "  x " + (x / 16) * 16);
		return -1;
	}

	public Node getNode(int x, int y) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getX() == x && nodes.get(i).getY() == y)
				return nodes.get(i);
		}
		return null;
	}

	public void createMatrix() {
		matrix = new int[nodes.size()][nodes.size()];
		System.out.println("Start Bordering ");
		int counter = 0;
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				int absx = nodes.get(i).getX() - nodes.get(j).getX();
				int absy = nodes.get(i).getY() - nodes.get(j).getY();
				if (absx <= 16 && absy <= 16 && nodes.get(j).isBegehbar() && nodes.get(i).isBegehbar())
					addBorder(i, j);
				counter++;
				System.out.println(counter);
			}
		}
		System.out.println("end Bordering ");
	}

	private int getNextNode() {
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

	public ArrayList<Node> astar(int startnode, int endnode) {
		int id, newDis;
		int maxValue = Integer.MAX_VALUE - 2000; //Diagonale statt 200 abziehen von der Welt
		for (int i = 0; i < nodes.size(); i++) {
			int absx = nodes.get(i).getX() - nodes.get(endnode).getX();
			int absy = nodes.get(i).getY() - nodes.get(endnode).getY();
			nodes.get(i).setHeuristic((int) Math.sqrt(absx * absx + absy * absy));
			nodes.get(i).setVisited(false);
			nodes.get(i).setRange(maxValue);
		}

		nodes.get(startnode).setRange(0);
		for (int i = 0; i < nodes.size(); i++) {
			id = getNextNode();
			if (id == -1)
				break;
			nodes.get(id).setVisited(true);

			outer: for (int ab = 0; ab < nodes.size(); ab++) {
				if (!nodes.get(ab).isVisited() && matrix[id][ab] > 0) {
					newDis = nodes.get(id).getRange() + matrix[id][ab];
					if (newDis < nodes.get(id).getRange()) {
						nodes.get(ab).setRange(newDis);
						nodes.get(ab).setFrom(id);

						if (ab == endnode)
							break outer;
					}
				}
			}
		}

		ArrayList<Node> way = new ArrayList<Node>();
		id = endnode;
		way.add(nodes.get(id));
		while (id != startnode) {
			id = nodes.get(id).getFrom();
			way.add(nodes.get(id));
		}
		return way;

	}

	public Node getNodeWithID(int i) {
		return nodes.get(i);
	}
}
