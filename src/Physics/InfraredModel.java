package Physics;

import Object.ExternalLoad;
import Property.Radar;
import Object.Object;

/**
 * DamgeModel is used for calculate and implement infrared event
 * @author WangShuzheng
 */

public class InfraredModel {
	
	/**
	 * Attributes
	 */
	
	private Object owner;
	private Radar myRadar;
	
	private double InfraredLockField;
	private double InfraredLockRange;
	private double InfraredAttackAngle;
	private double InfraredLockDirection;
	private Object InfraredTarget;
	
	/**
	 * Constructor
	 * @param owner owner of this Infrared model
	 * @param myRadar radar which use this model(IRST is in radar class)
	 */
	
	public InfraredModel(Object owner, Radar myRadar) {
		super();
		this.owner = owner;
		this.myRadar = myRadar;
	}
	
	public double getInfraredLockField() {
		return InfraredLockField;
	}

	public double getInfraredLockRange() {
		return InfraredLockRange;
	}

	public double getInfraredAttackAngle() {
		return InfraredAttackAngle;
	}
	
	public double getInfraredLockDirection() {
		return InfraredLockDirection;
	}
	
	public void setInfraredLockDirection(double infraredLockDirection) {
		InfraredLockDirection = infraredLockDirection;
	}

	public Object getInfraredTarget() {
		return InfraredTarget;
	}

	/**
	 * Create infrared detect envelope by infrared weapon and helmet direction
	 * @param weapon infrared weapon
	 * @param direction helmet direction
	 * @return if the infrared can be open, return true
	 */
	
	public boolean CreateEnvelope(ExternalLoad weapon, double direction)
	{
		this.InfraredTarget = null;
		if(weapon!=null)
		{
			if(weapon.getPerformance().getProperty().contains("Infrared"))
			{
				this.InfraredLockDirection = direction;
				this.InfraredLockRange = weapon.getPerformance().getLockRange();
				this.InfraredLockField = weapon.getPerformance().getLockField();
				this.InfraredAttackAngle = weapon.getPerformance().getMaxAttackField();
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Detect an object by infrared system, the infrared missles lock or IRST
	 * the target need to be covered in the infrared detect envelop and is alive
	 * @param object the object detected
	 */
	
	public void InfraredDetect(Object object)
	{
		if(this.InfraredTarget == object)
		{
			this.InfraredTarget = null;
		}
	
		if(object.getLife()>0)
		{
			if(Locating.getDistance(owner, object)<=this.InfraredLockRange&&Locating.isInsideField(Locating.getRelativeAngle(owner,object), owner.getVelocity().getDirection()+owner.getAOA()+this.InfraredLockDirection, this.InfraredLockField)&&owner.getVelocity().getAngle(object.getVelocity())<=this.InfraredAttackAngle)
			{
				this.InfraredTarget = object;
			}
			else if(Locating.getDistance(owner, object)<=myRadar.getIRSTRange()&&Locating.isInsideField(Locating.getRelativeAngle(owner,object), owner.getVelocity().getDirection()+owner.getAOA(), myRadar.getIRSTField()))
			{
				this.InfraredTarget = object;
			}
		}
	}
}
