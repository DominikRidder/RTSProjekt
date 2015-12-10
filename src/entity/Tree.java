package entity;

import gameEngine.Player;
import gameEngine.Screen;

import java.awt.Graphics2D;

public class Tree extends AbstractEntity {

	private final int m_type;
	private float m_hit;
	
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
	public void drawLifeBar(Graphics2D g2d)
	{
		if(getMaxLife() != getLife())
			super.drawLifeBar(g2d);
	}
	
	@Override
	public void drawImage(Graphics2D g2d)
	{
		g2d.drawImage(getImg(), imgrg.x+(int)m_hit, imgrg.y, null);
		if(m_hit >= 0)
		{
			m_hit-=0.1f;
		}
	}
	
	@Override
	public boolean takeDamage(int dmg, AbstractEntity dmgdealer) {
		if(m_hit <= 0)
			m_hit+=4;
		
		int before = this.getLife();
		boolean isalive =  super.takeDamage(dmg, dmgdealer);
		
		if (dmgdealer.getOwner() != -1){
			Player togift = Player.getPlayer(dmgdealer.getOwner());
			togift.setWood(togift.getWood()+before-getLife());
		}
		
		return isalive;
	}
	
	@Override
	public void update() {

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
