package gameScreens;

import dataManagement.MapManager;
import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.BigField;
import gui.Button;
import gui.Carrier;
import gui.Field;
import gui.GridLayout;
import gui.GuiElement;
import gui.Label;
import gui.LayerPanel;
import gui.Panel;
import gui.ScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import Utilitys.Point;

public class WorldEditor extends Screen implements ActionListener {
	int scale = 32;
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
		Field.drawBorder = true;
		setW(this.getScreenFactory().getGame().getWindow().getWidth());
		setH(this.getScreenFactory().getGame().getWindow().getHeight());
		pWorld = new LayerPanel(150, 40, width * scale, height * scale);
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
					Field next = new Field();
					pWorld.addElement(next);
					if (l == 0) {
						next.setImg("grass.png");
						next.setTileID("grass.png"); // Default background
					}
				}
			}
		}
		pWorld.setActualLayer(0);

		Panel pMenue = new Panel(0, 0, getW(), 25);
		pMenue.setLayout(new GridLayout(1, 5, pMenue));

		pTiles = new Panel(0, 40, 145, getH() - 185, true);
		pTiles.setLayout(new GridLayout(3, 1, pTiles));

		Panel pFunctions = new Panel(0, pTiles.getY() + pTiles.getHeight(), getW(), getH() - pTiles.getHeight() - 80,
				true);
		pFunctions.setLayout(new GridLayout(5, 3, pFunctions));

		// Menue Start

		Button btnL1 = null;
		Button btnL2 = null;
		Button btnL3 = null;
		Button btnL4 = null;

		btnL1 = new Button("L1", true, false);
		btnL2 = new Button("L2", true, false);
		btnL3 = new Button("L3", true, false);
		btnL4 = new Button("L4", true, false);

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

		Button cursorSizePlus = new Button("Cursorgroesse erhoehen", true, false);
		cursorSizePlus.addActionListener(this);
		Button cursorSizeMinus = new Button("Cursorgroesse verkleinern", true, false);
		cursorSizeMinus.addActionListener(this);
		Button save = new Button("Karte speichern", true, false);
		save.addActionListener(this);
		Button load = new Button("Karte laden", true, false);
		load.addActionListener(this);

		// ******** Zoom Buttons ********************
		Button zoomin = new Button("+", true, false);
		zoomin.addActionListener(this);

		Button zoomout = new Button("-", true, false);
		zoomout.addActionListener(this);
		// ********************************************

		Button backtomenue = new Button("Exit", true, false);
		backtomenue.addActionListener(this);

		Label lblCursorSize = new Label("Aktuelle Cursor Groesse = " + cursorSize);

		functions.add(cursorSizePlus);
		functions.add(cursorSizeMinus);
		functions.add(lblCursorSize);
		functions.add(save);
		functions.add(load);
		functions.add(zoomin);
		functions.add(zoomout);
		functions.add(backtomenue);
		// functions.add(new TextField(0,0,0,0));

		for (int i = 0; i < functions.size(); i++) {
			pFunctions.addElement(functions.get(i));
		}

		// Funktion end

		addGuiElement(pMenue);
		addGuiElement(pTiles);
		addGuiElement(pFunctions);

		loadTiles("L1", this);

	}

	@Override
	public void onUpdate() {

		super.onUpdate();
		MousepadListener mpl = getScreenFactory().getGame().getMousepadListener();
		Field f = new Field(mpl.getX(), mpl.getY());
		int till = cursorSize - 1;
		if (till < 0) {
			till = 0;
		}
		if (mpl.isLeftClicked() && lastposition == null) { // pro click kommt
															// nur eine aktion
															// hierdurch
			if (mpl.isDragging()) {
				lastposition = world.getCoordinate(mpl.getMarkX(), mpl.getMarkY());
				if (lastposition != null) { // click was im ScrollPane
					for (int i = 0; i <= till && i + lastposition.getX() < pWorld.getLayout().getRowSize(); i++) {
						for (int j = 0; j <= till
								&& j + lastposition.getY() < pWorld.getLayout().getColumnSize(); j++) {
							if (pWorld.getLayout().getElement(lastposition.getX() + i,
									lastposition.getY() + j) instanceof BigField) {
								((BigField) pWorld.getLayout().getElement(lastposition.getX() + i,
										lastposition.getY() + j)).delete();
							} else if (pWorld.getLayout().getElement(lastposition.getX() + i,
									lastposition.getY() + j) instanceof Field) {
								// System.out.println("Feld");
								f = (Field) pWorld.getLayout().getElement(lastposition.getX() + i,
										lastposition.getY() + j);
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
					if (selected != -1 && selected < tiles.size()) {
						BufferedImage selectedImage = Game.getImageManager().getImage(tiles.get(selected).getText());
						if ((selectedImage.getWidth() > scale || selectedImage.getHeight() > scale)
								&& (selectedImage.getWidth() / scale + lastposition.getX() < pWorld.getLayout()
										.getRowSize()
								&& selectedImage.getHeight() / scale + lastposition.getY() < pWorld.getLayout()
										.getColumnSize())) {
							for (int i = lastposition.getX(); i < pWorld.getLayout().getRowSize()
									&& i < lastposition.getX() + selectedImage.getWidth() / scale; i++) {
								for (int j = lastposition.getY(); j < pWorld.getLayout().getColumnSize()
										&& j < lastposition.getY() + selectedImage.getHeight() / scale; j++) {
									if (pWorld.getLayout().getElement(i, j) instanceof BigField) {
										((BigField) pWorld.getLayout().getElement(i, j)).delete();
									}
								}
							}

							Carrier c = new Carrier(lastposition.getX(), lastposition.getY(), selectedImage,
									pWorld.getLayout());
							c.setX((lastposition.getX() * scale + pWorld.getX()));
							c.setY((lastposition.getY() * scale + pWorld.getY()));
							for (int k = lastposition.getX(); k < pWorld.getLayout().getRowSize()
									&& k < lastposition.getX() + selectedImage.getWidth() / scale; k++) {
								for (int l = lastposition.getY(); l < pWorld.getLayout().getColumnSize()
										&& l < lastposition.getY() + selectedImage.getHeight() / scale; l++) {
									BigField bf = new BigField(k * scale + pWorld.getX(), l * scale + pWorld.getY(), c);
									bf.setHeight(scale);
									bf.setWidth(scale);
									pWorld.getLayout().setElement(bf, k, l);
								}
							}
							c.init();
						} else {
							for (int i = 0; i <= till
									&& i + lastposition.getX() < pWorld.getLayout().getRowSize(); i++) {
								for (int j = 0; j <= till
										&& j + lastposition.getY() < pWorld.getLayout().getColumnSize(); j++) {
									boolean update = true;
									if (pWorld.getLayout().getElement(lastposition.getX() + i,
											lastposition.getY() + j) instanceof BigField) {
										((BigField) pWorld.getLayout().getElement(lastposition.getX() + i,
												lastposition.getY() + j)).delete();
									}
									if (pWorld.getLayout().getElement(lastposition.getX() + i,
											lastposition.getY() + j) instanceof Field) {
										f = (Field) pWorld.getLayout().getElement(lastposition.getX() + i,
												lastposition.getY() + j);
										if (f.getImg() == null || !f.getImg().equals(tiles.get(selected).getImage())) {
											update = true;
										} else {
											update = false;
										}
									}
									if (update) {
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
			mapToString(false);
			break;
		case "Karte laden":
			MapManager.loadMap(pWorld, null); // null to call JFileChooser
			pWorld.setActualLayer(0);
			width = pWorld.getWidth() / scale;
			height = pWorld.getHeight() / scale;
			loadTiles("L1", this);
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
		case "Exit":
			System.out.println("WorldEditor exit called. Saving current map to data/Maps/tmp.mpd");
			mapToString(true);
			this.getScreenFactory().createScreen(new MainScreen(this.getScreenFactory()));
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

	public void mapToString(boolean savetmp) {
		StringBuffer allLayers = new StringBuffer(
				pWorld.getLayout().getRowSize() + ";" + pWorld.getLayout().getColumnSize() + ";");
		int last = pWorld.getActualLayer();
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

		int retrival = JFileChooser.APPROVE_OPTION; // in case we save tmpS
		JFileChooser chooser = null;
		if (!savetmp) {
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("data/"));
			retrival = chooser.showSaveDialog(null);
		}
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter fw = null;
				if (!savetmp) {
					if (chooser.getSelectedFile().getName().contains(".")) {
						fw = new FileWriter(chooser.getSelectedFile());
					} else {
						fw = new FileWriter(chooser.getSelectedFile() + ".mpd");
					}
				} else {
					fw = new FileWriter("data/Maps/tmp.mpd");
				}
				fw.write(allLayers.toString());
				fw.flush();
				fw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		pWorld.setActualLayer(last);
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
		ArrayList<String> imgs = Game.getImageManager().getImages(layer);
		pTiles.setLayout(new GridLayout(imgs.size() / 3 + 2, imgs.size() / 3 + 1, pTiles));
		for (int i = 0; i < imgs.size(); i++) {
			Button tile = null;
			tile = new Button(0, 0, Game.getImageManager().getImage(imgs.get(i)));
			tile.setText(imgs.get(i));
			tile.setWidth(scale);
			tile.setHeight(scale);
			tile.addActionListener(action);
			tile.setTextShowing(false);
			pTiles.addElement(tile);
			tiles.add(tile);
		}
	}
}