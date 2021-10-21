package ControlLogic;

import Object.Aircraft;
import Physics.Locating;
import Physics.RadarModel;
import Property.ControlSettings;
import View.PlayerDisplay;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * class for the Player control
 * @author WangShuzheng
 */

public class PlayerControl extends ControlLogic {
	
	/**
	 * Attributes for Player control
	 * isControl is false when turning, if it is true, the aircraft will trim automatically
	 */
	
	private ControlSettings Setting;
	private PlayerDisplay MyDisplay = new PlayerDisplay(this);
	
	private int InfraredAAMIndicateNumber;
	private int RadarAAMIndicateNumber;
	private int viewPointer;
	private int maxViewPointer;
	
	private boolean isControl;

	/**
	 * Constructor
	 * @param Setting load the setting of KeyCode
	 */
	
	public PlayerControl(ControlSettings Setting) {
		super();
		this.Setting = Setting;
		this.viewPointer = 0;
	}
	
	/**Getters and Setters**/
	
	public PlayerDisplay getMyDisplay() {
		return MyDisplay;
	}

	public boolean isControl() {
		return isControl;
	}
	
	public void setControl(boolean isControl) {
		this.isControl = isControl;
	}

	public void setMaxViewPointer(int maxViewPointer) {
		this.maxViewPointer = maxViewPointer;
	}
	
	/**
	 * Update the view of helmet(the indicate line) if the aircraft has it
	 * this will be called in GameWindowController
	 */
	
	public void HelmetViewUpdate()
	{
		if(this.CurrentWeapon!=null)
		{
			if((this.isRadarMode()==true||this.isInfraredMode()==true)&&(this.getMyAircraft().getHelmet()!=null&&this.getMyAircraft().getHelmet().getAllowedWeapon().contains(this.CurrentWeapon.getPerformance().getName())))
			{
                this.MyDisplay.setIndicatorLine(this.MyDisplay.getEyeLine(), this.getHelmetDirection());
                this.MyDisplay.getEyeLine().setVisible(true);
			}
			else this.MyDisplay.getEyeLine().setVisible(false);
		}
	}
	
	/**
	 * Update the view of RadarSystem(the Radar Field and indicators of targets) if the aircraft has it
	 * this will be called in GameWindowController
	 */
	
