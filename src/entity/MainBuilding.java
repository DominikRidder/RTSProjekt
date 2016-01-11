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

	public MainBuilding(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
		setMaxLife(200);
		setLife(200);
		Game.getImageManager().getImage(img_name, size, size);
		this.img_name = img_name + ";" + size + ";" + size;
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
				if ((mpl.getX() >= getX() && mpl.getX() <= getX() + /*
																	 * getImageBounds
																	 * (
																	 * ).getWidth
																	 * ()
																	 */200)
						&& (mpl.getY() >= getY() && mpl.getY() <= getY() + /*
																			 * getImageBounds
																			 * (
																			 * )
																			 * .
																			 * getHeight
																			 * (
																			 * )
																			 */200)) { // 50
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

			if (menueisopen && mpl.isLeftClicked()
					&& !EntityOptions.singleton.isMarked()) {
				EntityOptions.singleton.setOptions(null, null);
			}
		}
	}

	public void openMenue() {
		ArrayList<Button> options = new ArrayList<Button>();

		for (int i = 0; i < 4; i++) {
			options.add(new Button(Game.getImageManager().getImage("Orc.png"),
					false, false));
			options.get(i).addActionListener(this);
			options.get(i).setText("Spawn Ork");
		}

		EntityOptions.singleton.setOptions(options, this);

		menueisopen = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		default:
			m_curtask = task.t_spawn; // Test
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

		if (Player.getPlayer(getOwner()).getWood() < 20) {
			System.out.println("Get at least 20 Wood to Spawn a Unit!");
			System.out
					.println("This is an example of using Resources to spawn Units.");

			m_curtask = task.t_none;
			return;
		} else {
			Player.getPlayer(getOwner()).setWood(
					Player.getPlayer(getOwner()).getWood() - 20);
		}

		loop: for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) { // Limited to 50 trys

				if (!gamescreen.getEntityWithMap().containsValue( // I have
																	// space to
																	// spawn him
																	// here?
						Screen.pointToMapConst(getX() - 100 + 25 * i,
								getY() + 25*(j+1)))) {

					OrkTest ork = new OrkTest(getX() - 100 + 25 * i,
							getY() + 25*(j+1), // Spawn
							// a
							// new
							// Ork
							getOwner());

					gamescreen.getEntityWithMap().put(ork,
							Screen.pointToMapConst(ork.getX(), ork.getY()));
					break loop;
				}
			}
		}
		
		m_curtask = task.t_none; // Task complete
	}
}
