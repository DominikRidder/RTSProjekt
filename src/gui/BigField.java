package gui;

import java.awt.Graphics2D;

public class BigField extends Field {
	private Carrier carry;

	public BigField(int x, int y, Carrier c) {
		super(x, y);
		carry = c;
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		if (carry != null) {
			carry.onDraw(g2d, getX(), getY());
		}
		//		if (getImg() != null) {
		//			g2d.drawImage(getImg(), getX(), getY(), null);
		//		}
	}

	public void delete() {
		carry.delete();
	}

	public void prepare() {
		carry.prepareToSave();
	}

}
