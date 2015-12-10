package gameEngine;

import java.util.HashMap;

public class Mission {
	private String Name;
	private String description;
	private String iconlocation;
	private String maplocation;
	
	private boolean accomplished;
	
	
	public String getName() {
		return Name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getIconLocation() {
		return iconlocation;
	}

	public String getMapLocation() {
		return maplocation;
	}
	
	public boolean isAccomplished(){
		return accomplished;
	}
	
	public void setAccomplished(boolean didit){
		accomplished = didit;
	}
	
	public Mission(String name, String descr, String iconl, String mapl) {
		this.Name = name;
		this.description = descr;
		this.iconlocation = iconl;
		this.maplocation = mapl;
	}

	public Mission(HashMap<String, String> info) {
		this.Name = info.get("Name");
		this.description = info.get("Description");
		this.iconlocation = info.get("IconLocation");
		this.maplocation = info.get("MapLocation");
	}
}
