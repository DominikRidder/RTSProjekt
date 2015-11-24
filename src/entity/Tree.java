package entity;

import gameEngine.Screen;

public class Tree extends AbstractEntity {

	private final int m_type;
	
	private static final int rad = 16;//Hitboxsizes
	
	private static final int life = 20;
	/**
	 * Creates a Tree of the type 'type'
	 * @param x
	 * @param y
	 * @param img_name
	 * @param owner 
	 * @param type Type of the Tree (Tanne, apfel, todesbaum)
	 */
	public Tree(int x, int y, int type)//type equals teetrype
	{
		super(x, y, rad, "tree"+type+".png", -1);//Trees have no owner, and no aura
		m_type = type;
		setMaxLife(life);
		setLife(life);
	}
	
	public int getType()
	{
		return m_type;
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Screen sc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean attack(int opponentID) {
		// TODO Auto-generated method stub
		return false;//most Trees do not attack, maybe whe should do one xD
	}
}
