package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Property.AircraftPerformance;

/**
 * This class is use to construct and translate the xml file for class AircraftPerformance
 * @author WangShuzheng
 */

@XmlRootElement(name = "ObjectData")
public class AircraftData {
	
	private AircraftPerformance data;

	@XmlElement(name = "Aircraft")
	public AircraftPerformance getData() {
		return data;
	}

	public void setData(AircraftPerformance data) {
		this.data = data;
	}
}