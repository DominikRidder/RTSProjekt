package gameScreens;
import gameEngine.Screen;
import gameEngine.ScreenFactory;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class MainScreen extends Screen {

	private int y = 0;
	private ArrayList<OrkTest> orks = new ArrayList<OrkTest>();

	public MainScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		Random rnd = new Random();
		System.out.println("Main Creating!");
		for (int i = 0; i < 100; i++) {
			orks.add(new OrkTest(rnd.nextInt(700) + 40, rnd.nextInt(500) + 40));
		}

	}

	@Override
	public void onUpdate() {
		for (int i = 0; i < orks.size(); i++) {
			orks.get(i).update(this);
		}
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		for (int i = 0; i < orks.size(); i++) {
			orks.get(i).draw(g2d);
		}
	}
}
