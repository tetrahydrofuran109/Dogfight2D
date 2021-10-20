package Property;

import java.util.ArrayList;

/**
 * This class defines the list of load carried by an object
 * the data is stored into a xml file
 * @author WangShuzheng
 */

public class LoadChoice {

	/**
	 * Attributes
	 */
	
	private ArrayList<String> LoadName = new ArrayList<String>();
	private ArrayList<Double> LoadLocationX = new ArrayList<Double>();
	private ArrayList<Double> LoadLocationY = new ArrayList<Double>();
	private ArrayList<Double> PylonIndex = new ArrayList<Double>();
	
	public ArrayList<Double> getPylonIndex() {
		return PylonIndex;
	}

	public void setPylonIndex(ArrayList<Double> pylonIndex) {
		PylonIndex = pylonIndex;
	}

	public ArrayList<String> getLoadName() {
		return LoadName;
	}

	public void setLoadName(ArrayList<String> loadName) {
		LoadName = loadName;
	}

	public ArrayList<Double> getLoadLocationX() {
		return LoadLocationX;
	}

	public void setLoadLocationX(ArrayList<Double> loadLocationX) {
		LoadLocationX = loadLocationX;
	}

	public ArrayList<Double> getLoadLocationY() {
		return LoadLocationY;
	}

	public void setLoadLocationY(ArrayList<Double> loadLocationY) {
		LoadLocationY = loadLocationY;
	}
}
