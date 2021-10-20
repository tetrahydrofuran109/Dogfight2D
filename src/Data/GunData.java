package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is use to construct and translate the xml file for class Gun
 * @author WangShuzheng
 */

import Property.Gun;

@XmlRootElement(name = "GunData")
public class GunData {
	
	private Gun data;

	@XmlElement(name = "Gun")
	public Gun getData() {
		return data;
	}

	public void setData(Gun data) {
		this.data = data;
	}
}
