package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Property.Engine;

/**
 * This class is use to construct and translate the xml file for class Engine
 * @author WangShuzheng
 */

@XmlRootElement(name = "EngineData")
public class EngineData {
	
	private Engine data;

	@XmlElement(name = "Engine")
	public Engine getData() {
		return data;
	}

	public void setData(Engine data) {
		this.data = data;
	}
}
