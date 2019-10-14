package model.logic;

import java.util.List;

public class Geometry {
	
	private String type;
	
	private List<List<List<List<Double>>>> coordinates;
	

	
	
	public Geometry(String type, List<List<List<List<Double>>>> coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public List<List<List<List<Double>>>> getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(List<List<List<List<Double>>>> coordinates) {
		this.coordinates = coordinates;
	}
	
}