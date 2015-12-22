package entity;

import gameEngine.Game;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gui.Button;
import gui.EntityOptions;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class MainBuilding extends Building{

	private boolean wasnotmarked = true;
	private boolean marked;
	private boolean menueisopen;
	
	public MainBuilding(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
		// TODO Auto-generated constructor stub
	}

	public void drawImage(Graphics2D g2d)
	{
		//this may gets overwritten in some classes (like tree)
		g2d.drawImage(getImg(), getX(), getY(), 200, 200, null);
	}
	
	@Override
	public void update(Screen screen) {
		super.update(screen);
		
		MousepadListener mpl = screen.getScreenFactory().getGame().getMousepadListener();
		if (mpl.isLeftClicked()) {
			if (mpl.isDragging()) {
//				if (screen.getDraggingZone().intersects(getBounds())) {
//					marked = true;
//				} else {
//					marked = false;
//				}
			} else {
				if ((mpl.getX() >= getX() && mpl.getX() <= getX() + /*getImageBounds().getWidth()*/200) && (mpl.getY() >= getY() && mpl.getY() <= getY() + /*getImageBounds().getHeight()*/200)) { // 50 dynamisch machen
					marked = true;
				} else {
					marked = false;
				}
			}
		}
		
		if (marked && wasnotmarked) {
			openMenue();
			wasnotmarked = false;
		}else if (!marked) {
			wasnotmarked = true;
			
			if (menueisopen && mpl.isLeftClicked()) {
				EntityOptions.singleton.setOptions(null, null);
			}
		}
	}
	
	public void openMenue(){
		ArrayList<Button> options = new ArrayList<Button>();
		
		for (int i=0; i<4; i++){
			options.add(new Button(getX()+i*50, getY()-50, Game.getImageManager().getImage("notfound.png")));
			options.get(i).setSize(30, 30);
		}
		
		EntityOptions.singleton.setOptions(options, this);
		
		menueisopen = true;
	}
}
