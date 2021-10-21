package Controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import ControlLogic.AIControl;
import ControlLogic.PlayerControl;
import Object.Aircraft;
import Object.MapObject;
import Physics.DamageModel;
import Physics.Vector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Controller class for the Construction of a game
 * includes indicators and visualization of backgroud and aircrafts
 * @author WangShuzheng
 */

public class GameWindowController implements Initializable {
	
	/**
	 * These text attributes are used to show the information of player's aircraft
	 */
	
	@FXML
	private Text Throttle;
	
	@FXML
	private Text Altitude;
	
	@FXML
	private Text TAS;
	
	@FXML
	private Text IAS;
	
	@FXML
	private Text overLoad;
	
	@FXML
	private Text mach;
	
	@FXML
	private Text fuel;
	
	@FXML
	private Text ammo;
	
	@FXML
	private Text chaff;
	
	@FXML
	private Text flare;
	
	/**
	 * Buttons for pause and continue, up is used for test and debug(the button with "R" in game)
	 */
	
	@FXML
	private Button Pause;
	
	@FXML
	private Button Continue;
	
	@FXML
	private Button up;
	
	/**
	 * X is the location(value of x coordinate) of the player's aircraft
	 * Tips and upTips are used to indicate informations such as airbrake on and aircraft destroyed
	 */
	
	@FXML
	private Text X;
	
	@FXML
	private Text Tips;
	
	@FXML
	private Text upTips;
	
	/**
	 * WeaponIndicator is used to show the name of current weapon
	 */
	
	@FXML
	private Text WeaponIndicator;
	
	/**
	 * these groups are used to construct images in the stage
	 * group contains things like aircrafts
	 * the sky and ground are stored in the Background group
	 * houses, trees and other backgroud objects stored in the BackgroundObject group
	 * Weapon is used to store the bullets and externalloads
	 */
	
	@FXML
	private Group group;
	
	@FXML
	private Group Background;
	
	@FXML
	private Group BackgroundObject;
	
	@FXML
	private Group Weapon;
	
	@FXML
	private Group Indicators;
	
	/**
	 * UI of the current weapon, you can see it in the right bottom of the game window
	 */
	
	@FXML
	private ImageView WeaponUI;
	
	/**
	 * the size of map and map object
	 */
	
	public static double XMAX;
	
	public static MapObject map;
	
	/**
	 * X and Y location of Camera, Counter is used to eliminate informations of tips in time
	 */
	
	public static double CameraX;
	public static double CameraY;
	public static int Counter;
	
	/**
	 * list of aircrafts and the list used for count kills of player
	 */
	
	public static ArrayList<Aircraft> Aircrafts;
	public static ArrayList<Aircraft> Kills;
	
	/**
	 * Timer for the game
	 */
	
	static Timer t;
	static boolean record_start;
	static boolean record_stop;

	/**
	 * attribute used to controll explosion audio
	 */
	
	private boolean haveExploded;
	
   /**
	* Initialize the parameters
	* @param arg0 arg0 in type URL 
	* @param arg1 arg1 in type ResourceBundle
	*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		/**
		 * Initialize the parameters
		 */
		
		t = new Timer();
		record_start = true;
		record_stop = false;
		Counter = 0;
		CameraX = 0;
		CameraY = 0;
		group.getChildren().clear();
		Background.getChildren().clear();
		BackgroundObject.getChildren().clear();
		Weapon.getChildren().clear();
		Indicators.getChildren().clear();
		WeaponUI.setVisible(false);
		Aircrafts = new ArrayList<Aircraft>();
		Kills = new ArrayList<Aircraft>();
		haveExploded = false;
		
		/**
		 * Initialize the map
		 */
		
		map = new MapObject(StartWindowController.myMap0);
		XMAX = map.getMapSize();

		/**
		 * Initialize the aircrafts data
		 */
		