	public void RadarViewUpdate()
	{
		if(this.isRadarMode() == true)
		{
			if(this.getMyAircraft().getRadar().getMaxDetectField()<5)
			{
				this.MyDisplay.setIndicatorLine(this.MyDisplay.getRadarUpperLine(), 0);
				this.MyDisplay.getRadarUpperLine().setVisible(true);
				this.MyDisplay.getRadarLowerLine().setVisible(false);
			}
			else
			{
				this.MyDisplay.setIndicatorLine(this.MyDisplay.getRadarUpperLine(), this.getMyAircraft().getRadar().getMaxDetectField());
				this.MyDisplay.getRadarUpperLine().setVisible(true);
				this.MyDisplay.setIndicatorLine(this.MyDisplay.getRadarLowerLine(), -this.getMyAircraft().getRadar().getMaxDetectField());
				this.MyDisplay.getRadarLowerLine().setVisible(true);
			}
			
			if(!this.getRadarModel().getRadarTargetList().isEmpty())
			{
				for(int i=0;i<this.getRadarModel().getRadarTargetList().size();i++)
				{
					this.MyDisplay.getRadarTarget().get(i).setVisible(false);
					this.MyDisplay.getRadarTargetDistance().get(i).setVisible(false);
					
					this.MyDisplay.getRadarTarget().get(i).setVisible(this.MyDisplay.setIndicatorTarget(this.MyDisplay.getRadarTarget().get(i), this.getRadarModel().getRadarTargetList().get(i)));
					if(Locating.getDistance(this.getMyAircraft(), this.getRadarModel().getRadarTargetList().get(i))<=RadarModel.getActualDetecRange(this.getRadarModel().getRadarTargetList().get(i).getRCS(), this.getMyAircraft().getRadar().getShowDistanceRange()))
					{
						this.MyDisplay.getRadarTargetDistance().get(i).setVisible(this.MyDisplay.setDistanceIndicator(this.MyDisplay.getRadarTargetDistance().get(i), this.getRadarModel().getRadarTargetList().get(i)));
					}
					if(this.getRadarModel().getRadarTargetList().get(i).getSignature()>=0&&this.getMyAircraft().getRadar().getIFFDistance()>=Locating.getDistance(this.getMyAircraft(), this.getRadarModel().getRadarTargetList().get(i)))
					{
						this.MyDisplay.getRadarTarget().get(i).setImage(new Image("/Image/Indicator/TargetFriendRadar.png"));
					}
					else this.MyDisplay.getRadarTarget().get(i).setImage(new Image("/Image/Indicator/TargetRadar.png"));
				}
			}
			else
			{
				for(int i = 0;i<this.MyDisplay.getRadarTarget().size();i++)
				{
					this.MyDisplay.getRadarTarget().get(i).setVisible(false);
					this.MyDisplay.getRadarTargetDistance().get(i).setVisible(false);
				}
			}
			
			if(!this.getMyAircraft().getExternalLoadList().isEmpty())
			{
				this.RadarAAMIndicateNumber = 0;
				this.InfraredAAMIndicateNumber = 0;
				for(int i=0;i<this.getMyAircraft().getExternalLoadList().size();i++)
				{
					this.MyDisplay.getMissleTarget().get(i).setVisible(false);
					this.MyDisplay.getMissleTargetDistance().get(i).setVisible(false);			
					
					if(this.getMyAircraft().getExternalLoadList().get(i).getPerformance().getProperty().contains("Radar"))
					{
						this.MyDisplay.getMissleTarget().get(i).setImage(new Image("/Image/Indicator/TargetFriendRadar.png"));
						this.MyDisplay.getMissleTargetDistance().get(i).setFill(Color.GREEN);
						if(this.getMyAircraft().getExternalLoadList().get(i).isEffective() == true&&this.RadarAAMIndicateNumber<this.getMyAircraft().getRadar().getMaxIndicateRadarAAM())
						{
							this.RadarAAMIndicateNumber++;
							this.MyDisplay.getMissleTarget().get(i).setVisible(this.MyDisplay.setIndicatorTarget(MyDisplay.getMissleTarget().get(i), this.getMyAircraft().getExternalLoadList().get(i)));
							this.MyDisplay.getMissleTargetDistance().get(i).setVisible(this.MyDisplay.setDistanceIndicator(MyDisplay.getMissleTargetDistance().get(i), this.getMyAircraft().getExternalLoadList().get(i)));
						}
					}
					else if(this.getMyAircraft().getExternalLoadList().get(i).getPerformance().getProperty().contains("Infrared"))
					{
						this.MyDisplay.getMissleTarget().get(i).setImage(new Image("/Image/Indicator/TargetFriendInfrared.png"));
						this.MyDisplay.getMissleTargetDistance().get(i).setFill(Color.RED);
						if(this.getMyAircraft().getExternalLoadList().get(i).isEffective() == true&&this.InfraredAAMIndicateNumber<this.getMyAircraft().getRadar().getMaxIndicateInfraredAAM())
						{
							this.InfraredAAMIndicateNumber++;
							this.MyDisplay.getMissleTarget().get(i).setVisible(this.MyDisplay.setIndicatorTarget(MyDisplay.getMissleTarget().get(i), this.getMyAircraft().getExternalLoadList().get(i)));
							this.MyDisplay.getMissleTargetDistance().get(i).setVisible(this.MyDisplay.setDistanceIndicator(MyDisplay.getMissleTargetDistance().get(i), this.getMyAircraft().getExternalLoadList().get(i)));
						}
					}
				}
			}
		}
		else
		{
			this.MyDisplay.getRadarUpperLine().setVisible(false);
			this.MyDisplay.getRadarLowerLine().setVisible(false);
			for(int i = 0;i<this.getMyAircraft().getExternalLoadList().size();i++)
			{
				this.MyDisplay.getMissleTarget().get(i).setVisible(false);
				this.MyDisplay.getMissleTargetDistance().get(i).setVisible(false);
			}
			for(int i = 0;i<this.MyDisplay.getRadarTarget().size();i++)
			{
				this.MyDisplay.getRadarTarget().get(i).setVisible(false);
				this.MyDisplay.getRadarTargetDistance().get(i).setVisible(false);
			}
		}
	}
	
	/**
	 * Update the view of InfraredSystem(the Infrared Field and indicators of targets) if the aircraft has it
	 * this will be called in GameWindowController
	 */
	
