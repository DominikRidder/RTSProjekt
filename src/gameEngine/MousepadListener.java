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

	public void mouseClicked(MouseEvent event) {

	}

	public void mouseEntered(MouseEvent event) {

	}

	public void mouseExited(MouseEvent event) {

	}

	public void mousePressed(MouseEvent event) {
		dragging = true;
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

	public void setRightClicked(boolean rightClicked) {
		this.rightClicked = rightClicked;
	}

	public int getX() {
		return mouseX;
	}

	public int getY() {
		return mouseY - 20;
	}

	public void mouseDragged(MouseEvent e) {
		if (dragging) {
			setMarkX(e.getX());
			setMarkY(e.getY());
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public int getMarkX() {
		return markX;
	}

	public void setMarkX(int markX) {
		this.markX = markX;
	}

	public int getMarkY() {
		return markY - 20;
	}

	public void setMarkY(int markY) {
		this.markY = markY;
	}

	public boolean isDragging() {
		return dragging;
	}
}
