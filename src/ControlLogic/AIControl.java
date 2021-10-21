package ControlLogic;

import Object.Aircraft;
import Physics.Locating;

/**
 * class for the AI control
 * @author WangShuzheng
 */

public class AIControl extends ControlLogic {
	
	/**
	 * Attributes for AI control
	 * CruiseAltitude defines the altitude the aircraft should stay in if it has no target
	 * LauchMissleInterval defines the launch interval between two missle
	 * isControl is false when turning, if it is true, the aircraft will trim automatically
	 */
	
	private int CruiseAltitude;
	private Aircraft TargetAircraft;
	private int LauchMissleInterval;
	
	private boolean isControl;
	
	public boolean isControl() {
		return isControl;
	}

	public void setControl(boolean isControl) {
		this.isControl = isControl;
	}
	
	public int getCruiseAltitude() {
		return CruiseAltitude;
	}

	public void setCruiseAltitude(int cruiseAltitude) {
		CruiseAltitude = cruiseAltitude;
	}

	public Aircraft getTargetAircraft() {
		return TargetAircraft;
	}

	public void setTargetAircraft(Aircraft targetAircraft) {
		TargetAircraft = targetAircraft;
	}
	
   /**
	* Observe an aircraft and check whether it can be the target
	* Tagert should be within the range of 3000 or detected by radar, also it should be an enemy not friend
	* The closest target is prior to others
	* @param aircraft the observed aircraft
	*/
	public void Observe(Aircraft aircraft)
	{
		if(this.isRadarMode()==false)
		{
			setRadarMode();
		}
		
		if(this.isInfraredMode()==false)
		{
			setInfraredMode();
		}
		
		if(aircraft.getLife()>0&&TacticsLogic.IsEnemy(this.getMyAircraft(), aircraft))
		{
			if(Locating.getDistance(this.getMyAircraft(), aircraft)<=this.getDetectRange()||this.getRadarModel().getRadarTargetList().contains(aircraft))
			{
				if(this.TargetAircraft == null)
				{
					this.TargetAircraft = aircraft;
				}
				else
				{
					if(this.TargetAircraft.getLife()==0&&Locating.getDistance(this.getMyAircraft(), aircraft)>this.getDetectRange()&&!this.getRadarModel().getRadarTargetList().contains(aircraft))
					{
						this.TargetAircraft = null;
					}
					else if(Locating.getDistance(this.getMyAircraft(), aircraft)<Locating.getDistance(this.getMyAircraft(), this.TargetAircraft))
					{
						this.TargetAircraft = aircraft;
					}
				}
			}
		}
	}
	
   /**
	* Cruise method
	* The aircraft will cruise in the cruise altitude if there is no target
	*/
	public void Cruise()
	{
		if(this.getMyAircraft().getThrottle()<100&&this.getMyAircraft().getIAS()<this.getMyAircraft().getPerformance().getLimitIAS())
		{
			this.ThrottleUP();
		}
		
		if(this.getMyAircraft().getThrottle()<100&&this.getMyAircraft().getIAS()<this.getMyAircraft().getPerformance().getLimitIAS())
		{
			this.ThrottleUP();
		}

		if(this.getMyAircraft().getVelocity().getDirection()>90 && this.getMyAircraft().getVelocity().getDirection()<270)
		{
			if(this.getMyAircraft().getY()>this.getCruiseAltitude())
			{
				this.Pitch();
			}
			else if(this.getMyAircraft().getY()<this.getCruiseAltitude())
			{
				this.Dive();
			}
			if(this.getMyAircraft().getVelocity().getValueY()<0)
			{
				this.Dive(); 
			}
			else
			{   
				this.Pitch();
			}
		}
		else
		{
			if(this.getMyAircraft().getY()>this.getCruiseAltitude())
			{
				this.Dive();
			}
			else if(this.getMyAircraft().getY()<this.getCruiseAltitude()) 
			{
				this.Pitch();		
			}
			if(this.getMyAircraft().getVelocity().getValueY()<0)
			{
				this.Pitch();
			}
			else 
			{
				this.Dive();
			}
		}
		
		this.isControl = true;
	}
	
