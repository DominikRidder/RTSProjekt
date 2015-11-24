package gameScreens;

import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.Field;
import gui.GridLayout;
import gui.GuiElement;
import gui.Label;
import gui.LayerPanel;
import gui.Panel;
import gui.ScrollPane;
import gui.TextField;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class WorldEditor extends Screen implements ActionListener {
	int scale = 16;
	int width = 50;
	int height = 50;
	LayerPanel pWorld;
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
		pWorld = new LayerPanel(150, 0, width * 16, height * 16);
		world = new ScrollPane(pWorld, getW() - 165, getH() - 200);
		pWorld.addLayout(new GridLayout(width, height, pWorld));
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

		// Tile initialization start

		Button grass = null;
		Button weg = null;

		grass = new Button(0, 0, Game.getImageLoader().getImage("grass.png"));
		grass.setText("grass.png");
		grass.setWidth(32);
		grass.setHeight(32);
		weg = new Button(0, 0, Game.getImageLoader().getImage("weg(gerade).png"));
		weg.setText("weg(gerade).png");
		weg.setWidth(32);
		weg.setHeight(32);

		tiles.add(grass);
		tiles.add(weg);

		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).addActionListener(this);
			pTiles.addElement(tiles.get(i));
		}

		// Tile initialization end

		// Funktions start

		Button cursorSizePlus = new Button("Cursorgroesse erhoehen");
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
		functions.add(new TextField(0,0,0,0));

		for (int i = 0; i < functions.size(); i++) {
			pFunctions.addElement(functions.get(i));
		}

		// Funktion end

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
									f.setImg(tiles.get(selected).getText());
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
								f.setImg(tiles.get(selected).getText());
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
		case "grass.png":
			selectTile(0);
			tileID = "g";
			break;
		case "weg(gerade).png":
			selectTile(1);
			tileID = "s";
			break;
		case "Cursorgroesse erhoehen":
			cursorSize++;
			((Label) functions.get(2)).setText("Aktuelle Cursor Groesse = " + cursorSize);
			break;
		case "Cursorgroesse verkleinern":
			if (cursorSize != 1)
				cursorSize--;
			((Label) functions.get(2)).setText("Aktuelle Cursor Groesse = " + cursorSize);
			break;
		case "Karte speichern":
			mapToString();
			break;
		case "Karte laden":
			loadMap();
			break;
		case "+":
			scale *= 2;
			pWorld.setSize(scale * width, scale * height);
			break;
		case "-":
			if (scale != 1) {
				scale /= 2;
				pWorld.setSize(scale * width, scale * height);
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

	public void mapToString() {
		StringBuffer map = new StringBuffer();
		for (int i = 0; i < pWorld.getLayout().getRowSize(); i++) {
			for (int j = 0; j < pWorld.getLayout().getColumnSize(); j++) {
				if (((Field) pWorld.getLayout().getElement(j, i)).getTileID() != null) {
					map.append(((Field) pWorld.getLayout().getElement(j, i)).getTileID());
				} else {
					map.append("v");
				}
			}
		}

		String actTile = map.substring(0, 1);
		int counter = 1;
		StringBuffer tmp = new StringBuffer();
		for (int i = 1; i < map.length(); i++) {
			if (map.substring(i, i + 1).equals(actTile)) {
				counter++;
			} else {
				tmp.append(";" + counter + actTile);
				actTile = map.substring(i, i + 1);
				counter = 1;
			}
		}
		map = new StringBuffer(pWorld.getLayout().getRowSize() + ";" + pWorld.getLayout().getColumnSize()).append(tmp);

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("data/"));
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".mpd");
				fw.write(map.toString());
				fw.flush();
				fw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void loadMap() {
		File fMap;
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("./data/"));
		int retrival = chooser.showOpenDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			fMap = new File(chooser.getSelectedFile().getAbsolutePath());
			try (BufferedReader bf = new BufferedReader(new FileReader(fMap))) {
				String line = bf.readLine();
				String[] data = line.split(";");
				height = Integer.parseInt(data[0]);
				width = Integer.parseInt(data[1]);
				pWorld.removeAllLayouts();
				pWorld.setWidth(width * 16);
				pWorld.setHeight(height * 16);
				pWorld.addLayout(new GridLayout(width, height, pWorld));
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						pWorld.addElement(new Field(i * 16, j * 16));
					}
				}
				int containerI = -1;
				int containerJ = 0;
				for (int i = 2; i < data.length; i++) {
					String pair = data[i];
					String imgname = null;
					switch (pair.charAt(pair.length() - 1)) {
					case 'g':
						imgname = "grass.png";
						break;
					case 's':
						imgname = "weg(gerade).png";
						break;
					default:
						imgname = "";
						break;
					}
					for (int j = Integer.parseInt(pair.split("[a-z]")[0]); j > 0; j--) {
						if (containerI < height - 1) {
							containerI++;
						} else {
							containerJ++;
							containerI = 0;
						}
						if (imgname != "") {
							((Field) pWorld.getLayout().getElement(containerI, containerJ)).setImg(imgname);
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}