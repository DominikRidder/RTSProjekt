package gameEngine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MousepadListener implements MouseListener, MouseMotionListener {

	private int mouseX, mouseY;
	private int markX = -1, markY = -1;

	private boolean leftClicked = false;
	private boolean rightClicked = false;
	private boolean dragging = false;

	@Override
	public void mouseClicked(MouseEvent event) {
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
		dragging = true;
		mouseX = event.getX();
		mouseY = event.getY();
		mouseClicked(event);

	}

	@Override
	public void mouseReleased(MouseEvent event) {
		leftClicked = false;
		rightClicked = false;
		dragging = false;
		markX = -1;
		markY = -1;
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
		return mouseY - 20;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dragging) {
			setMarkX(e.getX());
			setMarkY(e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public int getMarkX() {
		return markX;
	}

	public void setMarkX(int markX) {
		this.markX = markX;
	}

	public int getMarkY() {
		return markY;
	}

	public void setMarkY(int markY) {
		this.markY = markY;
	}

	public boolean isDragging() {
		return dragging;
	}
}
