package Utilitys;

public class Node {
	private int x;
	private int y;
	private int heuristic;
	private int cost;

	private Node from;
	private boolean begehbar = true;

	public Node(int x, int y, int heuristic, int cost, Node from) {
		this.x = x;
		this.y = y;
		this.heuristic = heuristic;
		this.cost = cost;
		this.from = from;
	}

	public int getTotalCost() {
		return this.getLastCosts() + this.cost + this.heuristic;
	}

	public int getLastCosts() {
		if (this.from == null)
			return 0;
		return this.from.getLastCosts() + this.cost;
	}

	public void setFrom(Node neuer) {
		this.from = neuer;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

	public boolean isBegehbar() {
		return begehbar;
	}

	public void setBegehbar(boolean begehbar) {
		this.begehbar = begehbar;
	}

	public Node getFrom() {
		return from;
	}

}
