package entity;

public interface IHealth {
	boolean takeDamage(int dmg);
	boolean checkDeath();
	boolean die();
	void setMinDmg(int mindmg);
	void setMaxDmg(int maxdmg);
	void setLife(int life);
	int getMaxDmg();
	int getMinDmg();
	int getRandDmg();
	int getLife();
	boolean attack(int opponentID);
}
