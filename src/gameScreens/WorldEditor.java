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
	ArrayList<Button> functions = new ArrayList<Button>(1);
	int selected = -1;
	int cursorSize = 1;

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

		Panel pTiles = new Panel(0, 0, 145, getH() - 185, true);
		pTiles.setLayout(new GridLayout(3, 1, pTiles));

		Panel pFunctions = new Panel(0, pTiles.getY() + pTiles.getHeight(), getW(), getH() - pTiles.getHeight() - 40, true);
		pFunctions.setLayout(new GridLayout(5, 3, pFunctions));

		//Tile initialization start

		Button grass = null;
		Button weg = null;
		try {
			grass = new Button(0, 0, ImageIO.read(new File("data/grass.png")));
			grass.setText("grass");
			grass.setWidth(32);
			grass.setHeight(32);
			weg = new Button(0, 0, ImageIO.read(new File("data/weg(gerade).png")));
			weg.setText("weg");
			weg.setWidth(32);
			weg.setHeight(32);
		} catch (IOException e) {
			e.printStackTrace();
		}
		tiles.add(grass);
		tiles.add(weg);

		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).addActionListener(this);
			pTiles.addElement(tiles.get(i));
		}

		//Tile initialization end

		//Funktions start

		Button cursorSizePlus = new Button("Cursorgroesse erhöhen");
		cursorSizePlus.addActionListener(this);
		Button cursorSizeMinus = new Button("Cursorgroesse verkleinern");
		cursorSizeMinus.addActionListener(this);

		functions.add(cursorSizePlus);
		functions.add(cursorSizeMinus);

		for (int i = 0; i < functions.size(); i++) {
			functions.get(i).addActionListener(this);
			pFunctions.addElement(functions.get(i));
		}

		//Funktion end

		addGuiElement(pTiles);
		addGuiElement(pFunctions);

	}

	public void onUpdate() {
		super.onUpdate();
		MousepadListener mpl = getScreenFactory().getGame().getMousepadListener();
		Field f;
		int till = cursorSize - 1;
		if (till < 0) {
			till = 0;
		}
		if (mpl.isLeftClicked() && lastposition == null) { // pro click kommt nur eine aktion hierdurch
			if (mpl.isDragging()) {
				lastposition = world.getCoordinate(mpl.getMarkX(), mpl.getMarkY());
				if (lastposition != null) { // click was im ScrollPane
					for (int i = 0; i <= till && i + lastposition.getX() < pWorld.getLayout().getRowSize(); i++) {
						for (int j = 0; j <= till && j + lastposition.getY() < pWorld.getLayout().getColumnSize(); j++) {
							f = (Field) pWorld.getLayout().getElement((int) lastposition.getX() + i, (int) lastposition.getY() + j);
							if (selected != -1) {
								if (f.getImg() == null || !f.getImg().equals(tiles.get(selected).getImage())) {
									f.setImg(tiles.get(selected).getImage());
								}
							}
						}
					}
				}
			}

		} else if (!mpl.isLeftClicked() && lastposition != null) {
			lastposition = null;
		} else {
			lastposition = world.getCoordinate(mpl.getX(), mpl.getY());
			if (lastposition != null) { // click was im ScrollPane
				for (int i = 0; i <= till && i + lastposition.getX() < pWorld.getLayout().getRowSize(); i++) {
					for (int j = 0; j <= till && j + lastposition.getY() < pWorld.getLayout().getColumnSize(); j++) {
						f = (Field) pWorld.getLayout().getElement((int) lastposition.getX() + i, (int) lastposition.getY() + j);
						if (selected != -1) {
							if (f.getImg() == null || !f.getImg().equals(tiles.get(selected).getImage())) {
								f.setImg(tiles.get(selected).getImage());
							}
						}
					}
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
			selected = 0;
			break;
		case "weg":
			selected = 1;
			break;
		case "Cursorgroesse erhöhen":
			cursorSize++;
			break;
		case "Cursorgroesse verkleinern":
			if (cursorSize != 0)
				cursorSize--;
			break;
		default:
			System.out.println("Unknown ActionEvent: " + e.getActionCommand());
			break;
		}
	}
}