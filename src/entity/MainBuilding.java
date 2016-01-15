package entity;

import gameEngine.Game;
import gameEngine.MousepadListener;
import gameEngine.Player;
import gameEngine.Screen;
import gameScreens.GameScreen;
import gui.Button;
import gui.EntityOptions;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainBuilding extends Building implements ActionListener {

	private boolean wasnotmarked = true;
	private boolean marked;
	private boolean menueisopen;
	private final int size = 200;
	private int level = 1;

	public MainBuilding(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
		setMaxLife(200);
		setLife(200);
		Game.getImageManager().getImage(img_name);
		this.img_name = img_name;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Screen screen) {
		if (current_status != STATUS_FINISHED) {
			if (life >= maxLife) {
				current_status = STATUS_FINISHED;
			}
			return;
		}
		super.update(screen);

		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();
		if (mpl.isLeftClicked()) {
			if (mpl.isDragging()) {
				// if (screen.getDraggingZone().intersects(getBounds())) {
				// marked = true;
				// } else {
				// marked = false;
				// }
			} else {
				if (getImageBounds().contains(mpl.getX(), mpl.getY()) && getOwner() == Player.MAIN_PLAYER) { // 50
																												// dynamisch
																												// machen
					marked = true;
				} else {
					marked = false;
				}
			}
		}

		if (marked && wasnotmarked) {
			openMenue();
			wasnotmarked = false;
		} else if (!marked) {
			wasnotmarked = true;

			if (menueisopen && mpl.isLeftClicked() && !EntityOptions.singleton.isMarked()) {
				EntityOptions.singleton.setOptions(null, null);
			}
		}
	}

	public void openMenue() {
		ArrayList<Button> options = new ArrayList<Button>();

		options.add(new Button(Game.getImageManager().getImage("worker.png"), false, false));
		options.get(0).addActionListener(this);
		options.get(0).setText("Worker");
		if (level < 3) {
			options.add(new Button(Game.getImageManager().getImage("M_MainBuilding_" + (level + 1) + ".png"), false, false));
			options.get(1).addActionListener(this);
			options.get(1).setText("Upgrade");
		}

		EntityOptions.singleton.setOptions(options, this);

		menueisopen = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Worker":
			m_curtask = task.t_spawn;
			toSpawn = 1;
			break;
		case "Upgrade":
			System.out.println("Upgrade");
			m_curtask = task.t_upgrading;
			Game.getImageManager().getImage(img_name, size, size);
			img_name = "M_MainBuilding_" + (level + 1) + ".png";
			setMaxLife(getMaxLife() * 3);
			setStatus(STATUS_BUIDLING);
			break;
		default:
			m_curtask = task.t_none;
			System.out.println("Action: " + e.getActionCommand());
		}
	}

	@Override
	public Rectangle getImageBounds() {
		super.getImageBounds();
		imgrg.width = size;
		imgrg.height = size;
		return imgrg;
	}

	/*************** Task Section ****************/

	public void taskSpawn(Screen screen) {
		GameScreen gamescreen = (GameScreen) screen; // needed for
														// pointToMapConst

		if (Player.getPlayer(getOwner()).getWood() < 50 || Player.getPlayer(getOwner()).getWood() < 25) {
			System.out.println("Get at least 50 Wood and 25 Stone to Spawn a Unit!");
			System.out.println("This is an example of using Resources to spawn Units.");

			m_curtask = task.t_none;
			return;
		} else {
			Player.getPlayer(getOwner()).setWood(Player.getPlayer(getOwner()).getWood() - 50);
			Player.getPlayer(getOwner()).setStone(Player.getPlayer(getOwner()).getStone() - 25);
		}

		loop: for (int j = 0; j < 6; j++) { // Limited to 36 tries
			for (int i = 0; i < 6; i++) {
				// I havespace to spawn him here?
				if (!gamescreen.getEntityWithMap().containsValue(Screen.pointToMapConst(getX() - 100 + 25 * i, getY() + 25 * (j + 1)))) {

					if (toSpawn == 1) {
						Worker work = new Worker(getX() - 100 + 25 * i, getY() + 25 * (j + 1), getOwner());

						gamescreen.getEntityWithMap().put(work, Screen.pointToMapConst(work.getX(), work.getY()));
					}

					break loop;
				}
			}
		}

		m_curtask = task.t_none; // Task complete
	}

	@Override
	public void taskUpgrading(Screen screen) {
		if (life == getMaxLife()) {
			setStatus(STATUS_FINISHED);
			m_curtask = task.t_none;
			level++;
		}

		if (life != maxLife) {
			setLife(getLife() + 1);
		}

	}
}
