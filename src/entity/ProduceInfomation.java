package entity;

public class ProduceInfomation {
	public ResourceInfo res;
	public String description;
	
	public ProduceInfomation(ResourceInfo res, String description) {
		this.res = res;
		this.description = description;
	}

	public ResourceInfo getCost() {
		return res;
	}

	public String getDescription() {
		return description;
	}
}