   /**
	* Act method defines the ai aircraft's reaction to different condition
	*/
	public void Act()
	{
		if(this.getRadarModel().isRadarWarning()&&this.getMyAircraft().getAntiRadarValue()<150)
		{
			this.LaunchChaff();
		}
		if(this.TargetAircraft!=null)
		{
			if(TacticsLogic.checkWarningByView(this.getMyAircraft(), this.TargetAircraft))
			{
				if(this.getMyAircraft().getAntiInfraredValue()<150)
				{
					this.LaunchFlares();
				}
				if(this.getMyAircraft().getAntiRadarValue()<150)
				{
					this.LaunchChaff();
				}
			}
			
			if(!TacticsLogic.AvoidCrash(this.getMyAircraft()))
			{
			   /**
				* Turning, head to the target
				*/
				
				if(Locating.Guiding(this.getMyAircraft(), this.TargetAircraft)==1)
				{
					this.Pitch();
					this.isControl = false;
				}
				else
				{
					this.Dive();
					this.isControl = false;
				}
			}
			
		   /**
			* Throttle up when engaged with target
			*/
			
			if(this.getMyAircraft().getIAS()<this.getMyAircraft().getPerformance().getLimitIAS())
			{
			    this.ThrottleUP();
			}
			
		   /**
			* Shoot at the target if possible
			*/
			
			if(Locating.isInsideField(Locating.getRelativeAngle(this.getMyAircraft(),this.TargetAircraft), this.getMyAircraft().getVelocity().getDirection()+this.getMyAircraft().getAOA(), 5)&&Locating.getDistance(this.getMyAircraft(), this.TargetAircraft)<1000)
			{
				this.Firing();
			}
			
		   /**
			* Use of air to air missle
			* Estimate the Launch envelope of every weapon the aircraft has
			* Choose the best one and set current weapon to it
			* lauch and wait for next lauch
			* If it has helmet aimer, make use of it
			* AutoComannd guide if the missle has such function
			*/
			
			if(!this.getMyAircraft().getExternalLoadList().isEmpty())
			{
				if(TacticsLogic.ChooseMinRangeWeapon(this.getMyAircraft(), this.TargetAircraft)!=null)
				{
					this.CurrentWeapon = TacticsLogic.ChooseMinRangeWeapon(this.getMyAircraft(), this.TargetAircraft);
					if(this.getMyAircraft().getHelmet()!=null&&this.getMyAircraft().getHelmet().getAllowedWeapon().contains(this.CurrentWeapon.getPerformance().getName())&&Locating.getDistance(this.getMyAircraft(), this.TargetAircraft)<=this.getDetectRange())
					{
						TacticsLogic.AutoHeadTurning(this.getMyAircraft(), this.TargetAircraft);
						if(this.LauchMissleInterval==0&&Locating.isInsideField(Locating.getRelativeAngle(this.getMyAircraft(),this.TargetAircraft), this.getMyAircraft().getVelocity().getDirection()+this.getMyAircraft().getAOA()+this.HelmetDirection, this.CurrentWeapon.getPerformance().getLockField()/2))
						{
							this.Launch();
							this.LauchMissleInterval = 500;
						}
					}
					else
					{
						if(this.LauchMissleInterval==0&&Locating.isInsideField(Locating.getRelativeAngle(this.getMyAircraft(),this.TargetAircraft), this.getMyAircraft().getVelocity().getDirection()+this.getMyAircraft().getAOA(), this.CurrentWeapon.getPerformance().getLockField()/2))
						{
							this.Launch();
							this.LauchMissleInterval = 500;
						}
					}
				}
				TacticsLogic.AutoCommand(this.getMyAircraft(), this.getTargetAircraft());
			}
			if(this.LauchMissleInterval > 0)
			{
				this.LauchMissleInterval--;
			}
		}
		else Cruise();
		
	   /**
		* Throttle down and airbrake on when overspeed
		*/
		
		if(this.getMyAircraft().getIAS()>=this.getMyAircraft().getPerformance().getLimitIAS()||this.getMyAircraft().getMach()>=this.getMyAircraft().getPerformance().getLimitMach())
		{
			this.getMyAircraft().setAirBrake(true);
			this.ThrottleDOWN();
		}
		else this.getMyAircraft().setAirBrake(false);
	}
	
   /**
	* logicImplent method
	* excute the AI action, will be called in GameWindowController
	*/
	public void LogicImplement()
	{
		if(this.getMyAircraft().getLife()>0)
		{
			if(this.isControl==true)
			{
				this.Trim();
			}
			this.Act();
		}
	}

	
   /**
	* Construct
	* @param cruiseAltitude cruise altitude
	*/
	public AIControl(int cruiseAltitude) {
		super();
		this.isControl = true;
		this.CruiseAltitude = cruiseAltitude;
		this.LauchMissleInterval = 0;
		if(this.CruiseAltitude == 0)
		{
			this.CruiseAltitude = 1000;
		}
	}
}