		Aircrafts.add(new Aircraft(StartWindowController.Plane0,0, StartWindowController.alt0, new Vector(0,StartWindowController.Speed0/3.6), StartWindowController.Fuel0, StartWindowController.Throttle0, 0,new PlayerControl(SettingWindowController.Setting),StartWindowController.loadChoice));
		for(int i = 0;i<StartWindowController.Number0-1;i++)
		{
			if(StartWindowController.alt0 == 0)
			{
				Aircrafts.add(new Aircraft(StartWindowController.Plane0,0+200*(i+1), 0, new Vector(0,StartWindowController.Speed0/3.6), StartWindowController.Fuel0, StartWindowController.Throttle0, i+1,new AIControl((int)(StartWindowController.alt0+100*(i+1))),StartWindowController.loadChoice));
			}
			else Aircrafts.add(new Aircraft(StartWindowController.Plane0,0+200*(i+1), StartWindowController.alt0+100*(i+1), new Vector(0,StartWindowController.Speed0/3.6), StartWindowController.Fuel0, StartWindowController.Throttle0, i+1,new AIControl((int)(StartWindowController.alt0+100*(i+1))),StartWindowController.loadChoice));
			
		}
		for(int i = 0;i<StartWindowController.Number1;i++)
		{
			if(StartWindowController.alt1 == 0)
			{
				Aircrafts.add(new Aircraft(StartWindowController.Plane1,XMAX-200*i, 0, new Vector(180,StartWindowController.Speed1/3.6), StartWindowController.Fuel1, StartWindowController.Throttle1, -i-1,new AIControl((int)(StartWindowController.alt1+100*i)),StartWindowController.loadChoice1));
			}
			else Aircrafts.add(new Aircraft(StartWindowController.Plane1,XMAX-200*i, StartWindowController.alt1+100*i, new Vector(180,StartWindowController.Speed1/3.6), StartWindowController.Fuel1, StartWindowController.Throttle1, -i-1,new AIControl((int)(StartWindowController.alt1+100*i)),StartWindowController.loadChoice1));
		}
		
		/**
		 * visualization of the aircrafts
		 */
		
		for(int i = 0;i<Aircrafts.size();i++)
		{
			group.getChildren().add(Aircrafts.get(i).getView());
		}
		
		/**
		 * visualization of the indicators
		 */
		
