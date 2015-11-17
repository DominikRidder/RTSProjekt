package gameScreens;

import entity.AbstractEntity;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.Field;
import gui.GridLayout;
import gui.Panel;
import gui.ScrollPane;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class WorldEditor extends Screen implements ActionListener {
	int scale = 1;
	int width = 50;
	int height = 50;
	Panel pWorld;
	ScrollPane world;
	Point lastposition;
	ArrayList<Button> tiles = new ArrayList<Button>(1);
	int selected = -1;

	public WorldEditor(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		setW(this.getScreenFactory().getGame().getWindow().getWidth());
		setH(this.getScreenFactory().getGame().getWindow().getHeight());
		pWorld = new Panel(150, 0, width * 16, height * 16);
		world = new ScrollPane(pWorld, getW() - 165, getH() - 200);
		pWorld.setLayout(new GridLayout(width, height, pWorld));
		addGuiElement(world);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pWorld.addElement(new Field(i * 16, j * 16));
			}
		}
		Button grass = null;
		try {
			grass = new Button(0, 0, ImageIO.read(new File("data/grass.png")));
			grass.setText("grass");
			grass.setWidth(32);
			grass.setHeight(32);
		} catch (IOException e) {
			e.printStackTrace();
		}
		tiles.add(grass);

		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).addActionListener(this);
			addGuiElement(tiles.get(i));
		}

	}

	public void onUpdate() {
		super.onUpdate();
		MousepadListener mpl = getScreenFactory().getGame().getMousepadListener();
		Field f;
		if (mpl.isLeftClicked() && lastposition == null) { // pro click kommt nur eine aktion hierdurch
			if (mpl.isDragging()) {
				lastposition = world.getCoordinate(mpl.getMarkX(), mpl.getMarkY());
				if (lastposition != null) { // click was im ScrollPane
					f = (Field) pWorld.getLayout().getElement((int) lastposition.getX(), (int) lastposition.getY());
					if (selected != -1) {
						if (f.getImg() == null || !f.getImg().equals(tiles.get(selected).getImage())) {
							f.setImg(tiles.get(selected).getImage());
						}
					}
				}
			}

		} else if (!mpl.isLeftClicked() && lastposition != null) {
			lastposition = null;
		} else {
			lastposition = world.getCoordinate(mpl.getX(), mpl.getY());
			if (lastposition != null) { // click was im ScrollPane
				f = (Field) pWorld.getLayout().getElement((int) lastposition.getX(), (int) lastposition.getY());
				if (selected != -1) {
					f.setImg(tiles.get(selected).getImage());
				}
			}
		}
		lastposition = null;
	}

	@Override
	public ArrayList<AbstractEntity> getEntitys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == null) {
			return;
		}
		switch (e.getActionCommand()) { // name of the button
		case "grass":
			System.out.println("Selected");
			selected = 0;
			break;
		default:
			System.out.println("Unknown ActionEvent: " + e.getActionCommand());
			break;
		}
	}
}