package Physics;

import ControlLogic.TacticsLogic;
import Object.Explosion;
import Object.ExternalLoad;
import Object.GunBullet;
import Object.Object;

/**
 * DamgeModel contains classes for calculate damage event
 * @author WangShuzheng
 */

public class DamageModel {
	
	/**
	 * HitRadius defines the area which can cause basic hit damage event
	 */
	
	public static final double HitRadius = 30;
	
	/**
	 * GunDamage event is caused by gun bullet
	 * the damage area is basic hit area, no friend damage
	 * if the piercing armor ability of gun is better than armor of object, the damage value is gun power, else just 1
	 * @param gun gun which the bullet belons to
	 * @param object the assumed damaged object
	 * @return damage value
	 */
	
	public static boolean GunDamage(GunBullet gun, Object object)
	{
		if(gun.isEffective()==true)
		{
			if(Locating.isAInRadiusOfB(gun,object,HitRadius)&&TacticsLogic.IsEnemy(gun, object))
			{
				if(gun.getPiercingArmor()>object.getArmor())
				{
					object.getDamage(gun.getGunPower());
				}
				else object.getDamage(1);
				gun.setEffective(false);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * KneticEnergyDamage event is caused by ExternalLoad
	 * the damage area is basic hit area, no friend damage
	 * the KneticEnergyDamage is calculate according to knetic energy
	 * @param load externalLoad which create the event
	 * @param object the assumed damaged object
	 * @return damage value
	 */
	
	public static boolean KneticEnergyDamage(ExternalLoad load, Object object)
	{
		if(load.isEffective()==true&&load.isLauched()==true)
		{
			if(Locating.isAInRadiusOfB(load,object,HitRadius+load.getPerformance().getExplosionRange())&&TacticsLogic.IsEnemy(load, object))
			{
				object.getDamage(Math.sqrt(load.getKineticEnergy())/98);
				if(load.getPerformance().getExplosionRange()==0)
				{
					object.getDamage(25*load.getPerformance().getTNTMass());
				}
				load.setEffective(false);
				return true;
			}
		}
		return false;
	}

	/**
	 * ExplosionDamage event is caused by Explosion
	 * the damage area is explosion range, every in this area will be damaged
	 * the damage value is determind by tnt mass of explosion
	 * the closer to the explosion center, the greater the damage value is
	 * @param explosion explosion object
	 * @param object the assumed damaged object
	 * @return damage value
	 */
	
	public static boolean ExplosionDamage(Explosion explosion, Object object)
	{
		if(Locating.isAInRadiusOfB(object, explosion, explosion.getRadius())&&explosion.isEffective()==true)
		{
			object.getDamage(50*(1-Locating.getDistance(explosion, object)/explosion.getRadius())*explosion.getTNTMass());
			return true;
		}
		return false;
	}
}
