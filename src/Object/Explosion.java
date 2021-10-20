package Object;

import Physics.DamageModel;

/**
 * Create an explosion object with a max radius
 * it start with a radius of 30
 * it will expanding until the radius get into the max value
 * objects entering this object area will be damaged
 * @author WangShuzheng
 */

public class Explosion extends Object {
	
	/**
	 * Attributes of an explosion
	 * isEffective will turn false after its duration
	 */
	
	private double TNTMass;
	private double ExplosionRange;
	
	public static final double ExplosionRatio = 5;
	
	private double Radius;
	private double ExpandingRate;
	private int Duration;
	private boolean isEffective;
	
	/**
	 * Constructor
	 * @param owner Creater of this explosion, defined its start location
	 * @param tNTMass power of this explosion
	 * @param explosionRange use to calculate the max expanding range of this explosion
	 */
	
	public Explosion(Object owner, double tNTMass, double explosionRange) {
		super();
		this.TNTMass = tNTMass;
		this.ExplosionRange = ExplosionRatio*explosionRange+DamageModel.HitRadius;
		this.isEffective = false;
		this.x = owner.x;
		this.y = owner.y;
		this.Radius = DamageModel.HitRadius;
		this.ExpandingRate = 2;
		this.Duration = (int)(ExplosionRange/ExpandingRate);
		this.isEffective = true;
	}

	public double getTNTMass() {
		return TNTMass;
	}
	
	public double getExplosionRange() {
		return ExplosionRange;
	}
	
	public double getRadius() {
		return Radius;
	}

	public double getExpandingRate() {
		return ExpandingRate;
	}

	public int getDuration() {
		return Duration;
	}
	
	public void setDuration(int duration) {
		Duration = duration;
	}

	public boolean isEffective() {
		return isEffective;
	}

	public void setEffective(boolean isEffective) {
		this.isEffective = isEffective;
	}
	
	/**
	 * Excute the expanding of this explosion
	 * will be called in move method of this explosion
	 */
	
	public void Expanding()
	{
		if(this.Radius<this.ExplosionRange)
		{
			this.Radius = this.Radius + this.ExpandingRate;
		}
	}
	
	/**
	 * move method of this explosion
	 * it will expanding to max range during the duration time
	 */
	
	public void move()
	{
		if(this.Duration>0)
		{
			this.Expanding();
			this.Duration--;
		}
		else
		{
			this.isEffective = false;
		}
	}
}
