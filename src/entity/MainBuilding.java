package entity;

import gameEngine.Game;
import gameEngine.MousepadListener;
import gameEngine.Screen;
import gui.Button;
import gui.EntityOptions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainBuilding extends Building implements ActionListener{

	private boolean wasnotmarked = true;
	private boolean marked;
	private boolean menueisopen;
	private final int size = 200;
	
	public MainBuilding(int x, int y, int rad, String img_name, int owner) {
		super(x, y, rad, img_name, owner);
		setMaxLife(200);
		setLife(200);
		Game.getImageManager().getImage(img_name, size, size);
		this.img_name = img_name+";"+size+";"+size;
		// TODO Auto-generated constructor stub
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
			
			if (menueisopen && mpl.isLeftClicked() && !EntityOptions.singleton.isMarked()) {
				EntityOptions.singleton.setOptions(null, null);
			}
		}
	}
	
	public void openMenue(){
		ArrayList<Button> options = new ArrayList<Button>();
		
		for (int i=0; i<4; i++){
			options.add(new Button(Game.getImageManager().getImage("tbutton_none.png"), false, false));
			options.get(i).setSize(30, 30);
			options.get(i).addActionListener(this);
			options.get(i).setText(""+i);
		}
		
		EntityOptions.singleton.setOptions(options, this);
		
		menueisopen = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		default:
			System.out.println("Action: "+e.getActionCommand());
		}
	}
}
