package entity;

import gameEngine.Game;
import gameEngine.MousepadListener;
import gameEngine.Player;
import gameEngine.Screen;
import gameScreens.GameScreen;
import gui.Button;
import gui.EntityOptions;
import gui.Monolog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainBuilding extends Building implements ActionListener {

	protected static final ResourceInfo res = new ResourceInfo(250, 50, 0);
	protected static final ProduceInfomation produceInformation = new ProduceInfomation(
			res, description());

	private boolean wasnotmarked = true;
	private boolean menueisopen;
	private final int size = 200;
	private int level = 1;
	private final int FOOD = 10;

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
		super.update(screen);

		MousepadListener mpl = screen.getScreenFactory().getGame()
				.getMousepadListener();
		if (mpl.isLeftClicked()) {
			if (mpl.isDragging()) {
				// if (screen.getDraggingZone().intersects(getBounds())) {
				// marked = true;
				// } else {
				// marked = false;
				// }
			} else {
				if (getImageBounds().contains(mpl.getX(), mpl.getY())
						&& getOwner() == Player.MAIN_PLAYER) { // 50
																// dynamisch
																// machen
					marked = true;
				} else {
					marked = false;
				}
			}
		}

		if (marked && wasnotmarked && current_status == STATUS_FINISHED) {
			openMenue();
			wasnotmarked = false;
		} else if (!marked) {
			wasnotmarked = true;

			if (menueisopen && mpl.isLeftClicked()
					&& !EntityOptions.singleton.isMarked()) {
				EntityOptions.singleton.setOptions(null, null);
			}
		}
	}

	public void openMenue() {
		ArrayList<Option> options = new ArrayList<Option>();

		options.add(new Option(Game.getImageManager().getImage("worker.png"),
				false, false));
		options.get(0).addActionListener(this);
		options.get(0).setText("Worker");
		options.get(0).setToolTip(
				Monolog.createToolTip(200, 100, Worker.produceInformation));
		if (level < 2) {
			options.add(new Option(Game.getImageManager().getImage(
					"M_MainBuilding_" + (level + 1) + ".png"), false, false));
			options.get(1).addActionListener(this);
			options.get(1).setText("Upgrade");
		} else if (level < 3) {
			options.add(new Option(Game.getImageManager().getImage(
					"Soldier.png"), false, false));
			options.get(1).addActionListener(this);
			options.get(1).setText("Soldier");
			options.get(1).setToolTip(Monolog.createToolTip(200, 100, Soldier.produceInformation));
			options.add(new Option(Game.getImageManager().getImage(
					"M_MainBuilding_" + (level + 1) + ".png"), false, false));
			options.get(2).addActionListener(this);
			options.get(2).setText("Upgrade");
		} else if (level == 3) {
			options.add(new Option(Game.getImageManager().getImage(
					"Soldier.png"), false, false));
			options.get(1).addActionListener(this);
			options.get(1).setText("Soldier");
			options.get(1).setToolTip(Monolog.createToolTip(200, 100, Soldier.produceInformation));
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
		case "Soldier":
			m_curtask = task.t_spawn;
			toSpawn = 2;
			break;
		case "Upgrade":
			System.out.println("Upgrade");
			m_curtask = task.t_upgrading;
			Game.getImageManager().getImage(img_name, size, size);
			img_name = "M_MainBuilding_" + (level + 1) + ".png";
			setMaxLife(getMaxLife() * 2);
			setStatus(STATUS_BUIDLING);
			break;
		default:
			m_curtask = task.t_none;
			System.out.println("Action: " + e.getActionCommand());
		}
	}

	@Override
	/*	public Rectangle getImageBounds() {
			super.getImageBounds();
			imgrg.width = size;
			imgrg.height = size;
			return imgrg;
		}*/
	/*************** Task Section ****************/
	public void taskSpawn(Screen screen) {
		GameScreen gamescreen = (GameScreen) screen; // needed for
														// pointToMapConst
		if (toSpawn == 1) {
			if (!Player.getPlayer(getOwner()).hasResources(Worker.res)) {
				System.out.println("Get at least 50 Wood and 25 Stone to Spawn a Unit!");
				System.out.println("This is an example of using Resources to spawn Units.");

				m_curtask = task.t_none;
				return;
			} else {
				Player.getPlayer(getOwner()).consumeResources(Worker.res);
			}
		} else if (toSpawn == 2) {
			if (!Player.getPlayer(getOwner()).hasResources(Soldier.res)) {
				System.out.println("Get at least 100 Wood and 75 Stone and 50 Iron to Spawn a Unit!");
				System.out.println("This is an example of using Resources to spawn Units.");

				m_curtask = task.t_none;
				return;
			} else {
				Player.getPlayer(getOwner()).consumeResources(Soldier.res);
			}
		}

		loop: for (int j = 0; j < 6; j++) { // Limited to 36 tries
			for (int i = 0; i < 6; i++) {
				// I havespace to spawn him here?
				int x = getX() - 100 + 25 * i;
				int y = getY() + 25 * (j + 1);
				if (!gamescreen.getEntityWithMap().containsValue(Screen.pointToMapConst(x, y))) {

					if (toSpawn == 1) {
						Worker work = new Worker(x, y, getOwner());
						gamescreen.getEntityWithMap().put(work, Screen.pointToMapConst(x, y));
					} else if (toSpawn == 2) {
						Soldier sol = new Soldier(x, y, getOwner());
						gamescreen.getEntityWithMap().put(sol, Screen.pointToMapConst(x, y));
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
			setLife(getLife() + 10);
		}

	}

	@Override
	public void changeStatus(int status) {
		if (status == STATUS_FINISHED && current_status != status) {
			Player.getPlayer(owner).addToUnitLimit(FOOD);
		}
		current_status = status;
	}

	@Override
	public boolean die() {
		Player.getPlayer(getOwner()).freeSpace(FOOD);
		return l_Entities.remove(this);// This should be dead now!
	}

	public static String description() {
		return "Main Buidling";
	}
}
