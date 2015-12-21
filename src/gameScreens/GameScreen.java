package gameScreens;

import dataManagement.MapManager;
import entity.AbstractEntity;
import entity.OrkTest;
import entity.Tree;
import gameEngine.Game;
import gameEngine.KeyboardListener;
import gameEngine.MousepadListener;
import gameEngine.Player;
import gameEngine.Screen;
import gameEngine.ScreenFactory;
import gui.Button;
import gui.CompactLayout;
import gui.EntityLayout;
import gui.Field;
import gui.Label;
import gui.LayerPanel;
import gui.Panel;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import Utilitys.Point;

public class GameScreen extends Screen implements ActionListener {

	// private final List<AbstractEntity> entitys = new
	// LinkedList<AbstractEntity>();
	private final HashMap<AbstractEntity, Point> entitysOnMap = new HashMap<AbstractEntity, Point>();

	private EntityLayout entitytodraw;
	
	private int viewX, viewY;

	
	private Panel hud;
	
	private int lastRightClick = 0;
	private int lastRightClickX = 0;
	private int lastRightClickY = 0;

	private int savedX;
	private int savedY;
	private int savedMarkX;
	private int savedMarkY;
	
	private Label wood;

	private int mapwidth = 2000;
	private int mapheight = 2000;

	public GameScreen(ScreenFactory screenFactory) {
		super(screenFactory);
	}

	@Override
	public void onCreate() {
		List<AbstractEntity> entitys = AbstractEntity.getEntities();
		Random rnd = new Random();
		System.out.println("Main Creating!");
		
		LayerPanel pWorld = new LayerPanel(0,0, 50*16, 50*16);
		MapManager.loadMap(pWorld, null); // null to call JFileChooser
		pWorld.setActualLayer(0);
		mapwidth = pWorld.getWidth();
		mapheight = pWorld.getHeight();
		
		Field.drawBorder = false;
		
		entitytodraw = new EntityLayout(getEntitys());
		pWorld.addLayoutat(3, entitytodraw);		
		addGuiElement(pWorld);
		
		
		Player hum_sp = new Player(0);
		Player comp_sp = new Player(1);
		
		for (int i = 0; i < 10; i++) {
			entitys.add(new OrkTest(rnd.nextInt(700) + 40,
					rnd.nextInt(500) + 40, rnd.nextInt(2)));// TODO get(i) funktion
													// vermeiden, weil
													// LinkedList
			entitys.add(new Tree(rnd.nextInt(700) + 40, rnd.nextInt(500) + 40,
					rnd.nextInt(2)));
			entitysOnMap.put(
					entitys.get(i),
					pointToMapConst(entitys.get(i).getX(), entitys.get(i)
							.getY()));
		}
		
		/*********** HUD ***********************/
		
		wood = new Label("Wood: "+hum_sp.getWood());
		wood.setX(this.getScreenFactory().getGame().getWindow()
				.getWidth() - 150);
		wood.setY(0);
		wood.setWidth(150);
		wood.setHeight(50);
		
		hud = new Panel(0,0, this.getScreenFactory().getGame().getWindow()
				.getWidth(), this.getScreenFactory().getGame().getWindow()
				.getHeight());
		hud.setLayout(new CompactLayout(hud));
		hud.addElement(wood);
		
		addGuiElement(hud);
	}

	@Override
	public void onUpdate() {
		int scrollspeed = Game.getSetting().getValueInt("cl_g_scrollspeed");
		KeyboardListener kbl = this.getScreenFactory().getGame().getKeyboardListener();
		if(kbl.isOnPress("btn_ESC"))//ESC button
		{
			Button b = new Button("ESC");
			b.addActionListener(this);
			b.callActions();
			return;
		}
		if(kbl.isKeyPressed(39))//i would let this unchangeable
		{
			viewX+= scrollspeed;
		}
		if(kbl.isKeyPressed(37))
		{
			viewX-= scrollspeed;
		}
		if(kbl.isKeyPressed(38))
		{
			viewY-= scrollspeed;
		}
		if(kbl.isKeyPressed(40))
		{
			viewY+= scrollspeed;
		}
		
		MousepadListener mpl = this.getScreenFactory().getGame().getMousepadListener();
		wood.setText("Wood: "+Player.getPlayer(0).getWood());
		
		
		
		if (mpl.getCurrentX() > getScreenFactory().getGame().getWindow()
				.getWidth() - 30
				|| mpl.getCurrentX() - 30 < 0) {
			viewX += mpl.getCurrentX() > getScreenFactory().getGame()
					.getWindow().getWidth() / 2 ? scrollspeed : -scrollspeed;
		}
		if (mpl.getCurrentY() > getScreenFactory().getGame().getWindow()
				.getHeight() - 60
				|| mpl.getCurrentY() - 30 < 0) {
			viewY += mpl.getCurrentY() > getScreenFactory().getGame()
					.getWindow().getWidth() / 2 ? scrollspeed : -scrollspeed;
		}
		
		int wwidth = getScreenFactory().getGame().getWindow().getWidth();
		int wheight = getScreenFactory().getGame().getWindow().getHeight();

		if (viewX > mapwidth - wwidth) {
			viewX = mapwidth - wwidth;
		} else if (viewX < 0) {
			viewX = 0;
		}
		if (viewY > mapheight - wheight) {
			viewY = mapheight - wheight;
		} else if (viewY < 0) {
			viewY = 0;
		}

		mpl.setX(mpl.getX() + viewX);
		mpl.setY(mpl.getY() + viewY);
		mpl.setMarkX(mpl.getMarkX() + viewX);
		mpl.setMarkY(mpl.getMarkY() + viewY);

		savedX = mpl.getX();
		savedY = mpl.getY();
		if (mpl.isDragging()) {
			savedMarkX = mpl.getMarkX();
			savedMarkY = mpl.getMarkY();
		} else {
			savedMarkX = savedX;
			savedMarkY = savedY;
		}

		super.onUpdate();

		Collections.sort(getEntitys());// In diese Zeile hab ich so viel
										// hirnschmalz verbraten
		for (int i = 0; i < getEntitys().size(); i++) {
			getEntitys().get(i).update(this);
		}
		for (int i = 0; i < getEntitys().size(); i++) {
			if (getEntitys().get(i).checkDeath())// Toete tote
				i--;// liste wird kleiner
		}
		for (Entry<AbstractEntity, Point> entry : entitysOnMap.entrySet()) {
			AbstractEntity key = entry.getKey();
			entry.setValue(pointToMapConst(key.getX(), key.getY()));
		}

		mpl.setX(mpl.getX() - viewX);
		mpl.setY(mpl.getY() - viewY);
		mpl.setMarkX(mpl.getMarkX() - viewX);
		mpl.setMarkY(mpl.getMarkY() - viewY);
		
		checkWin();
	}
	
