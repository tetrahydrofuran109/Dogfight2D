package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Property.Helmet;

/**
 * This class is use to construct and translate the xml file for class Helmet
 * @author WangShuzheng
 */

@XmlRootElement(name = "HelmetData")
public class HelmetData {
	
	private Helmet data;

	@XmlElement(name = "Helmet")
	public Helmet getData() {
		return data;
	}

	public void setData(Helmet data) {
		this.data = data;
	}
}
