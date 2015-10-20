package Utilitys;

import java.util.LinkedList;

public class Graph {
	private final LinkedList<Node> nodes;
	private LinkedList<Node> openList;
	private LinkedList<Node> closedList;
	private Node finish;

	public Graph() {
		nodes = new LinkedList<Node>();
		openList = new LinkedList<Node>();
		// openList.add(new Node(this.playerX, this.playerY, 1, this.heuristik(this.playerX, this.playerY), null));
		closedList = new LinkedList<Node>();
	}

	private int getFirstBestListEntry(LinkedList<Node> list) {
		int best = list.get(0).getTotalCost();

		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getTotalCost() < best) {
				best = list.get(i).getTotalCost();
			}
		}
		// get first element with best costs
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTotalCost() == best) {
				return i;
			}
		}
		return 0;
	}

	public Node search(int startX, int startY, int endX, int endY) {
		openList.add(new Node(startX, startY, 1, this.heuristik(startX, startY, endX, endY), null));
		return astar(startX, startY, endX, endY);
	}

	public Node astar(int startX, int startY, int endX, int endY) {
		Node bestNode;
		bestNode = openList.get(this.getFirstBestListEntry(openList));
		closedList.add(openList.remove(this.getFirstBestListEntry(openList)));

		if (bestNode.getX() == endX && bestNode.getY() == endY) {
			this.finish = bestNode;
			return bestNode;
		}

		// oberhalb
		this.openListAddHelper(bestNode.getX(), bestNode.getY() - 1, openList, closedList, bestNode, endX, endY);

		// rechts
		this.openListAddHelper(bestNode.getX() + 1, bestNode.getY(), openList, closedList, bestNode, endX, endY);

		// unterhalb
		this.openListAddHelper(bestNode.getX(), bestNode.getY() + 1, openList, closedList, bestNode, endX, endY);

		// links
		this.openListAddHelper(bestNode.getX() - 1, bestNode.getY(), openList, closedList, bestNode, endX, endY);

		if (openList.size() != 0) {
			return astar(bestNode.getX(), bestNode.getY(), endX, endY);
		} else {
			return null;
		}

	}

	public Node getNode(int x, int y) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getX() == x && nodes.get(i).getY() == y) {
				return nodes.get(i);
			}
		}
		return null;

	}

	private void openListAddHelper(int x, int y, LinkedList<Node> openList, LinkedList<Node> closedList, Node vorher, int endX, int endY) {
		if (x < 0 || y < 0 || x >= 25 || y >= 25 || !getNode(x, y).isBegehbar()) {
			// nichts
		} else {
			if (getNode(x, y) != null) {
				if (this.isPointInList(x, y, closedList) == false && this.isPointInList(x, y, openList) == false) {
					openList.add(new Node(x, y, 1, this.heuristik(x, y, endX, endY), vorher));
				}
			}
		}
	}

	private boolean isPointInList(int x, int y, LinkedList<Node> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getX() == x && list.get(i).getY() == y) {
				return true;
			}
		}

		return false;
	}

	private int heuristik(int x, int y, int endX, int endY) {
		int dx = x - endX;
		if (dx < 0) {
			dx = -dx;
		}

		int dy = y - endY;
		if (dy < 0) {
			dy = -dy;
		}

		return dx + dy;
	}

	public int getSize() {
		return nodes.size();
	}

	public Node getNodeWithID(int i) {
		return nodes.get(i);
	}

	public void reset() {
		openList = new LinkedList<Node>();
		// openList.add(new Node(this.playerX, this.playerY, 1, this.heuristik(this.playerX, this.playerY), null));
		closedList = new LinkedList<Node>();
		this.finish = null;
	}

	public void addNode(Node n) {
		nodes.add(n);
	}
}
