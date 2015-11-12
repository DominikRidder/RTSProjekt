package Utilitys;

public class Point {

	private int x, y;

	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public Point(Double x, Double y) {
		this.setX(x.intValue());
		this.setY(y.intValue());
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

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Point other = (Point) obj;
		if ((this.x == other.x) && (other.y == this.y)) {
			return true;
		}
		return false;
	}
}