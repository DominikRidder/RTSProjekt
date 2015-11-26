package gameScreens;

import entity.AbstractEntity;
import gameEngine.Game;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.Label;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoryScreen extends Screen implements ActionListener {

	private static final int RACE_HUMAN = 0, RACE_SHADOW = 1, RACE_ANCIENT = 2;

	private String imglocation[] = { "story_hum.png", "story_sha.png", "story_aci.png" };

	private String humMission[] = { "a", "b", "c" };
	private String shaMission[] = { "a", "b" };
	private String bothMission[] = { "a", "b", "c", "d" };

	private ArrayList<Button> bothm;
	private ArrayList<Button> humm;
	private ArrayList<Button> sham;

	private String races[] = { "Human", "Shadow", "Ancient" };

	private int actual = RACE_HUMAN;

	private Label spRace;
	private Label finished;

	private Button background;

	private int progress[] = { 0,0,0,0 };

	private boolean finishedCreating;

	public StoryScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		HashMap<String, String> info = Game.getInfoLoader().getInfo("story.inf");
		progress[RACE_HUMAN] = Integer.parseInt(info.get("HUMAN_PROGRESS"));
		progress[RACE_SHADOW] = Integer.parseInt(info.get("SHADOW_PROGRESS"));
		progress[RACE_ANCIENT] = Integer.parseInt(info.get("ANCIENT1_PROGRESS"));
		progress[RACE_ANCIENT+1] = Integer.parseInt(info.get("ANCIENT2_PROGRESS"));
		
		background = new Button(0, 0, Game.getImageLoader().getImage(imglocation[actual]));
		background.setWidth(getScreenFactory().getGame().getWindow().getWidth());
		background.setHeight(getScreenFactory().getGame().getWindow().getHeight());
		addGuiElement(background);

		/*********************** MISSIONS ******************/
		int maxunique = humMission.length > shaMission.length ? humMission.length : shaMission.length;

		int misheight = maxunique + bothMission.length;
		int relHeight = getScreenFactory().getGame().getWindow().getHeight() / (misheight + 2);
		int width = getScreenFactory().getGame().getWindow().getWidth();

		bothm = new ArrayList<Button>();
		humm = new ArrayList<Button>();
		sham = new ArrayList<Button>();

		for (int i = 0; i < bothMission.length; i++) {
			Button b = new Button(width / 2 - 50, relHeight * (bothMission.length - i), 100, relHeight / 2, "Both " + i);
			this.addGuiElement(b);
			bothm.add(b);
		}

		for (int i = 0; i < humMission.length; i++) {
			Button b = new Button((int) (width / 4 - 50 + width / 4 * (((double) i) / (humMission.length))), (int) (relHeight * (bothMission.length) + relHeight * maxunique * ((double) humMission.length - i) / (humMission.length)), 100, relHeight / 2, "Human " + i);
			this.addGuiElement(b);
			humm.add(b);
		}

		for (int i = 0; i < shaMission.length; i++) {
			Button b = new Button((int) (width / 4 * 3 - 50 - width / 4 * (((double) i) / (shaMission.length))), (int) (relHeight * (bothMission.length) + relHeight * maxunique * ((double) shaMission.length - i) / (shaMission.length)), 100, relHeight / 2, "shadow " + i);
			this.addGuiElement(b);
			sham.add(b);
		}
		/********************************************************/

		Button arrowleft = new Button(30, 50, 20, 50, "<-");
		arrowleft.addActionListener(this);
		Button arrowright = new Button(150, 50, 20, 50, "->");
		arrowright.addActionListener(this);
		spRace = new Label(50, 50, 100, 50, "Human");
		finished = new Label(width - 150, 50, 100, 50, "Finished: " + progress[actual]);

		addGuiElement(arrowleft);
		addGuiElement(arrowright);
		addGuiElement(spRace);
		addGuiElement(finished);

		handleMissionVisibility();
		finishedCreating = true;
	}

	@Override
	public List<AbstractEntity> getEntitys() {
		return null;
	}

	public void onDraw(Graphics2D g2d) {
		if (!finishedCreating) {
			return;
		}

		Button b1 = null;
		Button b2 = null;

		g2d.setColor(Color.black);

		if (actual == RACE_HUMAN || actual == RACE_ANCIENT) {
			b1 = humm.get(0);
			for (int i = 1; i < humm.size(); i++) { // Human Lines
				b2 = humm.get(i);
				drawLine(b1, b2, g2d);
				b1 = b2;
			}
			b2 = bothm.get(0);
			drawLine(b1, b2, g2d);
		}

		if (actual == RACE_SHADOW || actual == RACE_ANCIENT) {
			b1 = sham.get(0);
			for (int i = 1; i < sham.size(); i++) { // Shadow Lines
				b2 = sham.get(i);
				drawLine(b1, b2, g2d);
				b1 = b2;
			}
			b2 = bothm.get(0);
			drawLine(b1, b2, g2d);
		}

		b1 = bothm.get(0);
		for (int i = 1; i < bothm.size(); i++) { // Both Lines
			b2 = bothm.get(i);
			drawLine(b1, b2, g2d);
			b1 = b2;
		}

		super.onDraw(g2d);
	}

	public void drawLine(Button b1, Button b2, Graphics2D g2d) {
		if (b1.isVisible() && b2.isVisible()) {
			g2d.drawLine(b1.getX() + b1.getWidth() / 2, b1.getY(), b2.getX() + b2.getWidth() / 2, b2.getY());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "->":
			changeActual(1);
			break;
		case "<-":
			changeActual(-1);
			break;
		default:
			System.out.println("Unknown ActionEvent: " + e.getActionCommand());
		}

	}

	private void changeActual(int i) {
		actual = (actual + i) % 3;
		if (actual < 0) {
			actual = races.length - 1;
		}

		if (actual == RACE_ANCIENT && !(progress[RACE_HUMAN] == humm.size() || progress[RACE_SHADOW] == sham.size())) {
			changeActual(i);
			return;
		}

		spRace.setText(races[actual]);

		int fin = progress[actual];
		if (actual == RACE_ANCIENT) {
			fin = fin > progress[RACE_ANCIENT + 1] ? fin : progress[RACE_ANCIENT + 1];
			if (fin >= bothm.size()) {
				fin = bothm.size();
				int next = progress[RACE_ANCIENT] - bothm.size();
				if (next > 0) {
					fin += next;
				}
				next = progress[RACE_ANCIENT + 1] - bothm.size();
				if (next > 0) {
					fin += next;
				}
			}
		}
		finished.setText("Finished: " + fin);

		background.setImage(Game.getImageLoader().getImage(imglocation[actual]));

		handleMissionVisibility();
	}

	public void handleMissionVisibility() {
		ArrayList<Button> visible = new ArrayList<Button>();
		ArrayList<Button> notvisible = null;
		int prog = progress[actual];

		if (actual == RACE_HUMAN) {
			notvisible = sham;
			for (Button b : humm) {
				visible.add(b);
			}
			for (Button b : bothm) {
				visible.add(b);
			}

		} else if (actual == RACE_SHADOW) {
			notvisible = humm;
			for (Button b : sham) {
				visible.add(b);
			}
			for (Button b : bothm) {
				visible.add(b);
			}

		} else if (actual == RACE_ANCIENT) {
			AcientVisibility();
			return;
		}

		for (int i = 0; i < visible.size(); i++) {
			if (i <= prog) {
				visible.get(i).setVisible(true);
			} else {
				visible.get(i).setVisible(false);
			}
		}

		for (int i = 0; i < notvisible.size(); i++) {
			notvisible.get(i).setVisible(false);
		}
	}

	private void AcientVisibility() {
		int humprog = progress[RACE_ANCIENT];
		int shaprog = progress[RACE_ANCIENT + 1];
		int bothprog = humprog > shaprog ? humprog : shaprog;

		for (int i = bothm.size() - 1; i >= 0; i--) {
			if (bothm.size() - i - 1 < bothprog) {
				bothm.get(i).setVisible(true);
			} else {
				bothm.get(i).setVisible(false);
			}
		}

		for (int i = humm.size() - 1; i >= 0; i--) {
			if ((bothm.size() + humm.size()) - i - 1 < humprog) {
				humm.get(i).setVisible(true);
			} else {
				humm.get(i).setVisible(false);
			}
		}

		for (int i = sham.size() - 1; i >= 0; i--) {
			if ((bothm.size() + sham.size()) - i - 1 < shaprog) {
				sham.get(i).setVisible(true);
			} else {
				sham.get(i).setVisible(false);
			}
		}
	}
}
