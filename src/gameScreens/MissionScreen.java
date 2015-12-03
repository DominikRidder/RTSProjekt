package gameScreens;

import entity.AbstractEntity;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Field;
import gui.GridLayout;
import gui.LayerPanel;
import gui.ScrollPane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;

public class MissionScreen extends Screen {

	public MissionScreen(ScreenFactory screenFactory) {
		super(screenFactory);
		// TODO Auto-generated constructor stub
	}

	private ScrollPane world;
	private int width = 50;
	private int height = 50;
	private LayerPanel pWorld;
	
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
								((Field) pWorld.getLayout().getElement(containerI, containerJ)).setTileID(imgname);
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
		
	}

	@Override
	public List<AbstractEntity> getEntitys() {
		// TODO Auto-generated method stub
		return null;
	}
}
