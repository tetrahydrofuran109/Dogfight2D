package Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Property.LoadChoice;

/**
 * This class is use to construct and translate the xml file for class LoadChoice
 * @author WangShuzheng
 */

@XmlRootElement(name = "LoadChoiceData")
public class LoadChoiceData {
	
	private LoadChoice data;

	@XmlElement(name = "LoadChoice")
	public LoadChoice getData() {
		return data;
	}

	public void setData(LoadChoice data) {
		this.data = data;
	}
}
