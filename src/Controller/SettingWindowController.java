package Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import Data.SettingData;
import Property.ControlSettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller class for the settings
 * includes settings of keyCode and audio
 * @author WangShuzheng
 */

public class SettingWindowController implements Initializable   {

	/**
	 * attributes for KeyCode settings
	 */
	
	@FXML
	private Button Pitch;
	
	@FXML
	private Button Dive;
	
	@FXML
	private Button Trim;
	
	@FXML
	private Button Firing;
	
	@FXML
	private Button Up;
	
	@FXML
	private Button Down;
	
	@FXML
	private Button Launch;
	
	@FXML
	private Button Radar;
	
	@FXML
	private Button Infrared;
	
	@FXML
	private Button Weapon;
	
	@FXML
	private Button Airbrake;
	
	@FXML
	private Button AIML;
	
	@FXML
	private Button AIMR;
	
	@FXML
	private Button CMDU;
	
	@FXML
	private Button CMDD;
	
	@FXML
	private Button Cancel;
	
	@FXML
	private Button Apply;
	
	@FXML
	private Text Key;
	
	/**
	 * attributes for Audio settings
	 */
	
	@FXML
	private CheckBox EngineAudio;
	
	@FXML
	private CheckBox GunAudio;
	
	@FXML
	private CheckBox LaunchAudio;
	
	@FXML
	private CheckBox EffectAudio;
	
	@FXML	
	private CheckBox WarningAudio;
	
	@FXML
	private Slider SliderEngine;
	
	@FXML
	private Slider SliderGun;
	
	@FXML
	private Slider SliderLaunch;
	
	@FXML
	private Slider SliderEffect;
	
	@FXML	
	private Slider SliderWarning;
	
	/**
	 * Construct a setting class
	 */
	
    public static ControlSettings Setting = new ControlSettings();
	
