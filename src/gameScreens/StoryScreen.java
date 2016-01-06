package gameScreens;

import gameEngine.Game;
import gameEngine.Mission;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.CompactLayout;
import gui.Label;
import gui.Monolog;
import gui.Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import dataManagement.ImageManager;
import dataManagement.InfoManager;
import entity.AbstractEntity;

public class StoryScreen extends Screen implements ActionListener {

	private static final int RACE_HUMAN = 0, RACE_SHADOW = 1, RACE_ANCIENT = 2;

	private final String imglocation[] = { "story_hum.png", "story_sha.png",
			"story_aci.png" };

	private ArrayList<Mission> humMission;
	private ArrayList<Mission> shaMission;
	private ArrayList<Mission> bothMission;

	private ArrayList<Button> bothB;
	private ArrayList<Button> humB;
	private ArrayList<Button> shaB;

	private final String races[] = { "Human", "Shadow", "Ancient" };
	private String maplocation;

	private int actual = RACE_HUMAN;

	private Label spRace;
	private Label finished;

	private Button background;

	private final int progress[] = { 0, 0, 0, 0 };

	private boolean finishedCreating;

	private HashMap<String, String> savestate;

	private Panel previewWindow;
	private Monolog description;
	private Label title;

	private int modus;

	public StoryScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		ImageManager imgloader = Game.getImageManager();
		savestate = Game.getInfoManager().getInfo("story.inf");
		progress[RACE_HUMAN] = Integer
				.parseInt(savestate.get("HUMAN_PROGRESS"));
		progress[RACE_SHADOW] = Integer.parseInt(savestate
				.get("SHADOW_PROGRESS"));
		progress[RACE_ANCIENT] = Integer.parseInt(savestate
				.get("ANCIENT1_PROGRESS"));
		progress[RACE_ANCIENT + 1] = Integer.parseInt(savestate
				.get("ANCIENT2_PROGRESS"));

		background = new Button(Game.getImageManager().getImage(
				imglocation[actual]), false, true);
		background
				.setWidth(getScreenFactory().getGame().getWindow().getWidth());
		background.setHeight(getScreenFactory().getGame().getWindow()
				.getHeight());
		addGuiElement(background);

		/*********************** MISSIONS ******************/

		loadMissions();

		int maxunique = humMission.size() > shaMission.size() ? humMission
				.size() : shaMission.size();

		int misheight = maxunique + bothMission.size();
		int relHeight = getScreenFactory().getGame().getWindow().getHeight()
				/ (misheight + 2);
		int width = getScreenFactory().getGame().getWindow().getWidth();

		bothB = new ArrayList<Button>();
		humB = new ArrayList<Button>();
		shaB = new ArrayList<Button>();

		/******** M Buttons ***********/
		for (int i = 0; i < bothMission.size(); i++) {
			Button b = new Button(width / 2 - 50, relHeight
					* (bothMission.size() - i), 100, relHeight / 2, "Both " + i);
			b.setImage(imgloader.getImage(bothMission.get(0).getIconLocation()));
			b.setDrawText(false);
			this.addGuiElement(b);
			bothB.add(b);
			b.addActionListener(this);
		}

		for (int i = 0; i < humMission.size(); i++) {
			Button b = new Button((int) (width / 4 - 50 + width / 4
					* (((double) i) / (humMission.size()))), (int) (relHeight
					* (bothMission.size()) + relHeight * maxunique
					* ((double) humMission.size() - i) / (humMission.size())),
					100, relHeight / 2, "Human " + i);
			b.setImage(imgloader.getImage(humMission.get(0).getIconLocation()));
			b.setDrawText(false);
			this.addGuiElement(b);
			humB.add(b);
			b.addActionListener(this);
		}

		for (int i = 0; i < shaMission.size(); i++) {
			Button b = new Button((int) (width / 4 * 3 - 50 - width / 4
					* (((double) i) / (shaMission.size()))), (int) (relHeight
					* (bothMission.size()) + relHeight * maxunique
					* ((double) shaMission.size() - i) / (shaMission.size())),
					100, relHeight / 2, "Shadow " + i);
			b.setImage(imgloader.getImage(shaMission.get(0).getIconLocation()));
			b.setDrawText(false);
			this.addGuiElement(b);
			shaB.add(b);
			b.addActionListener(this);
		}
		/********************************************************/

		Button arrowleft = new Button(30, 65, 20, 20, "<-", false, true);
		arrowleft.addActionListener(this);
		arrowleft.setImage(Game.getImageManager().getImage("ArrowLeft.png"));
		Button arrowright = new Button(150, 65, 20, 20, "->", false, true);
		arrowright.addActionListener(this);
		arrowright.setImage(Game.getImageManager().getImage("ArrowRight.png"));
		spRace = new Label(50, 50, 100, 50, "Human");
		finished = new Label(width - 150, 50, 100, 50, "Finished: "
				+ progress[actual]);

