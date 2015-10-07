package entity;
import gameEngine.MousepadListener;
import gameEngine.Screen;

public abstract class Unit extends AbstractEntity {

	private boolean marked;

	private int nextX = getX(), nextY = getY();

	public Unit(int x, int y) {
		super(x, y);
	}

	// public Rectangle getBounds() {
	// return new Rectangle(getX(), getY(), 25, 32);
	// }

	public void update(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();
		if (mpl.isLeftClicked()) {
			if ((mpl.getX() >= getX() && mpl.getX() <= getX() + 50) && (mpl.getY() >= getY() && mpl.getY() <= getY() + 50)) { // 50 dynamisch machen
				marked = true;
			} else {
				marked = false;
			}
		} else if (mpl.isRightClicked()) {
			if (marked == true) {
				nextX = mpl.getX() - 10;
				nextY = mpl.getY() - 40;
			}
		}

		if (getX() != nextX) {
			if (getX() < nextX) {
				setX(getX() + 1);
			} else {
				setX(getX() - 1);
			}
		}
		if (getY() != nextY) {
			if (getY() < nextY) {
				setY(getY() + 1);
			} else {
				setY(getY() - 1);
			}
		}
	}

	public void checkCollision() {

	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
}