    /**
 	* Initialize the current settings
 	* @param arg0 arg0 in type URL 
 	* @param arg1 arg1 in type ResourceBundle
 	*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	        Airbrake.setText(Setting.getAirbrakeCode());;
	        Weapon.setText(Setting.getChangeWeaponCode());
	        Dive.setText(Setting.getDiveCode());
	        Firing.setText(Setting.getFiringCode());
	        Launch.setText(Setting.getLaunchCode());
	        Infrared.setText(Setting.getOpenInfraredModeCode());
	        Radar.setText(Setting.getOpenRadarModeCode());
	        Pitch.setText(Setting.getPitchCode());
	        Down.setText(Setting.getThrottleDownCode());
	        Up.setText(Setting.getThrottleUPCode());
	        Trim.setText(Setting.getTrimCode());
	        AIML.setText(Setting.getHelmetAimingCodeUP());
	        AIMR.setText(Setting.getHelmetAimingCodeDown());
	        CMDU.setText(Setting.getCommandCodeUP());
	        CMDD.setText(Setting.getCommandCodeDown());
	        
	        EffectAudio.setSelected(Setting.isHaveEffectAudio());;
	        EngineAudio.setSelected(Setting.isHaveEngineAudio());
	        GunAudio.setSelected(Setting.isHaveGunAudio());
	        LaunchAudio.setSelected(Setting.isHaveLaunchAudio());
	        WarningAudio.setSelected(Setting.isHaveWarningAudio());
	        
	        SliderEffect.setValue(Setting.getEffectAudioVolume());
	        SliderEngine.setValue(Setting.getEngineAudioVolume());
	        SliderGun.setValue(Setting.getGunAudioVolume());
	        SliderLaunch.setValue(Setting.getLaunchAudioVolume());
	        SliderWarning.setValue(Setting.getWarningAudioVolume());
	}
	
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void Pitch(ActionEvent event) {
		   Key.setVisible(true);
		   Pitch.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
                   Pitch.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void Dive(ActionEvent event) {
		   Key.setVisible(true);
		   Dive.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Dive.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */	   
	   public void Trim(ActionEvent event) {
		   Key.setVisible(true);
		   Trim.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Trim.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }

	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */		   
	   public void Firing(ActionEvent event) {
		   Key.setVisible(true);
		   Firing.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Firing.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void Up(ActionEvent event) {
		   Key.setVisible(true);
		   Up.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Up.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void Down(ActionEvent event) {
		   Key.setVisible(true);
		   Down.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Down.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */	   
	   public void Launch(ActionEvent event) {
		   Key.setVisible(true);
		   Launch.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Launch.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void Radar(ActionEvent event) {
		   Key.setVisible(true);
		   Radar.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Radar.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void Infrared(ActionEvent event) {
		   Key.setVisible(true);
		   Infrared.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Infrared.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void Weapon(ActionEvent event) {
		   Key.setVisible(true);
		   Weapon.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Weapon.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void Airbrake(ActionEvent event) {
		   Key.setVisible(true);
		   Airbrake.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   Airbrake.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
       * @param event event in type ActionEvent
	   */
	   public void AIML(ActionEvent event) {
		   Key.setVisible(true);
		   AIML.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   AIML.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void AIMR(ActionEvent event) {
		   Key.setVisible(true);
		   AIMR.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   AIMR.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void CMDU(ActionEvent event) {
		   Key.setVisible(true);
		   CMDU.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   CMDU.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Press a key to set the keyCode
	   * @param event event in type ActionEvent
	   */
	   public void CMDD(ActionEvent event) {
		   Key.setVisible(true);
		   CMDD.setOnKeyPressed(new EventHandler<KeyEvent>(){
				 public void handle(KeyEvent e)
				{
				   CMDD.setText(e.getCode().toString());
                   Key.setVisible(false);
				}
	        });
	   }
	   
	  /**
	   * Cancel the setting
	   * @param event event in type ActionEvent
	   */
	   public void Cancel(ActionEvent event) {
		   ((Stage)Cancel.getScene().getWindow()).close();
	   }
	   
	  /**
	   * Apply the setting and write it to the xml file
	   * @param event event in type ActionEvent
	   */   
	   public void Apply(ActionEvent event) {
    	   try {
      	        JAXBContext context = JAXBContext
       	                .newInstance(SettingData.class);
       	        Marshaller m = context.createMarshaller();
       	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

       	        // Wrapping our data.
       	        SettingData data = new SettingData();
       	        
       	        Setting.setAirbrakeCode(Airbrake.getText());
       	        Setting.setChangeWeaponCode(Weapon.getText());
       	        Setting.setDiveCode(Dive.getText());
       	        Setting.setFiringCode(Firing.getText());
       	        Setting.setLaunchCode(Launch.getText());
       	        Setting.setOpenInfraredModeCode(Infrared.getText());
       	        Setting.setOpenRadarModeCode(Radar.getText());
       	        Setting.setPitchCode(Pitch.getText());
       	        Setting.setThrottleDownCode(Down.getText());
       	        Setting.setThrottleUPCode(Up.getText());
       	        Setting.setTrimCode(Trim.getText());
       	        Setting.setHelmetAimingCodeUP(AIML.getText());
       	        Setting.setHelmetAimingCodeDown(AIMR.getText());
       	        Setting.setCommandCodeUP(CMDU.getText());
       	        Setting.setCommandCodeDown(CMDD.getText());
       	        
       	        Setting.setHaveEffectAudio(EffectAudio.isSelected());
       	        Setting.setHaveEngineAudio(EngineAudio.isSelected());
       	        Setting.setHaveGunAudio(GunAudio.isSelected());
       	        Setting.setHaveLaunchAudio(LaunchAudio.isSelected());
       	        Setting.setHaveWarningAudio(WarningAudio.isSelected());
       	        
       	        Setting.setEffectAudioVolume(SliderEffect.getValue());
       	        Setting.setEngineAudioVolume(SliderEngine.getValue());
       	        Setting.setGunAudioVolume(SliderGun.getValue());
       	        Setting.setLaunchAudioVolume(SliderLaunch.getValue());
       	        Setting.setWarningAudioVolume(SliderWarning.getValue());
       	        
       	        data.setData(Setting);

       	        // Marshalling and saving XML to the file.
       	        m.marshal(data, new File("src/Data/Setting/Setting.xml"));
    	   } catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   ((Stage)Apply.getScene().getWindow()).close();
	   }
}
