package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Property.Radar;

/**
 * This class is use to construct and translate the xml file for class Radar
 * @author WangShuzheng
 */

@XmlRootElement(name = "RadarData")
public class RadarData {
	
	private Radar data;

	@XmlElement(name = "Radar")
	public Radar getData() {
		return data;
	}

	public void setData(Radar data) {
		this.data = data;
	}
}
