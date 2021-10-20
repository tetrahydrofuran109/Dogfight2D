package Property;

import java.util.ArrayList;

/**
 * This class defines the performance of a type of helmet
 * the data is stored into a xml file
 * @author WangShuzheng
 */

public class Helmet {
	
	/**
	 * Attributes of Helmet
	 */
	
	private double ViewField;
	private double TrackRate;
	
	private ArrayList<String> AllowedWeapon = new ArrayList<String>();

	public double getViewField() {
		return ViewField;
	}

	public void setViewField(double viewField) {
		ViewField = viewField;
	}

	public double getTrackRate() {
		return TrackRate;
	}

	public void setTrackRate(double trackRate) {
		TrackRate = trackRate;
	}

	public ArrayList<String> getAllowedWeapon() {
		return AllowedWeapon;
	}

	public void setAllowedWeapon(ArrayList<String> allowedWeapon) {
		AllowedWeapon = allowedWeapon;
	}
}