	@Override
	public void onDraw(Graphics2D g2d) {
		MousepadListener mpl = this.getScreenFactory().getGame()
				.getMousepadListener();
		
		
		AffineTransform transform = new AffineTransform();
		transform.translate(-viewX, -viewY);
		g2d.transform(transform);
		
		if (hud != null){
			hud.setX(viewX);
			hud.setY(viewY);
			hud.repack();
		}
		/*
		 * for (AbstractEntity e : AbstractEntity.getEntities()) {//linkedList
		 * performance plus e.draw(g2d); }
		 */
		
		super.onDraw(g2d);
		
		if (mpl.isDragging()) {
			if (savedMarkX < savedX) {
				setX(savedMarkX);
			} else {
				setX(savedX);
			}
			if (savedMarkY < savedY) {
				setY(savedMarkY);
			} else {
				setY(savedY);
			}
			setW(Math.abs(savedX - savedMarkX));
			setH(Math.abs(savedY - savedMarkY));
			drawDraggingZone(g2d);
		}
		if (mpl.isRightClicked()) {
			lastRightClick = 100;
			lastRightClickX = savedX;
			lastRightClickY = savedY;
		}
		if (lastRightClick != 0) {
			drawRightClick(g2d, lastRightClickX, lastRightClickY);
			lastRightClick--;
		}
		try {
			g2d.transform(transform.createInverse());//transform back to normal
		} catch (NoninvertibleTransformException e) {//THIS WILL NEVER NEVER NEVER ... happen!
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void drawRightClick(Graphics2D g2d, int x, int y) {
		g2d.drawLine(x - (int) (6 * lastRightClick / 100.), y
				- (int) (3 * lastRightClick / 100.), x
				+ (int) (6 * lastRightClick / 100.), y
				+ (int) (3 * lastRightClick / 100.));
		g2d.drawLine(x + (int) (6 * lastRightClick / 100.), y
				- (int) (3 * lastRightClick / 100.), x
				- (int) (6 * lastRightClick / 100.), y
				+ (int) (3 * lastRightClick / 100.));
	}

	public void drawDraggingZone(Graphics2D g2d) {
		if (this.getScreenFactory().getGame().getMousepadListener()
				.isDragging()
				&& this.getScreenFactory().getGame().getMousepadListener()
						.isLeftClicked()) {
			if (this.getScreenFactory().getGame().getMousepadListener()
					.getMarkX() != -1
					&& this.getScreenFactory().getGame().getMousepadListener()
							.getMarkY() != -1) {
				g2d.drawRect(getX(), getY(), getW(), getH());
			}
		}
	}

	@Override
	public List<AbstractEntity> getEntitys() {
		// return entitys;
		return AbstractEntity.getEntities();
	}

	public HashMap<AbstractEntity, Point> getEntityWithMap() {
		return entitysOnMap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) { // name of the button
		case "ESC":
			this.getScreenFactory().createScreen(new SettingsScreen(this.getScreenFactory(), this));
			break;
		default:
			System.out.println("Unknown ActionEvent: " + e.getActionCommand());
			break;
		}
	}

	public void checkWin() {
		int own = -1;
		int next = -1;
		for (AbstractEntity ent : this.getEntitys()) {
			next = ent.getOwner();
			if (next != -1){
				if (own == -1){
					own = next;
				}else if (next != own){
					return;
				}
			}
		}
		if (own != -1){
			Player.dropAllPlayer(); // as long we don't have a Screen for stats
			this.getScreenFactory().createScreen(new MainScreen(this.getScreenFactory()));
		}
	}
}