		addGuiElement(arrowleft);
		addGuiElement(arrowright);
		addGuiElement(spRace);
		// addGuiElement(finished);

		/************ MISSIONS PREVIEW WINDOW *************************/

		Button invis = new Button("back", false, false);
		invis.setWidth(width * 1 / 5);
		invis.setX(width);
		invis.setHeight(getScreenFactory().getGame().getWindow().getHeight());
		invis.setImage(Game.getImageManager().getImage("InvisibleImage.png"));
		invis.setBackgroundColor(new Color(0,0,0,0));
		invis.addActionListener(this);

		Button background = new Button(Game.getImageManager().getImage("MissionImage2.png"));
		background.setX(width + width * 1 / 5);
		background.setSize(width * 4 / 5, getScreenFactory().getGame().getWindow().getHeight());
		
		Button startmission = new Button(width + width * 8 / 20,
				getScreenFactory().getGame().getWindow().getHeight() * 4 / 5,
				width * 9 / 20, getScreenFactory().getGame().getWindow()
						.getHeight() * 1 / 10, "Start Mission");
		startmission.addActionListener(this);
		startmission.setTextColor(Color.YELLOW);

		description = new Monolog("");
		description.setSize(width * 9 / 20, getScreenFactory().getGame()
				.getWindow().getHeight() * 3 / 5);
		description.setX(width + width * 8 / 20);
		description.setY(getScreenFactory().getGame()
				.getWindow().getHeight() * 1 / 5);
		description.setBackgroundColor(new Color(0,0,0,0));
		description.setTextColor(Color.BLACK);
		description.setFont(new Font("newname",Font.ITALIC, 14));
		
		title = new Label("");
		title.turnOffDecorator();
		title.setFont(new Font("somename", Font.BOLD, 20));
		title.setSize(width * 4 / 5, getScreenFactory().getGame()
				.getWindow().getHeight() * 1 / 5);
		title.setX(width + width * 1 / 5);
		title.setBackgroundColor(new Color(0,0,0,0));
		title.setTextColor(new Color(255,215,0));

		previewWindow = new Panel(width, 0, width + 1 / 5 * width,
				getScreenFactory().getGame().getWindow().getHeight());
		previewWindow.setLayout(new CompactLayout(previewWindow));

		previewWindow.addElement(background);
		previewWindow.addElement(title);
		previewWindow.addElement(description);
		previewWindow.addElement(invis);
		previewWindow.addElement(startmission);

		addGuiElement(previewWindow);

		/**************************************************************/

