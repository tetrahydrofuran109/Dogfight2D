package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Property.Map;

/**
 * This class is use to construct and translate the xml file for class Map
 * @author WangShuzheng
 */

@XmlRootElement(name = "MapData")
public class MapData {
	
	private Map data;

	@XmlElement(name = "Map")
	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

}
