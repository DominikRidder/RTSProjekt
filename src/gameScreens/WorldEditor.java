package gameScreens;

import entity.AbstractEntity;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.Field;
import gui.GridLayout;
import gui.GuiElement;
import gui.Label;
import gui.Panel;
import gui.ScrollPane;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
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
	ArrayList<GuiElement> functions = new ArrayList<GuiElement>(1);
	int selected = -1;
	int cursorSize = 1;
	String tileID = "";

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
		pFunctions.setLayout(new GridLayout(3, 5, pFunctions));

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
		Button save = new Button("Karte speichern");
		save.addActionListener(this);
		Button load = new Button("Karte laden");
		load.addActionListener(this);

		// ******** Zoom Buttons ********************
		Button zoomin = new Button("+");
		zoomin.addActionListener(this);
		
		Button zoomout = new Button("-");
		zoomout.addActionListener(this);
		// ********************************************
		
		Label lblCursorSize = new Label("Aktuelle Cursor Groesse = " + cursorSize);

		functions.add(cursorSizePlus);
		functions.add(cursorSizeMinus);
		functions.add(lblCursorSize);
		functions.add(save);
		functions.add(load);
		functions.add(zoomin);
		functions.add(zoomout);

		for (int i = 0; i < functions.size(); i++) {
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
									f.setTileID(tileID);
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
								f.setTileID(tileID);
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
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == null) {
			return;
		}
		switch (e.getActionCommand()) { // name of the button
		case "grass":
			selectTile(0);
			tileID = "g";
			break;
		case "weg":
			selectTile(1);
			tileID = "s";
			break;
		case "Cursorgroesse erhöhen":
			cursorSize++;
			((Label) functions.get(2)).setText("Aktuelle Cursor Groesse = " + cursorSize);
			break;
		case "Cursorgroesse verkleinern":
			if (cursorSize != 1)
				cursorSize--;
			((Label) functions.get(2)).setText("Aktuelle Cursor Groesse = " + cursorSize);
			break;
		case "Karte speichern":
			File f = new File("data/maptest.mpd");
			try {
				FileWriter writer = new FileWriter(f, true);
				writer.write(mapToString());
				writer.flush();
				writer.close();
			} catch (IOException exc) {
				exc.printStackTrace();
			}
			System.out.println(mapToString());
			break;
		case "+":
			System.out.println("test");
			scale *= 2;
			pWorld.setSize(scale * 16 * width, scale * 16 * height);
			break;
		case "-":
			if (scale != 1) {
				scale /= 2;
				pWorld.setSize(scale * 16 * width, scale * 16 * height);
			}
			break;
		default:
			System.out.println("Unknown ActionEvent: " + e.getActionCommand());
			break;
		}
	}

	public void selectTile(int selected) {
		this.selected = selected;
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).setBorder(false);
		}

		tiles.get(selected).setBorder(true);
	}

	public String mapToString() {
		StringBuffer map = new StringBuffer(pWorld.getLayout().getRowSize() + ";" + pWorld.getLayout().getColumnSize() + ";");
		for (int i = 0; i < pWorld.getLayout().getRowSize(); i++) {
			for (int j = 0; j < pWorld.getLayout().getColumnSize(); j++) {
				if (((Field) pWorld.getLayout().getElement(i, j)).getTileID() != null) {
					map.append(((Field) pWorld.getLayout().getElement(i, j)).getTileID());
				} else {
					map.append("v");
				}
			}
		}

		return map.toString();
	}

}