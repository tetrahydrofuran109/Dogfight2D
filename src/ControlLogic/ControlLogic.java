package ControlLogic;

import java.io.File;

import Controller.SettingWindowController;
import Object.Aircraft;
import Object.CounterMeasures;
import Object.ExternalLoad;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import Object.Object;
import Physics.Earth;
import Physics.InfraredModel;
import Physics.RadarModel;
import Physics.Vector;
import View.PlayerDisplay;

/**
 * class ContrilLogic is to define the control method of a certain aircraft
 * including the setting of the Radar and Infared System, switching of weapon and aircraft operation such as turning and trim
 * Its Subclass include PlayerControl and AIControl
 * @author WangShuzheng
 */

public class ControlLogic {
	
	/**
	 * Attributes for ControlLogic
	 * DetectRange is the visual limit of a pilot, is defined to be 3000
	 * CommandOrder is used to control Command guide missles
	 */
	
	protected Aircraft MyAircraft;
	protected double DetectRange;
	
	protected boolean InfraredMode;
	protected InfraredModel infraredModel;
	
	protected ExternalLoad CurrentWeapon;
	protected int totalCurrentWeapon;
	private int CurrentWeaponPointer;
	
	protected boolean RadarMode;
	protected RadarModel radarModel;
	
	protected double HelmetDirection = 0;
	
	protected int CommandOrder = 0;
	
	protected int counterMeasureInterval = 0;
	
	public int getCommandOrder() {
		return CommandOrder;
	}

	public void setCommandOrder(int commandOrder) {
		CommandOrder = commandOrder;
	}

	public double getDetectRange() {
		this.DetectRange = 3000;
		return DetectRange;
	}
	
	public RadarModel getRadarModel() {
		return radarModel;
	}
	
	public void setRadarModel() {
		radarModel = new RadarModel(this.getMyAircraft(),this.getMyAircraft().getRadar());
	}
	
	public void setInfraredModel()
	{
		infraredModel = new InfraredModel(this.getMyAircraft(),this.getMyAircraft().getRadar());
	}
	
	public InfraredModel getInfraredModel() {
		return infraredModel;
	}

	public ExternalLoad getCurrentWeapon() {
		return CurrentWeapon;
	}
	
	public double getHelmetDirection() {
		return HelmetDirection;
	}
	
	public boolean isRadarMode() {
		return RadarMode;
	}

	public boolean isInfraredMode() {
		return InfraredMode;
	}

	public Aircraft getMyAircraft() {
		return MyAircraft;
	}
	
	public void setMyAircraft(Aircraft myAircraft) {
		MyAircraft = myAircraft;
	}

	/**
	 * Switch to next weapon
	 */
	
	public void setCurrentWeapon()
	{
		if(this.CurrentWeapon == null)
		{
			if(this.MyAircraft.getExternalLoadList().size()>0)
			{
				this.CurrentWeapon = this.getMyAircraft().getExternalLoadList().get(0);
				CurrentWeaponPointer = 0;
			}
		}
		else
		{
			for(int i = CurrentWeaponPointer;i<this.getMyAircraft().getExternalLoadList().size();i++)
			{
				CurrentWeaponPointer = i;
				if(!this.getMyAircraft().getExternalLoadList().get(i).getPerformance().getName().contains(this.CurrentWeapon.getPerformance().getName()))
				{
					this.CurrentWeapon = this.getMyAircraft().getExternalLoadList().get(i);
					this.HelmetDirection = 0;
					break;
				}
				if(this.CurrentWeaponPointer == this.getMyAircraft().getExternalLoadList().size()-1)
				{
					CurrentWeaponPointer = 0;
				}
			}
		}
	}
	
	/**
	 * This method is used to count the total amount of current weapon
	 * @return the total amount of current weapon
	 */
	
	public int getTotalCurrentWeapon()
	{
		this.totalCurrentWeapon = 0;
		if(this.CurrentWeapon!=null)
		{
			for(int i = 0;i<this.getMyAircraft().getExternalLoadList().size();i++)
			{
				if(this.getMyAircraft().getExternalLoadList().get(i).getPerformance().getName().contains(this.CurrentWeapon.getPerformance().getName())&&this.getMyAircraft().getExternalLoadList().get(i).isLauched()==false)
				{
					this.totalCurrentWeapon++;
				}
			}
		}
		return this.totalCurrentWeapon;
	}

	/**
	 * Turn on or turn off the infrared mode
	 */
	
	public void setInfraredMode()
	{
        if(this.InfraredMode == true)
        {
        	this.InfraredMode = false;
        }
        else
        {
        	this.setInfraredModel();
			this.InfraredMode = this.getInfraredModel().CreateEnvelope(this.CurrentWeapon, this.HelmetDirection);
        }
	}
	
