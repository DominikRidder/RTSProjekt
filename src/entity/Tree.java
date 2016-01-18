package entity;

public class Tree extends Materials {

	/**
	 * Creates a Tree of the type 'type'
	 * 
	 * @param x
	 * @param y
	 * @param img_name
	 * @param owner
	 * 
	 */

	public Tree(int x, int y, int rad, String imgname, int maxLife, int res) {
		super(x, y, rad, imgname, maxLife, res);// Trees have no owner, and
		// no aura
	}

}
