package Utilitys;

public class Node {
	private int x;
	private int y;
	private int heuristic;
	private int range;
	private int cost;
	private int from;
	private boolean visited;
	private boolean begehbar = true;

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

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean isBegehbar() {
		return begehbar;
	}

	public void setBegehbar(boolean begehbar) {
		this.begehbar = begehbar;
	}

	public int getValue() {
		return range + heuristic;
	}

}
