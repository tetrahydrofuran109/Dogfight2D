package Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import Data.SettingData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller class for the StartWindow
 * includes the ChoiceBoxs of faction, aircraft and load
 * @author WangShuzheng
 */

public class StartWindowController implements Initializable  {

	/**
	 * Start button
	 */
	
	@FXML
	private Button Start;
	
	/**
	 * TextFields and choiceBoxs used for Setting Player's aircraft
	 */
	
	@FXML
	private TextField myAlt;
	
	@FXML
	private TextField mySpeed;
	
	@FXML
	private ChoiceBox<Integer> myFuel;
	
	@FXML
	private ChoiceBox<Integer> myThrottle;	
	
	@FXML
	private ChoiceBox<String> myFaction;

	@FXML
	private ChoiceBox<String> myPlane;
	
	@FXML
	private ChoiceBox<String> LoadChoice;
	
	@FXML
	private ChoiceBox<Integer> number;
	
	/**
	 * TextFields and choiceBoxs used for Setting Enemy's aircraft
	 */
	
	@FXML
	private TextField myAlt1;
	
	@FXML
	private TextField mySpeed1;
	
	@FXML
	private ChoiceBox<Integer> myFuel1;
	
	@FXML
	private ChoiceBox<Integer> myThrottle1;	

	@FXML
	private ChoiceBox<String> myFaction1;
	
	@FXML
	private ChoiceBox<String> myPlane1;
	
	@FXML
	private ChoiceBox<String> LoadChoice1;
	
	@FXML
	private ChoiceBox<Integer> number1;
	
	/**
	 * used for Setting map
	 */
	
	@FXML
	private ChoiceBox<String> myMap;
	
	/**
	 * attributes used to store settings and pass it to GameWindowController
	 */
	
	public static double Speed0;
	
	public static double alt0;
	
	public static int Throttle0;
	
	public static double Fuel0;
	
	public static String Plane0;
	
	public static String loadChoice;
	
	public static String myMap0;
	
	public static int Number0;
	
	public static double Speed1;
	
	public static double alt1;
	
	public static int Throttle1;
	
	public static double Fuel1;
	
	public static String Plane1;
	
	public static String loadChoice1;
	
	public static int Number1;
	
   /**
	* Initialize the window
	* @param arg0 arg0 in type URL 
	* @param arg1 arg1 in type ResourceBundle
	*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	   /**
		* Read the Setting.xml file to get the Keyboard and Audio settings
		*/
        try {
			JAXBContext context = JAXBContext
			        .newInstance(SettingData.class);
			Unmarshaller um = context.createUnmarshaller();
			SettingData data = (SettingData) um.unmarshal(new File("src/Data/Setting/Setting.xml"));
			SettingWindowController.Setting = data.getData();
				
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 	   /**
 		* Initialize the Default Settings, add items for choiceBoxs
 		*/
        
		myFuel.getItems().add(25);
		myFuel.getItems().add(50);
		myFuel.getItems().add(75);
		myFuel.getItems().add(100);
		myThrottle.getItems().add(0);
		myThrottle.getItems().add(25);
		myThrottle.getItems().add(50);
		myThrottle.getItems().add(75);
		myThrottle.getItems().add(100);
		myFaction.getItems().add("USA");
		myFaction.getItems().add("Russia");
		myFaction.getItems().add("China");
		myFaction.getItems().add("Europe");
		myPlane.getItems().add("F-86F-25");
		LoadChoice.getItems().add("Clean");
		number.getItems().add(1);
		number.getItems().add(2);
		number.getItems().add(3);
		number.getItems().add(4);
		
		myFuel1.getItems().add(25);
		myFuel1.getItems().add(50);
		myFuel1.getItems().add(75);
		myFuel1.getItems().add(100);
		myThrottle1.getItems().add(0);
		myThrottle1.getItems().add(25);
		myThrottle1.getItems().add(50);
		myThrottle1.getItems().add(75);
		myThrottle1.getItems().add(100);
		myFaction1.getItems().add("USA");
		myFaction1.getItems().add("Russia");
		myFaction1.getItems().add("China");
		myFaction1.getItems().add("Europe");
		myPlane1.getItems().add("F-86F-25");
		LoadChoice1.getItems().add("Clean");
		number1.getItems().add(0);
		number1.getItems().add(1);
		number1.getItems().add(2);
		number1.getItems().add(3);
		number1.getItems().add(4);
		
		myMap.getItems().add("Korea");
		
		myAlt.setText("0");
		mySpeed.setText("0");
		myFuel.setValue(50);
		myThrottle.setValue(0);
		myFaction.setValue("USA");
		myPlane.setValue("F-86F-25");
		LoadChoice.setValue("Clean");
		number.setValue(1);
		
		myAlt1.setText("0");
		mySpeed1.setText("0");
		myFuel1.setValue(50);
		myThrottle1.setValue(0);
		myFaction1.setValue("USA");
		myPlane1.setValue("F-86F-25");
		LoadChoice1.setValue("Clean");
		number1.setValue(1);
		
		myMap.setValue("Korea");
	}
	
