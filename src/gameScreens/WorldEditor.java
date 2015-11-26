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
	Label lblLayer = new Label("Aktivierter Layer = L1");
	Panel pTiles;

	public WorldEditor(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		setW(this.getScreenFactory().getGame().getWindow().getWidth());
		setH(this.getScreenFactory().getGame().getWindow().getHeight());
		pWorld = new LayerPanel(150, 40, width * 16, height * 16);
		world = new ScrollPane(pWorld, getW() - 165, getH() - 200);
		pWorld.addLayout(new GridLayout(width, height, pWorld));
		pWorld.addLayout(new GridLayout(width, height, pWorld));
		pWorld.addLayout(new GridLayout(width, height, pWorld));
		pWorld.addLayout(new GridLayout(width, height, pWorld));
		addGuiElement(world);
		for (int l = 0; l < pWorld.numberOfLayouts(); l++) {
			pWorld.setActualLayer(l);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					pWorld.addElement(new Field(i * 16, j * 16));
				}
			}
		}
		pWorld.setActualLayer(0);

		Panel pMenue = new Panel(0, 0, getW(), 25);
		pMenue.setLayout(new GridLayout(1, 5, pMenue));

		pTiles = new Panel(0, 40, 145, getH() - 185, true);
		pTiles.setLayout(new GridLayout(3, 1, pTiles));

		Panel pFunctions = new Panel(0, pTiles.getY() + pTiles.getHeight(), getW(), getH() - pTiles.getHeight() - 80, true);
		pFunctions.setLayout(new GridLayout(5, 3, pFunctions));

		// Menue Start

		Button btnL1 = null;
		Button btnL2 = null;
		Button btnL3 = null;
		Button btnL4 = null;

		btnL1 = new Button("L1");
		btnL2 = new Button("L2");
		btnL3 = new Button("L3");
		btnL4 = new Button("L4");

		btnL1.addActionListener(this);
		btnL2.addActionListener(this);
		btnL3.addActionListener(this);
		btnL4.addActionListener(this);

		pMenue.addElement(btnL1);
		pMenue.addElement(btnL2);
		pMenue.addElement(btnL3);
		pMenue.addElement(btnL4);
		pMenue.addElement(lblLayer);

		// Menue End

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
		//		functions.add(new TextField(0,0,0,0));

		for (int i = 0; i < functions.size(); i++) {
			pFunctions.addElement(functions.get(i));
		}

		// Funktion end

		addGuiElement(pMenue);
		addGuiElement(pTiles);
		addGuiElement(pFunctions);

		loadTiles("L1", this);

	}

	public void onUpdate() {
		super.onUpdate();
		MousepadListener mpl = getScreenFactory().getGame().getMousepadListener();
		Field f = new Field(mpl.getX(), mpl.getY());
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
							if (pWorld.getLayout().getElement((int) lastposition.getX() + i, (int) lastposition.getY() + j) instanceof Field) {
								f = (Field) pWorld.getLayout().getElement((int) lastposition.getX() + i, (int) lastposition.getY() + j);
								if (selected != -1 && selected < tiles.size()) {
									if (f.getImg() == null || !f.getImg().equals(tiles.get(selected).getImage())) {
										f.setImg(tiles.get(selected).getText());
										f.setTileID(tileID);
									}
								}
							}
						}
					}
				}
			} else {
				lastposition = world.getCoordinate(mpl.getX(), mpl.getY());
				if (lastposition != null) { // click was im ScrollPane
					for (int i = 0; i <= till && i + lastposition.getX() < pWorld.getLayout().getRowSize(); i++) {
						for (int j = 0; j <= till && j + lastposition.getY() < pWorld.getLayout().getColumnSize(); j++) {
							boolean update = true;
							if (pWorld.getLayout().getElement((int) lastposition.getX() + i, (int) lastposition.getY() + j) instanceof Field) {
								f = (Field) pWorld.getLayout().getElement((int) lastposition.getX() + i, (int) lastposition.getY() + j);
								if (f.getImg() == null || !f.getImg().equals(tiles.get(selected).getImage())) {
									update = true;
								} else {
									update = false;
								}
							}
							if (selected != -1 && selected < tiles.size()) {
								if (update) {
									if ((Game.getImageLoader().getImage(tiles.get(selected).getText()).getWidth() > 16 || Game.getImageLoader().getImage(tiles.get(selected).getText()).getHeight() > 16) && (Game.getImageLoader().getImage(tiles.get(selected).getText()).getWidth() / 16 + lastposition.getX() < pWorld.getLayout().getRowSize() && Game.getImageLoader().getImage(tiles.get(selected).getText()).getHeight() / 16 + lastposition.getY() < pWorld.getLayout().getColumnSize())) {
										//										Field[][] fields = new Field[Game.getImageLoader().getImage(tiles.get(selected).getText()).getWidth() / 16][Game.getImageLoader().getImage(tiles.get(selected).getText()).getHeight() / 16];
										//										for (int k = 0; k < Game.getImageLoader().getImage(tiles.get(selected).getText()).getWidth() / 16; k++) {
										//											for (int l = 0; l < Game.getImageLoader().getImage(tiles.get(selected).getText()).getHeight() / 16; l++) {
										//												if (pWorld.getLayout().getElement((int) lastposition.getX() + k, (int) lastposition.getY() + l) instanceof Carrier) {
										//													Carrier g = (Carrier) pWorld.getLayout().getElement((int) lastposition.getX() + k, (int) lastposition.getY() + l);
										//													g.delete();
										//												} else {
										//													f = (Field) pWorld.getLayout().getElement((int) lastposition.getX() + k, (int) lastposition.getY() + l);
										//													Game.getImageLoader().addImage(tiles.get(selected).getText() + k + l + ";16;16", Game.getImageLoader().getImage(tiles.get(selected).getText()).getSubimage(0 + k * 16, 0 + l * 16, 16, 16));
										//													f.setImg(tiles.get(selected).getText() + k + l);
										//
										//												}
										//												fields[k][l] = f;
										//											}
										//										}
										//
										//										GuiElement carry = new Carrier(fields, (int) lastposition.getX(), (int) lastposition.getY());
										//										//										for (int k = 0; k < Game.getImageLoader().getImage(tiles.get(selected).getText()).getWidth() / 16; k++) {
										//										//											for (int l = 0; l < Game.getImageLoader().getImage(tiles.get(selected).getText()).getHeight() / 16; l++) {
										//										pWorld.getLayout().setElement(carry, (int) lastposition.getX(), (int) lastposition.getY());
										//										//											}
										//										//										}

									} else {
										f.setImg(tiles.get(selected).getText());
										f.setTileID(tileID);
									}
								}
							}
						}
					}
				}
			}

		} else if (!mpl.isLeftClicked() && lastposition != null) {
			lastposition = null;
		} else {

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
		case "L1":
			lblLayer.setText("Aktivierter Layer = " + e.getActionCommand());
			pWorld.setActualLayer(0);
			loadTiles(e.getActionCommand(), this);
			break;
		case "L2":
			lblLayer.setText("Aktivierter Layer = " + e.getActionCommand());
			pWorld.setActualLayer(1);
			loadTiles(e.getActionCommand(), this);
			break;
		case "L3":
			lblLayer.setText("Aktivierter Layer = " + e.getActionCommand());
			pWorld.setActualLayer(2);
			loadTiles(e.getActionCommand(), this);
			break;
		case "L4":
			lblLayer.setText("Aktivierter Layer = " + e.getActionCommand());
			pWorld.setActualLayer(3);
			loadTiles(e.getActionCommand(), this);
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
			int tile = isInTiles(e.getActionCommand());
			if (tile != -1) {
				selectTile(tile);
				tileID = e.getActionCommand();
			} else {
				System.out.println("Unknown ActionEvent: " + e.getActionCommand());
			}
			break;
		}
	}

	public int isInTiles(String tile) {
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).getText().equals(tile)) {
				return i;
			}
		}

		return -1;
	}

	public void selectTile(int selected) {
		this.selected = selected;
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).setBorder(false);
		}

		tiles.get(selected).setBorder(true);
	}

	public void mapToString() {
		StringBuffer allLayers = new StringBuffer(pWorld.getLayout().getRowSize() + ";" + pWorld.getLayout().getColumnSize());
		for (int i = 0; i < pWorld.numberOfLayouts(); i++) {
			pWorld.setActualLayer(i);
			StringBuffer map = new StringBuffer();
			for (int j = 0; j < pWorld.getLayout().getRowSize(); j++) {
				for (int k = 0; k < pWorld.getLayout().getColumnSize(); k++) {
					if (((Field) pWorld.getLayout().getElement(k, j)).getTileID() != null) {
						map.append("(" + ((Field) pWorld.getLayout().getElement(k, j)).getTileID() + ")");
					} else {
						map.append("(v)");
					}
				}
			}
			String actTile = map.substring(0, nextTileInString(0, map.toString()));
			int counter = 1;
			StringBuffer tmp = new StringBuffer();
			for (int j = actTile.length(); j < map.length() - 1; j += actTile.length()) {
				if (map.substring(j, nextTileInString(j, map.toString())).equals(actTile)) {
					counter++;
				} else {
					tmp.append(counter + actTile + ";");
					actTile = map.substring(j, nextTileInString(j, map.toString()));
					counter = 1;
				}
			}
			tmp.append(counter + actTile);
			allLayers.append("\n" + tmp);
		}

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("data/"));
		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".mpd");
				fw.write(allLayers.toString());
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
				int layouts = 0;
				while ((line = bf.readLine()) != null) {
					pWorld.addLayout(new GridLayout(width, height, pWorld));
					pWorld.setActualLayer(layouts);
					for (int i = 0; i < height; i++) {
						for (int j = 0; j < width; j++) {
							pWorld.addElement(new Field(i * 16, j * 16));
						}
					}
					int containerI = -1;
					int containerJ = 0;
					//					int offset = nextTileInString(0, line);
					String tiles[] = line.split(";");
					for (int i = 0; i < tiles.length; i++) {
						//						String tile = line.substring(i, nextTileInString(i, line));
						data = tiles[i].split("\\(", 2);
						int quantity = Integer.parseInt(data[0]);
						String imgname = data[1].substring(0, data[1].length() - 1);
						for (int j = quantity; j > 0; j--) {
							if (containerI < height - 1) {
								containerI++;
							} else {
								containerJ++;
								containerI = 0;
							}
							if (!imgname.equals("v")) {
								((Field) pWorld.getLayout().getElement(containerI, containerJ)).setImg(imgname);
							}
						}
					}
					layouts++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public int nextTileInString(int start, String searchString) {
		int count = 0;
		int i = start;
		boolean first = false;
		for (i = start; i < searchString.length(); i++) {
			if (searchString.charAt(i) == '(') {
				first = true;
				count++;
			} else if (searchString.charAt(i) == ')') {
				count--;
			}
			if (count == 0 && first) {
				return i + 1;
			}
		}

		return -1;
	}

	public void loadTiles(String layer, ActionListener action) {
		tiles.clear();
		ArrayList<String> imgs = Game.getImageLoader().getImages(layer);
		pTiles.setLayout(new GridLayout(imgs.size() / 3 + 2, imgs.size() / 3 + 1, pTiles));
		for (int i = 0; i < imgs.size(); i++) {
			Button tile = null;
			tile = new Button(0, 0, Game.getImageLoader().getImage(imgs.get(i)));
			tile.setText(imgs.get(i));
			tile.setWidth(16);
			tile.setHeight(16);
			tile.addActionListener(action);
			pTiles.addElement(tile);
			tiles.add(tile);
		}
	}
}