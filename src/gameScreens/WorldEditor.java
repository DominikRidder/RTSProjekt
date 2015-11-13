package gameScreens;

import entity.AbstractEntity;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Field;
import gui.GridLayout;
import gui.Panel;
import gui.ScrollPane;

import java.util.ArrayList;

public class WorldEditor extends Screen {
	int scale = 1;
	int width = 50;
	int height = 50;
	Panel pWorld;
	ScrollPane world;

	public WorldEditor(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		setW(this.getScreenFactory().getGame().getWindow().getWidth());
		setH(this.getScreenFactory().getGame().getWindow().getHeight());
		pWorld = new Panel(300, 0, width*16, height*16);
		world = new ScrollPane(pWorld, getW() - 320, getH() - 320);
		pWorld.setLayout(new GridLayout(width, height, pWorld));
		addGuiElement(world);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pWorld.addElement(new Field(i * 16, j * 16));
			}
		}
	}

	@Override
	public ArrayList<AbstractEntity> getEntitys() {
		// TODO Auto-generated method stub
		return null;
	}

}