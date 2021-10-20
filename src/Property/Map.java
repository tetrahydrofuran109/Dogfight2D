package Property;

import java.util.ArrayList;

/**
 * This class defines the list of background object of a map
 * the data is stored into a xml file
 * @author WangShuzheng
 */

public class Map {
	
	/**
	 * Attributes
	 */
	
	private ArrayList<String> ObjectNameList = new ArrayList<String>();
	private ArrayList<Double> ObjectXList = new ArrayList<Double>();
	private ArrayList<Double> ObjectYList = new ArrayList<Double>();
	
    private double MapSize;

	public double getMapSize() {
		return MapSize;
	}

	public void setMapSize(double mapSize) {
		MapSize = mapSize;
	}

	public ArrayList<String> getObjectNameList() {
		return ObjectNameList;
	}

	public void setObjectNameList(ArrayList<String> objectNameList) {
		ObjectNameList = objectNameList;
	}

	public ArrayList<Double> getObjectXList() {
		return ObjectXList;
	}

	public void setObjectXList(ArrayList<Double> objectXList) {
		ObjectXList = objectXList;
	}

	public ArrayList<Double> getObjectYList() {
		return ObjectYList;
	}

	public void setObjectYList(ArrayList<Double> objectYList) {
		ObjectYList = objectYList;
	}
}

