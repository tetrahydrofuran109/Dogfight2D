package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Property.ControlSettings;

/**
 * This class is use to construct and translate the xml file for class ControlSettings
 * @author WangShuzheng
 */

@XmlRootElement(name = "SettingData")
public class SettingData {
	
	private ControlSettings data;

	@XmlElement(name = "Setting")
	public ControlSettings getData() {
		return data;
	}

	public void setData(ControlSettings data) {
		this.data = data;
	}
}
