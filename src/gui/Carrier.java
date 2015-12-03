package gui;

import gameEngine.Game;
import gameEngine.Screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Carrier extends GuiElement {
	private int x1, y1, x2, y2;
	private boolean wasDrawn = false;
	private BufferedImage img;
	private GridLayout gl;
	private boolean initDone = false;

	public Carrier(int x1, int y1, BufferedImage image, GridLayout gl) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + image.getWidth() / 16;
		this.y2 = y1 + image.getHeight() / 16;
		img = image;
		this.gl = gl;
	}

	public void init() {
		String imgname = Game.getImageManager().getNameFromImage(img).split("\\\\")[Game.getImageManager().getNameFromImage(img).split("\\\\").length - 1];
		for (int i = x1; i < x2; i++) {
			for (int j = y1; j < y2; j++) {
				if (imgname != null) {
					Game.getImageManager().addImage(imgname + i + j + ";16;16", Game.getImageManager().getImage(imgname).getSubimage(0 + (i - x1) * 16, 0 + (j - y1) * 16, 16, 16));
					((Field) gl.getElement(i, j)).setImg(imgname + i + j);
				} else {
					System.out.println("Imagename null ");
				}
			}
		}
		initDone = true;
	}

	public void delete() {
		for (int i = x1; i < x2; i++) {
			for (int j = y1; j < y2; j++) {
				Field f = new Field(i, j);
				f.setWidth(16);
				f.setHeight(16);
				gl.setElement(f, i, j);
			}
		}
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		if (!wasDrawn && initDone) {
			for (int i = x1; i < x2; i++) {
				for (int j = y1; j < y2; j++) {
					if (((BigField) gl.getElement(i, j)).getImg() == null) {
						System.out.println("Warum sind manche null in init() werden doch alle gesetzt");
					}
					g2d.drawImage(((BigField) gl.getElement(i, j)).getImg(), getX(), getY(), null);
				}
			}
			wasDrawn = true;
		}
	}

	@Override
	public void onUpdate(Screen screen) {
		// TODO Auto-generated method stub

	}

}
