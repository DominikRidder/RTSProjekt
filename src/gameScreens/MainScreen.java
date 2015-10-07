package gameScreens;

import entity.Unit;
import gameEngine.Screen;
import gameEngine.ScreenFactory;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class MainScreen extends Screen {

	private int y = 0;
	private ArrayList<Unit> units = new ArrayList<Unit>();

	public MainScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		Random rnd = new Random();
		System.out.println("Main Creating!");
		for (int i = 0; i < 100; i++) {
			units.add(new OrkTest(rnd.nextInt(700) + 40, rnd.nextInt(500) + 40));
		}

	}

	@Override
	public void onUpdate() {
		for (int i = 0; i < units.size(); i++) {
			units.get(i).update(this);
		}
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		for (int i = 0; i < units.size(); i++) {
			units.get(i).draw(g2d);
		}
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}
}
