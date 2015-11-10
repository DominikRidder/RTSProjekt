package gui;

import gameEngine.MousepadListener;
import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class JScrollPane extends GuiElement {

	private GuiElement scrollableclient;

	private int rx, ry;

	private boolean dragx, dragy;

	/**
	 * Creates a Viewpoint in the given guielement. Note, that the total width
	 * and height of the ScrollPane is extended by 15 Pixels, because of the
	 * Scrollbars.
	 */
	public JScrollPane(GuiElement guielement, int x, int y, int width,
			int height) {
		scrollableclient = guielement;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	public void onDraw(Graphics2D g2d) {
		// Get the current transform
		AffineTransform saveAT = g2d.getTransform();
		// Perform transformation
		AffineTransform transform = new AffineTransform();
		transform.translate(-rx, -ry);
		g2d.transform(transform);

		g2d.setClip(getX() + rx, getY() + ry, getWidth(), getHeight());
		scrollableclient.onDraw(g2d);
		g2d.setClip(null);
		g2d.setTransform(saveAT);

		// scrollbar under the ScrollPane
		g2d.drawRoundRect(getX(), getY() + getHeight() + 5, getWidth(), 10, 5,
				5);
		g2d.drawOval(getX() + rx, getY() + getHeight() + 5, 10, 10);

		// scrollbar the right side of the ScrollPane
		g2d.drawRoundRect(getX() + getWidth() + 5, getY(), 10, getHeight(), 5,
				5);
		g2d.drawOval(getX() + getWidth() + 5, getY() + ry, 10, 10);
	}

	public void onUpdate(Screen screen) {
		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();
		
		if (!dragx) {
			Point p = new Point(getX() + rx, getY() + getHeight() + 5);
			if (p.distance(mpl.getX(), mpl.getY()) <= 10) {
				dragx = true;
			}
		} else {
			if (mpl.isDragging()) {
				rx = mpl.getMarkX() - getX();
			} else {
				if (!mpl.isLeftClicked()) {
					dragx = false;
				}
			}
		}
		
		if (!dragy) {
			Point p = new Point(getX() + getWidth() + 5, getY() + ry);
			if (p.distance(mpl.getX(), mpl.getY()) <= 10) {
				dragy = true;
			}
		} 
		if (dragy){
			if (mpl.isDragging()) {
				ry = mpl.getMarkY()- getY();
			} else {
				if (!mpl.isLeftClicked()) {
					dragy = false;
				}
			}
		}
		
		if (dragx || dragy){
			return;
		}
		
		if (mpl.isLeftClicked() && getBounds().contains(mpl.getX(), mpl.getY())) {
			mpl.setX(mpl.getX() + rx + 20);
			mpl.setY(mpl.getY() + ry + 20);

			scrollableclient.onUpdate(screen);

			mpl.setX(mpl.getX() - rx + 20);
			mpl.setY(mpl.getY() - ry + 20);
		} else {
			boolean wasclicked = mpl.isLeftClicked();
			mpl.setLeftClicked(false);
			scrollableclient.onUpdate(screen);
			mpl.setLeftClicked(wasclicked);
		}
	}

}