	/**
	 * Turn on or turn off the Radar mode
	 */
	
	public void setRadarMode()
	{
        if(this.RadarMode == true)
        {
        	this.RadarMode = false;
        }
        else
        {
    		if(this.MyAircraft.getRadar()!=null)
    		{
    			this.RadarMode = true;
    			this.setRadarModel();
    		}
        }
	}
	
	/**
	 * This method is used to detect a object by radar, which will be called by GameWindowController
	 * Also it can implement the helmet aiming of Radar Guide missle if aircraft has such function
	 * @param object the object which is detected
	 */
	
	public void RadarScan(Object object)
	{
		if(this.RadarMode == true)
		{
			this.radarModel.radarDetect(object);
			if(this.CurrentWeapon!=null)
			{
				if(this.getMyAircraft().getHelmet()!=null&&this.getMyAircraft().getHelmet().getAllowedWeapon().contains(this.CurrentWeapon.getPerformance().getName()))
				{
					this.getRadarModel().helmetGuide(object, this.HelmetDirection, this.getDetectRange());
				}
			}
		}
	}

	/**
	 * This method is used to detect a object by Infrared system, which will be called by GameWindowController
	 * @param object the object which is detected
	 */
	
	public void CheckInfraredLock(Object object)
	{
		if(this.InfraredMode == true)
		{		
			this.getInfraredModel().InfraredDetect(object);
		}
	}
	
	/**
	 * This method is used to turning the helmet aimer
	 * @param CounterClockWise It is true if the order is turning counterClockWise
	 */
	
	public void HeadTurning(boolean CounterClockWise)
	{
	    if(this.getMyAircraft().getHelmet()!=null&&this.getMyAircraft().getHelmet().getAllowedWeapon().contains(this.CurrentWeapon.getPerformance().getName()))
		{
			if(CounterClockWise == true)
			{
				if(this.HelmetDirection<this.getMyAircraft().getHelmet().getViewField())
				{
					this.HelmetDirection = this.HelmetDirection + Earth.TimeInterval*this.getMyAircraft().getHelmet().getTrackRate()/1000;
				}
			}
			else
			{
				if(this.HelmetDirection>-this.getMyAircraft().getHelmet().getViewField())
				{
					this.HelmetDirection = this.HelmetDirection - Earth.TimeInterval*this.getMyAircraft().getHelmet().getTrackRate()/1000;
				}
			}
			if(this.CurrentWeapon.getPerformance().getProperty().contains("Infrared"))
			{
				this.getInfraredModel().setInfraredLockDirection(this.HelmetDirection);
			}
		}
	}
	
	/**
	 * This method is used to play audio during missle launch
	 */
	
