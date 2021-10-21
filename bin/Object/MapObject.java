package Object;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import Data.MapData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is not extends the object class
 * it helps GameWindowController to load Background objects
 * @author WangShuzheng
 */

public class MapObject {
	
	/**
	 * attributes
	 */
	
	private String mapName;
	
	private ArrayList<String> ObjectNameList = new ArrayList<String>();
	private ArrayList<Double> ObjectXList = new ArrayList<Double>();
	private ArrayList<Double> ObjectYList = new ArrayList<Double>();
	
	private double MapSize;
	
	private ArrayList<ImageView> ViewList = new ArrayList<ImageView>();

	/**
	 * constructor
	 * @param name name of map
	 */
	
	public MapObject(String name) {
		// TODO Auto-generated constructor stub
		this.mapName = name;
		  try {
				JAXBContext context = JAXBContext
				        .newInstance(MapData.class);
				Unmarshaller um = context.createUnmarshaller();
				MapData data = (MapData) um.unmarshal(new File("src/Data/Map/"+this.mapName+".xml"));
				this.ObjectXList = data.getData().getObjectXList();
				this.ObjectYList = data.getData().getObjectYList();
				this.ObjectNameList = data.getData().getObjectNameList();
				this.MapSize = data.getData().getMapSize();
				
				for(int i = 0;i<ObjectNameList.size();i++)
				{
					Image Ins = new Image(this.ObjectNameList.get(i));
					ViewList.add(new ImageView(Ins));
					ViewList.get(i).setVisible(false);
				}
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public ArrayList<ImageView> getViewList() {
		return ViewList;
	}

	public void setViewList(ArrayList<ImageView> viewList) {
		ViewList = viewList;
	}

	public double getMapSize() {
		return MapSize;
	}

	public void setMapSize(double mapSize) {
		MapSize = mapSize;
	}

	public ArrayList<String> getObjectNameList() {
		return ObjectNameList;
	}

	public void setObjectNameList(ArrayList<String> objectNameList) {
		ObjectNameList = objectNameList;
	}

	public ArrayList<Double> getObjectXList() {
		return ObjectXList;
	}

	public void setObjectXList(ArrayList<Double> objectXList) {
		ObjectXList = objectXList;
	}

	public ArrayList<Double> getObjectYList() {
		return ObjectYList;
	}

	public void setObjectYList(ArrayList<Double> objectYList) {
		ObjectYList = objectYList;
	}
}