		for(int i = 0;i<Aircrafts.get(0).getMyPlayer().getMyDisplay().getIndicator().size();i++)
		{
			Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getIndicator().get(i));
		}
		
		for(int i = 0;i<Aircrafts.get(0).getMyPlayer().getMyDisplay().getRadarTarget().size();i++)
		{
			Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getRadarTarget().get(i));
			Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getRadarTargetDistance().get(i));
		}
		
		for(int i = 0;i<Aircrafts.get(0).getExternalLoadList().size();i++)
		{
			Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getMissleTarget().get(i));
			Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getMissleTargetDistance().get(i));
		}
		
		Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getRadarUpperLine());
		Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getRadarLowerLine());
		
		Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getInfraredUpperLine());
		Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getInfraredLowerLine());
		Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getInfraredTargetView());
		
		Indicators.getChildren().add(Aircrafts.get(0).getMyPlayer().getMyDisplay().getEyeLine());
		
		Aircrafts.get(0).getMyPlayer().setMaxViewPointer(Aircrafts.size());
		
		/**
		 * Initialize the bullet and externalLoad of aircrafts
		 */
		
		Aircrafts.get(0).getMyPlayer().setCurrentWeapon();
		for(int j = 0;j<Aircrafts.size();j++)
		{
			for(int i = 0;i<Aircrafts.get(j).getBulletList().size();i++)
			{
				Weapon.getChildren().add(Aircrafts.get(j).getBulletList().get(i).getView());
			}
			for(int i = 0;i<Aircrafts.get(j).getFlareList().size();i++)
			{
				Weapon.getChildren().add(Aircrafts.get(j).getFlareList().get(i).getView());
			}
			for(int i = 0;i<Aircrafts.get(j).getChaffList().size();i++)
			{
				Weapon.getChildren().add(Aircrafts.get(j).getChaffList().get(i).getView());
			}
			for(int i = 0;i<Aircrafts.get(j).getExternalLoadList().size();i++)
			{
				Weapon.getChildren().add(Aircrafts.get(j).getExternalLoadList().get(i).getView());
			}
		}
		
		/**
		 * Initialize the backgroud
		 */
		
		Image BG = new Image("/Image/Background/Background.png");
		ImageView background = new ImageView(BG);
		Background.getChildren().add(background);
		background.setLayoutX(-960);
		background.setLayoutY(-500);
		
		for(int i = 0;i<map.getViewList().size();i++)
		{
			BackgroundObject.getChildren().add(map.getViewList().get(i));
		}
	}
	
	/**
	* Update views of the BackGround Objects
	*/
	public void LoadBackGroundObjects()
	{
		for(int i = 0;i<map.getViewList().size();i++)
		{			
			    map.getViewList().get(i).setVisible(true);
			    map.getViewList().get(i).setLayoutX(map.getObjectXList().get(i)-CameraX-map.getViewList().get(i).getImage().getWidth()/2);
			    map.getViewList().get(i).setLayoutY(-map.getObjectYList().get(i)+CameraY-map.getViewList().get(i).getImage().getHeight()/2);
		}
	}
	
	/**
	* Update views of  of the weapons
	*/
	public void LoadWeapons()
	{
		for(int j = 0;j<Aircrafts.size();j++)
		{
			for(int i = 0;i<Aircrafts.get(j).getBulletList().size();i++)
			{	
				if(Aircrafts.get(j).getBulletList().get(i).isEffective()==true)
				{
					 Aircrafts.get(j).getBulletList().get(i).getView().setVisible(true);
					 Aircrafts.get(j).getBulletList().get(i).getView().setLayoutX(Aircrafts.get(j).getBulletList().get(i).getX()-CameraX-Aircrafts.get(j).getBulletList().get(i).getView().getImage().getWidth()/2);
					 Aircrafts.get(j).getBulletList().get(i).getView().setLayoutY(-Aircrafts.get(j).getBulletList().get(i).getY()+CameraY-Aircrafts.get(j).getBulletList().get(i).getView().getImage().getHeight()/2);
					 Aircrafts.get(j).getBulletList().get(i).getView().setRotate(-Aircrafts.get(j).getBulletList().get(i).getVelocity().getDirection());
				}
				else Aircrafts.get(j).getBulletList().get(i).getView().setVisible(false);
			}
			
			for(int i = 0;i<Aircrafts.get(j).getExternalLoadList().size();i++)
			{	
				Aircrafts.get(j).getExternalLoadList().get(i).setView(CameraX, CameraY);
			}
		}
	}
	
	/**
	* Deal with damage event
	* When an aircraft is hit by a bullet or externalload, construct the damageModel
	* When an enemy aircraft is destroyed by player, indicate that
	*/
	public void DamageEvent()
	{
		for(int i = 0;i<Aircrafts.size();i++)
		{
			for(int j = 0;j<Aircrafts.size();j++)
			{
				if(i!=j)
				{
					for(int k = 0;k<Aircrafts.get(j).getBulletList().size();k++)
					{
						boolean hit = DamageModel.GunDamage(Aircrafts.get(j).getBulletList().get(k), Aircrafts.get(i));
					    if(hit == true&&!Kills.contains(Aircrafts.get(i)) && Aircrafts.get(j).getSignature() == 0 && Aircrafts.get(i).getLife() <= 0)
						{
							Tips.setText("Aircraft Destroyed");
							ExplosionAudio();
							Tips.autosize();
							Counter = 150;
							Kills.add(Aircrafts.get(i));
						}
					}
					
					for(int k = 0;k<Aircrafts.get(j).getExternalLoadList().size();k++)
					{
						Aircrafts.get(j).getExternalLoadList().get(k).CreateExplosion(Aircrafts.get(i));
						boolean hit = DamageModel.KneticEnergyDamage(Aircrafts.get(j).getExternalLoadList().get(k), Aircrafts.get(i));
						if(hit == true&&!Kills.contains(Aircrafts.get(i)) && Aircrafts.get(j).getSignature() == 0 && Aircrafts.get(i).getSignature()<0 && Aircrafts.get(i).getLife() <= 0)
						{
							Tips.setText("Aircraft Destroyed");
							ExplosionAudio();
							Tips.autosize();
							Counter = 150;
							Kills.add(Aircrafts.get(i));
						}
						if(Aircrafts.get(j).getExternalLoadList().get(k).getExplosion()!=null)
						{
							Aircrafts.get(j).getExternalLoadList().get(k).getExplosion().move();
							hit = DamageModel.ExplosionDamage(Aircrafts.get(j).getExternalLoadList().get(k).getExplosion(), Aircrafts.get(i));
							if(hit == true&&!Kills.contains(Aircrafts.get(i)) && Aircrafts.get(j).getSignature() == 0 && Aircrafts.get(i).getSignature()<0 && Aircrafts.get(i).getLife() <= 0)
							{
								Tips.setText("Aircraft Destroyed");
								ExplosionAudio();
								Tips.autosize();
								Counter = 150;
								Kills.add(Aircrafts.get(i));
							}
						}
					}
				}
			}
		}
	}
	
	/**
	* Clear information of tips in time
	*/
	
	public void AutoClearTips()
	{
		if(Counter > 0)
		{
			Counter--;
		}
		if(Counter == 0)
		{
			Tips.setText("");
		}
	}
	
	/**
	* Audio of Warning and Explosion
	*/
	
	public void PlayWarningAudio()
	{
		if(SettingWindowController.Setting.isHaveWarningAudio())
		{
			Media sound = new Media(new File("src/Audio/Signal/Warning.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setVolume(SettingWindowController.Setting.getWarningAudioVolume());
			mediaPlayer.setStartTime(Duration.seconds(0));
			mediaPlayer.setStopTime(Duration.seconds(1));
			mediaPlayer.play();
		}
	}
	
	public void ExplosionAudio()
	{
		if(SettingWindowController.Setting.isHaveEffectAudio())
		{
			Media sound = new Media(new File("src/Audio/Effect/Explosion Distant Air Bomb 01.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setVolume(SettingWindowController.Setting.getEffectAudioVolume());
			mediaPlayer.setStartTime(Duration.seconds(0));
			mediaPlayer.setStopTime(Duration.seconds(1));
			mediaPlayer.play();
		}
	}
	
	
	  /**
	   * Countinue action, countinue the runing of the aircraft
	   * @param event event in type ActionEvent
	   */
	   public void Continue(ActionEvent event)
	   {;
			if (record_start)
			{
				t = new Timer();
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub										
						Platform.runLater(() -> 
						{
							/**
							* Set Camera to the location of the player's aircraft
							*/
							
							if(Aircrafts.get(0).getX()>=480)
							{
								CameraX = Aircrafts.get(0).getX();
							}
							else CameraX = 480;
							
							if(Aircrafts.get(0).getY()>260)
							{
								CameraY = Aircrafts.get(0).getY();
							}
							else CameraY = 260;
							
							/**
							* Implement the player's control of aircraft
							*/
							
							Continue.setOnKeyPressed(new EventHandler<KeyEvent>(){
								 public void handle(KeyEvent e)
								{
		                            Aircrafts.get(0).getMyPlayer().LogicImplementWithInput(e.getCode().toString());
								}
					        });
							
							/**
							* Excute all the operations of aircrafts
							* include move, fuel consump, view update, radar and Infrared working, externalload and bullet operations
							*/
							
							for(int j = 0;j<Aircrafts.size();j++)
							{
								Aircrafts.get(j).move();
								Aircrafts.get(j).FuelConsump(); 
								Aircrafts.get(j).setFlightCondition(XMAX);
								Aircrafts.get(j).setView(CameraX, CameraY);
								Aircrafts.get(j).setAccordingToLife();		
								
								for(int k = 0;k<Aircrafts.size();k++)
								{
									if(k!=j)
									{
										Aircrafts.get(j).getMyPlayer().Observe(Aircrafts.get(k));
										Aircrafts.get(j).getMyPlayer().CheckInfraredLock(Aircrafts.get(k));
										if(Aircrafts.get(j).getRadar()!=null)
										{
											Aircrafts.get(j).getMyPlayer().RadarScan(Aircrafts.get(k));
										}
									}
								}
								Aircrafts.get(j).getMyPlayer().getRadarModel().RadarScanPointerIncreament();
									
								if(j != 0)
								{
								    Aircrafts.get(j).getMyPlayer().LogicImplement();
								}
								
								for(int i = 0;i<Aircrafts.get(j).getBulletList().size();i++)
								{
									if(Aircrafts.get(j).getBulletList().get(i).isEffective() == true)
									{
										Aircrafts.get(j).getBulletList().get(i).move();
									}
								}
								
								for(int i = 0;i<Aircrafts.get(j).getFlareList().size();i++)
								{
									if(Aircrafts.get(j).getFlareList().get(i).isEffective() == true)
									{
										Aircrafts.get(j).getFlareList().get(i).move();
										Aircrafts.get(j).getFlareList().get(i).setView(CameraX, CameraY);
									}
								}
								
								for(int i = 0;i<Aircrafts.get(j).getChaffList().size();i++)
								{
									if(Aircrafts.get(j).getChaffList().get(i).isEffective() == true)
									{
										Aircrafts.get(j).getChaffList().get(i).move();
										Aircrafts.get(j).getChaffList().get(i).setView(CameraX, CameraY);
									}
								}
								
								for(int i = 0;i<Aircrafts.get(j).getExternalLoadList().size();i++)
								{
									if(Aircrafts.get(j).getExternalLoadList().get(i).isEffective() == true)
									{
										Aircrafts.get(j).getExternalLoadList().get(i).move();
										for(int k=0; k<Aircrafts.size(); k++)
										{
											if(Aircrafts.get(k).getMyPlayer().isRadarMode()==true)
											{
												if(j!=k)
												{
													Aircrafts.get(k).getMyPlayer().getRadarModel().setRadarWarning(Aircrafts.get(k).getMyPlayer().getRadarModel().RadarWarning(Aircrafts.get(j).getExternalLoadList().get(i),Aircrafts.get(j).getMyPlayer().getRadarModel()));
													if(Aircrafts.get(0).getMyPlayer().getRadarModel().isRadarWarning())
													{
														Tips.setText("Radar homing missile lauched!");
														PlayWarningAudio();
														Tips.autosize();
														Counter = 150;
													}
												}
											}
										}
									}
								}
							}
							
							DamageEvent();
							
							/**
							* Update of player's view
							*/
							
							LoadBackGroundObjects();					
							LoadWeapons();
							
							Aircrafts.get(0).getMyPlayer().InfraredViewUpdate();
							Aircrafts.get(0).getMyPlayer().RadarViewUpdate();
							Aircrafts.get(0).getMyPlayer().HelmetViewUpdate();
							Aircrafts.get(0).setSound();

							X.setText(Aircrafts.get(0).getIndicateX());
							Throttle.setText(Aircrafts.get(0).getIndicateThrottle());
							Altitude.setText(Aircrafts.get(0).getIndicateAltitude());
							TAS.setText(Aircrafts.get(0).getIndicateTAS());
							IAS.setText(Aircrafts.get(0).getIndicateIAS());
							overLoad.setText(Aircrafts.get(0).getIndicateG());
							mach.setText(Aircrafts.get(0).getIndicateMach());
							fuel.setText(Aircrafts.get(0).getIndicateFuel());
							ammo.setText(Aircrafts.get(0).getIndicateAmmo());
							chaff.setText(Aircrafts.get(0).getIndicateChaff());
							flare.setText(Aircrafts.get(0).getIndicateFlare());
							if(Aircrafts.get(0).getMyPlayer().getCurrentWeapon()!=null)
							{
								WeaponIndicator.setText(Aircrafts.get(0).getMyPlayer().getCurrentWeapon().getPerformance().getName()+" ¡Á "+Integer.toString(Aircrafts.get(0).getMyPlayer().getTotalCurrentWeapon()));
								WeaponUI.setImage(new Image("/Image/ExternalLoad/UI/"+Aircrafts.get(0).getMyPlayer().getCurrentWeapon().getPerformance().getName()+"_UI.png"));
								WeaponUI.setVisible(true);
							}
							else WeaponUI.setVisible(false);
							
							/**
							* Update of tips and upTips
							*/
							
							if(Aircrafts.get(0).getLife() == 0)
							{
								if(haveExploded == false)
								{
									ExplosionAudio();
									haveExploded = true;
								}
								Tips.setText("GAME OVER");
								Tips.autosize();
								Counter = 2;
							}
							
							boolean EnemyAlive = false;
							
							for(int i = 0;i<Aircrafts.size();i++)
							{
								if(Aircrafts.get(i).getSignature()<0&&Aircrafts.get(i).getLife()>0)
								{
									EnemyAlive = true;
								}
							}
							if(EnemyAlive == false&&StartWindowController.Number1!=0)
							{
								Tips.setText("Your team win!");
								Tips.autosize();
								Counter = 2;
							}
							
							if(Aircrafts.get(0).getMach()>Aircrafts.get(0).getPerformance().getLimitMach())
							{
								upTips.setText("Warning: Mach Number Limit "+String.format("%.2f", Aircrafts.get(0).getPerformance().getLimitMach())+"M!");
								upTips.autosize();
								PlayWarningAudio();
							}
							else if(Aircrafts.get(0).getIAS()>Aircrafts.get(0).getPerformance().getLimitIAS()/3.6)
							{
								upTips.setText("Warning: Indicated Airspeed Limit "+Integer.toString((int)Aircrafts.get(0).getPerformance().getLimitIAS())+"km/h!");
								upTips.autosize();
								PlayWarningAudio();
							}
							else if(Aircrafts.get(0).getAirBrake() == true) 
							{
								upTips.setText("Air brake is on");
								upTips.autosize();
							}
							else upTips.setText("");
							
							AutoClearTips();
						});
					}
				}, 0, 20);
			}
			record_start = false;
			record_stop = false;
	   }
	   
	  /**
	   * Pause action, pause the game
	   * @param event event in type ActionEvent
	   */
	   public void Pause(ActionEvent event)
	   {
			if (record_stop) {
				record_stop = false;
			}
			record_stop = true;
			record_start = true;
			t.cancel();
	   }
	   
	  /**
       * Event actived by the R button, used for test and debug
	   * @param event event in type ActionEvent
	   */
	   public void up(ActionEvent event)
	   {
		   System.out.println("A:"+Aircrafts.get(0).getA());
		   System.out.println("AOA:"+Aircrafts.get(0).getAOA());
		   System.out.println("V:"+Aircrafts.get(0).getVelocity().getValue()*3.6+","+Aircrafts.get(0).getVelocity().getDirection());
		   System.out.println("Mach:"+Aircrafts.get(0).getIndicateMach());
		   System.out.println("IAS:"+Aircrafts.get(0).getIndicateIAS());
		   System.out.println("D:"+Aircrafts.get(0).getVelocity().getDirection());
		   System.out.println("G:"+Aircrafts.get(0).getIndicateG());
		   System.out.println("alt:"+Aircrafts.get(0).getY());
		   System.out.println("Thrust:"+Aircrafts.get(0).getThrust().getValue()+","+Aircrafts.get(0).getThrust().getDirection());
		   System.out.println("Lift:"+Aircrafts.get(0).getLift().getValue()+","+Aircrafts.get(0).getLift().getDirection());
		   System.out.println("Drag:"+Aircrafts.get(0).getDrag().getValue()+","+Aircrafts.get(0).getDrag().getDirection());
		   System.out.println("TotalForce:"+Aircrafts.get(0).getTotalForce().getValue()+","+Aircrafts.get(0).getTotalForce().getDirection());
		   System.out.println("Fuel:"+Aircrafts.get(0).getIndicateFuel());
		   System.out.println("SEP:"+(Aircrafts.get(0).getThrust().getValue()-Aircrafts.get(0).getDrag().getValue())*Aircrafts.get(0).getVelocity().getValue()/Aircrafts.get(0).getGravity().getValue());
		   System.out.println(" ");
	   }
}