	   public void UpdateAircraft(MouseEvent event)
	   {
		   /*
		   if(myFaction.getValue().contains("USA"))
		   {
			   myPlane.setValue("F-86F-25");
		   }
		   else if(myFaction.getValue().contains("Russia"))
		   {
			   myPlane.setValue("MiG-17");
		   }
		   else if(myFaction.getValue().contains("China"))
		   {
			   myPlane.setValue("MiG-15bis");
		   }*/
	   }
	   
	   public void UpdateAircraft1(MouseEvent event)
	   {
		   /*
		   if(myFaction1.getValue().contains("USA"))
		   {
			   myPlane1.setValue("F-86F-25");
		   }
		   else if(myFaction1.getValue().contains("Russia"))
		   {
			   myPlane1.setValue("MiG-17");
		   }
		   else if(myFaction1.getValue().contains("China"))
		   {
			   myPlane1.setValue("MiG-15bis");
		   }*/
	   }
	
 	   /**
 		* Method to Read all files from a file folder
 		* @param filepath path of the folder
 		* @return the list of file names in this folder
 		*/
	   public static ArrayList<String> getFiles(String filepath)
	   {
		   ArrayList<String> files = new ArrayList<String>();
		   File file = new File(filepath);
		   File[] tempLists = file.listFiles();
		   for(int i=0; i<tempLists.length;i++)
		   {
			   if(tempLists[i].isFile())
			   {
				   files.add(tempLists[i].toString());
			   }
		   }
		   return files;
	   }
	   
 	  /**
 	   * Initialize the allowable loads for aircraft choosen
 	   * @param LoadChoice ChoiceBox of the load
 	   * @param myPlane ChoiceBox of the aircraft
 	   */
	   public void LoadChoice(ChoiceBox<String> LoadChoice, ChoiceBox<String> myPlane)
	   {
		   LoadChoice.getItems().clear();
		   LoadChoice.getItems().add("Clean");
		   ArrayList<String> choices = getFiles("src/Data/Aircraft/"+myPlane.getValue()+"/");
		   for(int i=0;i<choices.size();i++)
		   {
			   LoadChoice.getItems().add(choices.get(i).substring(choices.get(i).lastIndexOf('\\')+1,choices.get(i).lastIndexOf(".")));
		   }  
	   }
	   
