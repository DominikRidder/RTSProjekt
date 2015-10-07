package gameEngine;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MousepadListener implements MouseListener, MouseMotionListener {

	private int mouseX, mouseY;
	private int markX, markY;

	private boolean leftClicked = false;
	private boolean rightClicked = false;

	@Override
	public void mouseClicked(MouseEvent event) {
		mouseX = event.getX();
		mouseY = event.getY();
		if (event.getButton() == MouseEvent.BUTTON1) {
			leftClicked = true;
			rightClicked = false;
		} else if (event.getButton() == MouseEvent.BUTTON3) {
			rightClicked = true;
			leftClicked = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {

	}

	@Override
	public void mouseExited(MouseEvent event) {

	}

	@Override
	public void mousePressed(MouseEvent event) {
		mouseClicked(event);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		leftClicked = false;
		rightClicked = false;
	}

	public boolean isLeftClicked() {
		return leftClicked;
	}

	public boolean isRightClicked() {
		return rightClicked;
	}

	public int getX() {
		return mouseX;
	}

	public int getY() {
		return mouseY;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		markX = e.getX();
		markY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
