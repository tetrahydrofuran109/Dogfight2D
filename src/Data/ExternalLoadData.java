package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Property.ExternalLoadPerformance;

/**
 * This class is use to construct and translate the xml file for class ExternalLoadPerformance
 * @author WangShuzheng
 */

@XmlRootElement(name = "ExternalLoadData")
public class ExternalLoadData {
	
	private ExternalLoadPerformance data;

	@XmlElement(name = "ExternalLoad")
	public ExternalLoadPerformance getData() {
		return data;
	}

	public void setData(ExternalLoadPerformance data) {
		this.data = data;
	}

}