	  /**
	   * Initialize the allowable aircrafts for faction choosen
	   * @param myFaction ChoiceBox of the faction
	   * @param LoadChoice ChoiceBox of the load
	   * @param myPlane ChoiceBox of the aircraft
	   */
	   public void LoadAircrafts(ChoiceBox<String> myFaction, ChoiceBox<String> LoadChoice, ChoiceBox<String> myPlane)
	   {
		   LoadChoice.setValue("Clean");
		   if(myFaction.getValue().contains("USA"))
		   {
			    myPlane.getItems().clear();
			    myPlane.getItems().add("F-86F-25");
				myPlane.getItems().add("F-100C");
				myPlane.getItems().add("F-104C");
				myPlane.getItems().add("F-4B");
				myPlane.getItems().add("F-4J");
				myPlane.getItems().add("F-15C");
				myPlane.getItems().add("F-16A Block 15");
				myPlane.getItems().add("FA-18C");
		   }
		   else if(myFaction.getValue().contains("Russia"))
		   {
			    myPlane.getItems().clear();
				myPlane.getItems().add("MiG-17");
				myPlane.getItems().add("MiG-19S");
				myPlane.getItems().add("MiG-21F-13");
				myPlane.getItems().add("MiG-21bis");
				myPlane.getItems().add("MiG-23ML");
				myPlane.getItems().add("MiG-29");
				myPlane.getItems().add("Su-27");
				myPlane.getItems().add("Su-33");
		   }
		   else if(myFaction.getValue().contains("China"))
		   {
			    myPlane.getItems().clear();
				myPlane.getItems().add("MiG-15bis");
				myPlane.getItems().add("J-6");
				myPlane.getItems().add("J-7II");
				myPlane.getItems().add("J-8I");
				myPlane.getItems().add("J-8II");
				myPlane.getItems().add("J-10A");
				myPlane.getItems().add("Su-27SK");
		   }
		   if(myFaction.getValue().contains("Europe"))
		   {
			    myPlane.getItems().clear();
			    myPlane.getItems().add("Hunter F.6");
			    myPlane.getItems().add("Mirage IIIC");
			    myPlane.getItems().add("Mirage IIIE");
			    myPlane.getItems().add("Mirage 2000C");
			    myPlane.getItems().add("J35D");
			    myPlane.getItems().add("J35F");
			    myPlane.getItems().add("JA37");
		   }
	   }
	   
	  /**
	   * Initialize the allowable aircrafts for player
	   * @param event MouseEvent
	   */
	   public void UpdateChoice(MouseEvent event)
	   {
		   LoadAircrafts(myFaction, LoadChoice, myPlane);
	   }
	   
      /**
	   * Initialize the allowable aircrafts for enemy
	   * @param event MouseEvent
	   */
	   public void UpdateChoice1(MouseEvent event)
	   {
		   LoadAircrafts(myFaction1, LoadChoice1, myPlane1);
	   }
	
	  /**
	   * Initialize the allowable loads for player
	   * @param event MouseEvent
	   */
	   public void LoadChoice(MouseEvent event)
	   {
		   LoadChoice(LoadChoice, myPlane);
	   }
	   
	  /**
	   * Initialize the allowable loads for enemy
	   * @param event MouseEvent
	   */	   
	   public void LoadChoice1(MouseEvent event)
	   {
		   LoadChoice(LoadChoice1, myPlane1);
	   }
	
	  /**
	   * Start action, open the game window
	   * @param event event in type ActionEvent
	   */
	   public void Start(ActionEvent event) {
		   if(LoadChoice.getValue()==null)
		   {
			   LoadChoice.setValue("Clean");
		   }
		   
		   if(LoadChoice1.getValue()==null)
		   {
			   LoadChoice1.setValue("Clean");
		   }
		   
		   Speed0 = Double.parseDouble(mySpeed.getText());
		   alt0 = Double.parseDouble(myAlt.getText());
		   Throttle0 = myThrottle.getValue();
		   Fuel0 = myFuel.getValue();
		   Plane0 = myPlane.getValue();
		   myMap0 = myMap.getValue();
		   Number0 = number.getValue();
		   loadChoice = LoadChoice.getValue();
		   
		   Speed1 = Double.parseDouble(mySpeed1.getText());
		   alt1 = Double.parseDouble(myAlt1.getText());
		   Throttle1 = myThrottle1.getValue();
		   Fuel1 = myFuel1.getValue();
		   Plane1 = myPlane1.getValue();
		   Number1 = number1.getValue();
		   loadChoice1 = LoadChoice1.getValue();
		   
		    try {
		        Stage dialogStage = new Stage();
	            Parent root = FXMLLoader.load(getClass().getResource("/View/GameWindow.fxml"));
	            dialogStage.setTitle("2D Dogfight");
	            dialogStage.setScene(new Scene(root));
	            dialogStage.show();
	            
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	   }
	   
	   public void Setting(ActionEvent event) {
		    try {
		        Stage dialogStage = new Stage();
	            Parent root = FXMLLoader.load(getClass().getResource("/View/SettingWindow.fxml"));
	            dialogStage.setTitle("Setting");
	            dialogStage.setScene(new Scene(root));
	            dialogStage.show();
	            
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	   }
}