	public void MissleLaunchAudio()
	{
		if(SettingWindowController.Setting.isHaveLaunchAudio())
		{
			Media sound = new Media(new File("src/Audio/Weapon/MissleLaunch.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setVolume(SettingWindowController.Setting.getLaunchAudioVolume());
			mediaPlayer.play();
		}
	}
	
	/**
	 * This method is used to determine which externalload can launch and implement it
	 */
	
	public void Launch()
	{
		if(this.CurrentWeapon != null)
		{
			for(int i = 0;i<this.getMyAircraft().getExternalLoadList().size();i++)
			{
				if(this.getMyAircraft().getExternalLoadList().get(i).isLauched() == false&&this.getMyAircraft().getExternalLoadList().get(i).getPerformance().getName().contains(this.CurrentWeapon.getPerformance().getName()))
				{
					if(this.getMyAircraft().getExternalLoadList().get(i).getPerformance().getProperty().contains("InfraredAAM"))
					{
						if(this.getInfraredModel().getInfraredTarget() != null)
						{
							this.getMyAircraft().getExternalLoadList().get(i).setVelocity(new Vector(this.getMyAircraft().getVelocity().getDirection()+this.MyAircraft.getAOA(),this.MyAircraft.getVelocity().getValue()));
							this.getMyAircraft().getExternalLoadList().get(i).setCapturedObject(this.getInfraredModel().getInfraredTarget());
							this.getMyAircraft().getExternalLoadList().get(i).setLauched(true);
							MissleLaunchAudio();
							break;
						}
					}
					
					if(this.getMyAircraft().getExternalLoadList().get(i).getPerformance().getProperty().contains("SemiRadarAAM"))
					{
						if(this.getMyAircraft().getExternalLoadList().get(i).getPerformance().getProperty().contains("Command")||this.getMyAircraft().getExternalLoadList().get(i).getCapturedObject()!=null)
						{
							this.getMyAircraft().getExternalLoadList().get(i).setVelocity(new Vector(this.getMyAircraft().getVelocity().getDirection()+this.MyAircraft.getAOA(),this.MyAircraft.getVelocity().getValue()));
							this.getMyAircraft().getExternalLoadList().get(i).setLauched(true);
							MissleLaunchAudio();
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * This method is to implement the shooting of guns
	 */
	
	public void Firing()
	{
		for(int i = 0;i<this.MyAircraft.getGuns().size();i++)
		{
			if(this.MyAircraft.getGuns().get(i).getAmmoCount()>0)
			{
				if(this.MyAircraft.getGuns().get(i).getCount() == 0)
				{
					int Pointer = 0;
					for(int j = 0;j<i;j++)
					{
						Pointer = Pointer + this.MyAircraft.getGuns().get(j).getAmmoAmount();
					}
					this.MyAircraft.getBulletList().get(Pointer+this.MyAircraft.getGuns().get(i).getAmmoAmount()-this.MyAircraft.getGuns().get(i).getAmmoCount()).Enable(this.MyAircraft.getX(), this.MyAircraft.getY(), this.MyAircraft.getGuns().get(i).getGunLocation(), this.MyAircraft.getVelocity().getValue(), this.MyAircraft.getVelocity().getDirection()+this.MyAircraft.getAOA());
					this.MyAircraft.getGuns().get(i).setAmmoCount(this.MyAircraft.getGuns().get(i).getAmmoCount()-1);
				}
				this.MyAircraft.getGuns().get(i).IncreaseCount();
				if(this.MyAircraft.getGuns().get(i).getCount()>this.MyAircraft.getGuns().get(i).getShootInterval())
				{
					this.MyAircraft.getGuns().get(i).setCount();
				}
			}
		}
	}
	
	/**
	 * This method is to implement the launch of flares
	 */
	
	public void LaunchFlares()
	{
		if(this.counterMeasureInterval == 0)
		{
			for(int i=0;i<this.getMyAircraft().getFlareList().size();i++)
			{
				if(this.getMyAircraft().getFlareList().get(i).isEffective()==false&&this.getMyAircraft().getFlareList().get(i).getDuration()==150)
				{
					this.getMyAircraft().getFlareList().get(i).Enable(this.getMyAircraft());
					if(this.getMyAircraft().getAntiInfraredValue()<150)
					{
						this.getMyAircraft().setAntiInfraredValue(this.getMyAircraft().getAntiInfraredValue()+CounterMeasures.flarePower);
					}
					this.counterMeasureInterval = 2;
					break;
				}
			}
		}
		else this.counterMeasureInterval--;
	}
	
	/**
	 * This method is to implement the launch of chaff
	 */
	
	public void LaunchChaff()
	{
		if(this.counterMeasureInterval == 0)
		{
			for(int i=0;i<this.getMyAircraft().getChaffList().size();i++)
			{
				if(this.getMyAircraft().getChaffList().get(i).isEffective()==false&&this.getMyAircraft().getChaffList().get(i).getDuration()==150)
				{
					this.getMyAircraft().getChaffList().get(i).Enable(this.getMyAircraft());
					if(this.getMyAircraft().getAntiRadarValue()<150)
					{
						this.getMyAircraft().setAntiRadarValue(this.getMyAircraft().getAntiRadarValue()+CounterMeasures.ChaffPower);
					}
					this.counterMeasureInterval = 2;
					break;
				}
			}
		}
		else this.counterMeasureInterval--;
	}
	
	/**
	 * Pitch order is used to let the aircraft turning counterClockWise(when turning counterClockWise, aoa is larger than 0)
	 * this is implemented by increase the attack angle(AOA) of aircraft, the lift coefficient is proportional to the angle of attack
	 */
	
	public void Pitch()
	{
		if(this.MyAircraft.getY()>=1 || (this.MyAircraft.getVelocity().getDirection()>=0 && this.MyAircraft.getVelocity().getDirection()<=90))
		{
			if(this.MyAircraft.getAOA()<this.MyAircraft.getPerformance().getAOAmax())
			{
				this.MyAircraft.setAOA(this.MyAircraft.getAOA() + this.MyAircraft.getPitchRate()/this.MyAircraft.getMass());
				if(this.MyAircraft.getAOA() > this.MyAircraft.getPerformance().getAOAmax())
				{
					this.MyAircraft.setAOA(this.MyAircraft.getPerformance().getAOAmax());
				}
			}
		}
	}
	
	/**
	 * Dive order is used to let the aircraft turning ClockWise(when turning clockwise, aoa is less than 0)
	 * this is implemented by decrease the attack angle(AOA) of aircraft, the lift coefficient is proportional to the angle of attack
	 */
	
	public void Dive()
	{
		if(this.MyAircraft.getY()>=1 || (this.MyAircraft.getVelocity().getDirection()>=90 && this.MyAircraft.getVelocity().getDirection()<=180))
		{
			if(this.MyAircraft.getAOA()>-this.MyAircraft.getPerformance().getAOAmax())
			{
				this.MyAircraft.setAOA(this.MyAircraft.getAOA() - this.MyAircraft.getPitchRate()/this.MyAircraft.getMass());
				if(this.MyAircraft.getAOA() < -this.MyAircraft.getPerformance().getAOAmax())
				{
					this.MyAircraft.setAOA(-this.MyAircraft.getPerformance().getAOAmax());
				}
			}
		}
	}
	
	/**
	 * Trim order is to stay the aircraft in level flight
	 * it is implemented by setting AOA to a certain value, which can make the total normal force of the aircraft balanced
	 */
	
	public void Trim()
	{
		if(0<this.MyAircraft.getTotalForce().getAngle(this.MyAircraft.getVelocity()) && this.MyAircraft.getTotalForce().getAngle(this.MyAircraft.getVelocity())<180)
		{
			Double needAOA = this.MyAircraft.getTotalForce().getValue()*Math.sin(Math.PI*this.MyAircraft.getTotalForce().getAngle(this.MyAircraft.getVelocity())/180)/this.MyAircraft.getDynamicPressure()/this.MyAircraft.getPerformance().getWingArea()/this.MyAircraft.getPerformance().getKCl();
			if(this.MyAircraft.getAOA() == 0)
			{
				if(90<this.MyAircraft.getVelocity().getDirection()&&this.MyAircraft.getVelocity().getDirection()<270)
				{
					this.MyAircraft.setAOA(this.MyAircraft.getAOA() - needAOA);
				}
				else this.MyAircraft.setAOA(this.MyAircraft.getAOA() + needAOA);
			}
			else if(this.MyAircraft.getAOA()*Math.cos(Math.PI*this.MyAircraft.getLift().getAngle(this.MyAircraft.getTotalForce())/180)>0)
			{
				this.MyAircraft.setAOA(this.MyAircraft.getAOA() - needAOA);
			}
			else
			{
				this.MyAircraft.setAOA(this.MyAircraft.getAOA() + needAOA);
			}
			
			if(this.MyAircraft.getAOA() > this.MyAircraft.getPerformance().getAOAmax())
			{
				this.MyAircraft.setAOA(this.MyAircraft.getPerformance().getAOAmax());
			}
			if(this.MyAircraft.getAOA() < -this.MyAircraft.getPerformance().getAOAmax())
			{
				this.MyAircraft.setAOA(-this.MyAircraft.getPerformance().getAOAmax());
			}
		}
	}
	
	/**
	 * ThrottleUP() order is to increase the throttle of aircraft
	 * when throttle is at 100, use this order can open the afterburn if this plane has it
	 */
	
	public void ThrottleUP()
	{
		if(this.MyAircraft.getThrottle() < 100)
		{
			this.MyAircraft.setThrottle(this.MyAircraft.getThrottle() + 5);
		}
		else if(this.MyAircraft.getEngine().isHaveWEP()==true) this.MyAircraft.setWEP(true);
		if(this.MyAircraft.isBrake() == true)
		{
			this.MyAircraft.setBrake(false);
		}
	}
	
	/**
	 * ThrottleDOWN() order is to decrease the throttle of aircraft
	 * when throttle is at 0, use this order can open the brake if this plane is on the ground
	 */
	
	public void ThrottleDOWN()
	{
		if(this.MyAircraft.getThrottle() > 0)
		{
			this.MyAircraft.setThrottle(this.MyAircraft.getThrottle() - 5);
		}
		else if(this.MyAircraft.getY()<1) this.MyAircraft.setBrake(true);
		if(this.MyAircraft.isWEP() == true)
		{
			this.MyAircraft.setWEP(false);
		}
	}
	
	/**
	 * Switch on or off the airbrake if this airplane has it
	 * it can help aircraft to slow down when it is flying
	 */
	
	public void setAirBrake()
	{
		if(this.MyAircraft.getPerformance().isHasBrake()==true)
		{
			if(this.MyAircraft.getAirBrake() == false)
			{
				this.MyAircraft.setAirBrake(true);
			}
			else
			{
				this.MyAircraft.setAirBrake(false);
			}
		}
	}
	
	/**
	 * This is classes implemented by its subClass, which need to be accessed by other class
	 */
	
	public void LogicImplement()
	{
		
	}
	
	public void LogicImplementWithInput(String Code)
	{
		
	}
	
	public void Observe(Aircraft aircraf)
	{
		
	}
	
	public void InfraredViewUpdate()
	{
		
	}
	
	public void HelmetViewUpdate()
	{
		
	}

	public void RadarViewUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	public void setMaxViewPointer(int maxViewPointer) {

	}
	
	public PlayerDisplay getMyDisplay() {
		return null;
	}
}