	public void InfraredViewUpdate()
	{
		if(this.isInfraredMode() == true)
		{
			if(this.getInfraredModel().getInfraredLockField()<5)
			{
                this.MyDisplay.setIndicatorLine(this.MyDisplay.getInfraredUpperLine(), this.getInfraredModel().getInfraredLockDirection());
                this.MyDisplay.getInfraredUpperLine().setVisible(true);
                this.MyDisplay.getInfraredLowerLine().setVisible(false);
			}
			else
			{
                this.MyDisplay.setIndicatorLine(this.MyDisplay.getInfraredUpperLine(), this.getInfraredModel().getInfraredLockDirection()+this.getInfraredModel().getInfraredLockField());
                this.MyDisplay.getInfraredUpperLine().setVisible(true);
                this.MyDisplay.setIndicatorLine(this.MyDisplay.getInfraredLowerLine(), this.getInfraredModel().getInfraredLockDirection()-this.getInfraredModel().getInfraredLockField());
                this.MyDisplay.getInfraredLowerLine().setVisible(true);
			}
			
			if(this.getInfraredModel().getInfraredTarget()!=null)
			{
				this.MyDisplay.getInfraredTargetView().setVisible(false);
				this.MyDisplay.getInfraredTargetView().setVisible(this.MyDisplay.setIndicatorTarget(this.MyDisplay.getInfraredTargetView(), this.getInfraredModel().getInfraredTarget()));
			}
		}
		else
		{
			this.MyDisplay.getInfraredUpperLine().setVisible(false);
			this.MyDisplay.getInfraredLowerLine().setVisible(false);
			this.MyDisplay.getInfraredTargetView().setVisible(false);
		}
	}
	
	/**
	 * Implement an aircraft control according to the key pressed
	 * @param Code the Key pressed
	 */
	
	public void LogicImplementWithInput(String Code)
	{
	    if(Code.equals(this.Setting.getPitchCode())){
	    	this.Pitch();
	        isControl = false;
	    }
	    else if(Code.equals(this.Setting.getDiveCode())){
	    	this.Dive();
            isControl = false;
        }
	    else if(Code.equals(this.Setting.getThrottleDownCode())){
	    	this.ThrottleDOWN();
        }
	    else if(Code.equals(this.Setting.getThrottleUPCode())){
	    	this.ThrottleUP();
	    }
	    else if(Code.equals(this.Setting.getTrimCode())){
	    	if(isControl == false)
	    	{
	    		isControl = true;
	    	}
	    	else isControl = false;
	    }
	    else if(Code.equals(this.Setting.getFiringCode())){
	    	this.Firing();
	    }
	    else if(Code.equals(this.Setting.getAirbrakeCode())){
	    	this.setAirBrake();
	    }
	    else if(Code.equals(this.Setting.getOpenInfraredModeCode())){
	    	this.setInfraredMode();
	    }
	    else if(Code.equals(this.Setting.getOpenRadarModeCode())){
	    	this.setRadarMode();
	    }
	    else if(Code.equals(this.Setting.getLaunchCode())){
	    	this.Launch();
	    }
	    else if(Code.equals(this.Setting.getChangeWeaponCode())){
	    	this.setCurrentWeapon();
	    }
	    else if(Code.equals(this.Setting.getHelmetAimingCodeUP())){
	    	this.HeadTurning(true);
	    }
	    else if(Code.equals(this.Setting.getHelmetAimingCodeDown())){
	    	this.HeadTurning(false);
        }
	    else if(Code.equals(this.Setting.getCommandCodeUP())){
	    	this.setCommandOrder(1);
	    }
	    else if(Code.equals(this.Setting.getCommandCodeDown())){
	    	this.setCommandOrder(-1);
        }
	    else if(Code.equals(this.Setting.getChaffCode())){
	    	this.LaunchChaff();
	    }
	    else if(Code.equals(this.Setting.getFlareCode())){
	    	this.LaunchFlares();
        }
		if(isControl == true)
		{
			this.Trim();
		}
	}
	
	/**
	* Observe an aircraft and check whether it can be indicated
	* Tagert should be within the range of 3000
	* @param aircraft the aircraft observed
	*/
	
	public void Observe(Aircraft aircraft)
	{	
        this.MyDisplay.getIndicator().get(this.viewPointer).setVisible(false);	
        if(Locating.getDistance(this.getMyAircraft(), aircraft)<this.getDetectRange()&&aircraft.getLife() > 0)
        {
            this.MyDisplay.getIndicator().get(this.viewPointer).setVisible(this.MyDisplay.setIndicatorTarget(this.MyDisplay.getIndicator().get(this.viewPointer), aircraft));
        }
		if(this.getMyAircraft().getRadar()!=null&&aircraft.getSignature()>=0&&this.getMyAircraft().getRadar().getIFFDistance()>=Locating.getDistance(this.getMyAircraft(), aircraft))
		{
			this.MyDisplay.getIndicator().get(this.viewPointer).setImage(new Image("/Image/Indicator/TargetFriendRadar.png"));
		}
		else this.MyDisplay.getIndicator().get(this.viewPointer).setImage(new Image("/Image/Indicator/TargetNormal.png"));
		
		this.viewPointer++;
		
		if(this.viewPointer>Locating.minNum(8, this.maxViewPointer))
		{
			this.viewPointer = 0;
		}
	}	
}
