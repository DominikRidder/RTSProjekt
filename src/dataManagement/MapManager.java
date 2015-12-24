package dataManagement;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import entity.AbstractEntity;
import entity.MainBuilding;
import gameEngine.Game;
import gui.BigField;
import gui.Carrier;
import gui.Field;
import gui.GridLayout;
import gui.LayerPanel;

public class MapManager {

	public static List<AbstractEntity> loadMap(LayerPanel pWorld, String maplocation) {
		File fMap = null;
		List<AbstractEntity> defaultEntitys = new ArrayList<AbstractEntity>();
		if (maplocation == null) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("./data/"));
			int retrival = chooser.showOpenDialog(null);
			if (retrival != JFileChooser.APPROVE_OPTION) {
				return defaultEntitys;
			}
			fMap = new File(chooser.getSelectedFile().getAbsolutePath());
		} else {
			fMap = new File(maplocation);
		}
		try (BufferedReader bf = new BufferedReader(new FileReader(fMap))) {
			String line = bf.readLine();
			String[] data = line.split(";");
			int height = Integer.parseInt(data[0]);
			int width = Integer.parseInt(data[1]);
			pWorld.removeAllLayouts();
			pWorld.setWidth(width * 32);
			pWorld.setHeight(height * 32);
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
				// int offset = nextTileInString(0, line);
				String tiles[] = line.split(";");
				for (int i = 0; i < tiles.length; i++) {
					// String tile = line.substring(i, nextTileInString(i,
					// line));
					data = tiles[i].split("\\(", 2);
					int quantity = Integer.parseInt(data[0]);
					String imgData = "";
					for (int j = data.length - 1; j > 0; j--) {
						imgData += data[j];
					}
					String imgname = imgData.substring(0, imgData.length() - 1);
					if (imgname.startsWith("s_")) {
						defaultEntitys.add(new MainBuilding(containerI * 16, containerJ * 16, 10, "Barracks.png", 0));
					}
					if (imgname.contains(",")) {
						String[] bigImageData = imgname.split(",");
						if (Game.getImageManager().getImage(bigImageData[0] + bigImageData[1] + bigImageData[2] + ";32;32") != ((Field) pWorld.getLayout().getElement(Integer.parseInt(bigImageData[1]), Integer.parseInt(bigImageData[2]))).getImg()) {
							BufferedImage image = Game.getImageManager().getImage(bigImageData[0]);
							Carrier c = new Carrier(Integer.parseInt(bigImageData[1]), Integer.parseInt(bigImageData[2]), image, pWorld.getLayout());
							c.setX((Integer.parseInt(bigImageData[1]) * 16 + pWorld.getX()));
							c.setY((Integer.parseInt(bigImageData[2]) * 16 + pWorld.getY()));
							for (int k = Integer.parseInt(bigImageData[1]); k < pWorld.getLayout().getRowSize() && k < Integer.parseInt(bigImageData[1]) + image.getWidth() / 16; k++) {
								for (int l = Integer.parseInt(bigImageData[2]); l < pWorld.getLayout().getColumnSize() && l < Integer.parseInt(bigImageData[2]) + image.getHeight() / 16; l++) {
									BigField bigf = new BigField(k * 16 + pWorld.getX(), l * 16 + pWorld.getY(), c);
									bigf.setHeight(16);
									bigf.setWidth(16);
									pWorld.getLayout().setElement(bigf, k, l);
								}
							}
							c.init();
						} else {
							containerI++;
						}
					} else {
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
				}
				layouts++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defaultEntitys;

	}
}