		handleMissionVisibility();
		finishedCreating = true;
	}

	private void loadMissions() {
		InfoManager loader = Game.getInfoManager();
		humMission = new ArrayList<Mission>();
		shaMission = new ArrayList<Mission>();
		bothMission = new ArrayList<Mission>();
		ArrayList<String> filenames = new ArrayList<String>();

		File file = new File("data/Story/Missions");

		for (File f : file.listFiles()) {
			if (f.getName().endsWith(".inf")) {
				filenames.add(f.getName());
			}
		}

		Collections.sort(filenames);

		for (String filename : filenames) {
			if (filename.startsWith("hum")) {
				humMission.add(new Mission(loader.getInfo(filename)));
			} else if (filename.startsWith("sha")) {
				shaMission.add(new Mission(loader.getInfo(filename)));
			} else if (filename.startsWith("both")) {
				bothMission.add(new Mission(loader.getInfo(filename)));
			}

		}
	}

	@Override
	public List<AbstractEntity> getEntitys() {
		return null;
	}

	@Override
	public void onDraw(Graphics2D g2d) {
		if (!finishedCreating) {
			return;
		}

		super.onDraw(g2d);

		// drawLines(g2d);
	}

	public void drawLines(Graphics2D g2d) {
		Button b1 = null;
		Button b2 = null;

		g2d.setColor(Color.black);

		if (actual == RACE_HUMAN || actual == RACE_ANCIENT) {
			b1 = humB.get(0);
			for (int i = 1; i < humB.size(); i++) { // Human Lines
				b2 = humB.get(i);
				drawLine(b1, b2, g2d);
				b1 = b2;
			}
			b2 = bothB.get(0);
			drawLine(b1, b2, g2d);
		}

		if (actual == RACE_SHADOW || actual == RACE_ANCIENT) {
			b1 = shaB.get(0);
			for (int i = 1; i < shaB.size(); i++) { // Shadow Lines
				b2 = shaB.get(i);
				drawLine(b1, b2, g2d);
				b1 = b2;
			}
			b2 = bothB.get(0);
			drawLine(b1, b2, g2d);
		}

		b1 = bothB.get(0);
		for (int i = 1; i < bothB.size(); i++) { // Both Lines
			b2 = bothB.get(i);
			drawLine(b1, b2, g2d);
			b1 = b2;
		}
	}

	public void drawLine(Button b1, Button b2, Graphics2D g2d) {
		if (b1.isVisible() && b2.isVisible()) {
			g2d.drawLine(b1.getX() + b1.getWidth() / 2, b1.getY(), b2.getX()
					+ b2.getWidth() / 2 - (b2.getX() - b1.getX()) / 2,
					b2.getY() + b2.getHeight());
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
		case "back":
			modus = 2;
			break;
		case "Start Mission":
			this.getScreenFactory().createScreen(
					new GameScreen(this.getScreenFactory(), maplocation));
			break;
		default:
			String parts[] = e.getActionCommand().split(" ");
			String race = parts[0];
			int missionNumber = Integer.parseInt(parts[1]);

			Mission mission = null;
			if (race.equals("Human")) {
				mission = humMission.get(missionNumber);
			} else if (race.equals("Shadow")) {
				mission = shaMission.get(missionNumber);
			} else if (race.equals("Both")) {
				mission = bothMission.get(missionNumber);
			}
			title.setText(mission.getName());
			description.setText(mission.getDescription());
			maplocation = mission.getMapLocation();
			modus = 1;
			// System.out.println("Unknown ActionEvent: " +
			// e.getActionCommand());
		}

	}

	private void changeActual(int i) {
		actual = (actual + i) % 3;
		if (actual < 0) {
			actual = races.length - 1;
		}

		if (actual == RACE_ANCIENT
				&& !(progress[RACE_HUMAN] == humB.size() || progress[RACE_SHADOW] == shaB
						.size())) {
			changeActual(i);
			return;
		}

		spRace.setText(races[actual]);

		int fin = progress[actual];
		if (actual == RACE_ANCIENT) {
			fin = fin > progress[RACE_ANCIENT + 1] ? fin
					: progress[RACE_ANCIENT + 1];
			if (fin >= bothB.size()) {
				fin = bothB.size();
				int next = progress[RACE_ANCIENT] - bothB.size();
				if (next > 0) {
					fin += next;
				}
				next = progress[RACE_ANCIENT + 1] - bothB.size();
				if (next > 0) {
					fin += next;
				}
			}
		}
		finished.setText("Finished: " + fin);

		background.setImage(Game.getImageManager()
				.getImage(imglocation[actual]));

		handleMissionVisibility();
	}

	public void handleMissionVisibility() {
		ArrayList<Button> visible = new ArrayList<Button>();
		ArrayList<Button> notvisible = null;
		int prog = progress[actual];

		if (actual == RACE_HUMAN) {
			notvisible = shaB;
			for (Button b : humB) {
				visible.add(b);
			}
			for (Button b : bothB) {
				visible.add(b);
			}

		} else if (actual == RACE_SHADOW) {
			notvisible = humB;
			for (Button b : shaB) {
				visible.add(b);
			}
			for (Button b : bothB) {
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

		for (int i = bothB.size() - 1; i >= 0; i--) {
			if (bothB.size() - i - 1 < bothprog) {
				bothB.get(i).setVisible(true);
			} else {
				bothB.get(i).setVisible(false);
			}
		}

		for (int i = humB.size() - 1; i >= 0; i--) {
			if ((bothB.size() + humB.size()) - i - 1 < humprog) {
				humB.get(i).setVisible(true);
			} else {
				humB.get(i).setVisible(false);
			}
		}

		for (int i = shaB.size() - 1; i >= 0; i--) {
			if ((bothB.size() + shaB.size()) - i - 1 < shaprog) {
				shaB.get(i).setVisible(true);
			} else {
				shaB.get(i).setVisible(false);
			}
		}
	}

	@Override
	public void onUpdate() {
		switch (modus) {
		case 0:
			break;
		case 1:
			if (previewWindow.getX() > 0) {
				if (previewWindow.getX() + (-previewWindow.getX()) / 10 > 0) {
					if ((previewWindow.getX()) / 10 > 0) {
						previewWindow.setX(previewWindow.getX()
								+ (-previewWindow.getX()) / 10);
					} else {
						previewWindow.setX(previewWindow.getX() - 1);
					}
				} else {
					previewWindow.setX(0);
				}
			} else {
				modus = 0;
			}
			break;
		case 2:
			int width = getScreenFactory().getGame().getWindow().getWidth();
			if (previewWindow.getX() < width) {
				if (previewWindow.getX() + (previewWindow.getX()) / 10 < width) {
					if ((previewWindow.getX()) / 10 < width) {
						previewWindow.setX(previewWindow.getX()
								+ (previewWindow.getX()) / 10 + 1);
					} else {
						previewWindow.setX(previewWindow.getX() + 1);
					}
				} else {
					previewWindow.setX(width);
				}
			} else {
				modus = 0;
			}
			break;
		default:
			break;
		}
		super.onUpdate();
	}
}
