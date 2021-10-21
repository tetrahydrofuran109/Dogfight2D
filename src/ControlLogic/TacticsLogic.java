package ControlLogic;

import Object.Aircraft;
import Object.ExternalLoad;
import Object.Object;
import Physics.Earth;
import Physics.Locating;
import Physics.RadarModel;

/**
 * class TacticsLogic is to define the methods used to calculate the tactics data the ai need and several procedure they need to excute
 * such as the calculation of the missle launch envelope, Identification friend or foe and implementation of command guide of missle
 * @author WangShuzheng
 */

public class TacticsLogic {
	
	/**
	 * this method is used to determind the relationship between A and B
	 * @param A an object
	 * @param B another object
	 * @return true if they are enemy
	 */
	
	public static boolean IsEnemy(Object A, Object B)
	{
		if(A.getSignature()>=0)
		{
			if(B.getSignature()<0)
			{
				return true;
			}
			else return false;
		}
		else 
		{
			if(B.getSignature()>=0)
			{
				return true;
			}
			else return false;
		}
	}
	
	/**
	 * calculate the ImpulsePerGravity of a missle(totalImpulse/Gravity), which is helpful to calculate the missle launch envelope
	 * @param missle the missle used for calculate
	 * @return ImpulsePerGravity of missle
	 */
	
	public static double MissleImpulsePerGravity(ExternalLoad missle)
	{
		double totalImpulse = missle.getPerformance().getThrustA()*missle.getPerformance().getThrustATime()*Earth.TimeInterval/1000 + missle.getPerformance().getThrustB()*missle.getPerformance().getThrustBTime()*Earth.TimeInterval/1000;
		return totalImpulse/missle.getMass()/Earth.g;
	}
	
	/**
	 * calculate a coefficient by Drag of a missle and the drag of the refernce drag, which is helpful to calculate the missle launch envelope
	 * @param missle the missle used for calculate
	 * @return DragCorrection coefficient
	 */
	
	public static double DragCorrection(ExternalLoad missle)
	{
		return Math.pow(missle.getPerformance().getRefArea()*missle.getPerformance().getSuperSonicCd0()/0.024,3);
	}
	
	/**
	 * calculate the missle launch envelope, which is helpful to choose the proper weapon by ai
	 * @param missle the missle used for calculate
	 * @param target The target to be attacked
	 * @return the max launch range when the missle is used to attack the target
	 */
	
	public static double EstimateMissleLauchEnvelope(ExternalLoad missle, Object target)
	{
		double estimateHeadOnRange = MissleImpulsePerGravity(missle)*1000*(2*missle.getOwner().getY()/1000+10)/57/DragCorrection(missle);
		double estimateChaseRange = MissleImpulsePerGravity(missle)*1000*(missle.getOwner().getY()/2000+3)/57/DragCorrection(missle);
		double Correction;
		if(Locating.getRelativeAngle(missle.getOwner(), target)>45&&Locating.getRelativeAngle(missle.getOwner(), target)<135)
		{
			Correction = 0.8;
		}
		else if(Locating.getRelativeAngle(missle.getOwner(), target)>225&&Locating.getRelativeAngle(missle.getOwner(), target)<315)
		{
			Correction = 1.2;
		}
		else Correction = 1;
		if(Locating.AttackDirection(missle.getOwner(), target)==1)
		{
			if(missle.getPerformance().getProperty().contains("Command"))
			{
				return Locating.maxNum(1500, Correction*estimateChaseRange);
			}
			else
			{
				if(missle.getPerformance().getProperty().contains("Radar"))
				{
					return Locating.maxNum(1500, Locating.minNum(Correction*estimateChaseRange, RadarModel.getActualDetecRange(target.getRCS(), missle.getPerformance().getLockRange())));
				}
				else return Locating.maxNum(1500, Locating.minNum(Correction*estimateChaseRange, missle.getPerformance().getLockRange()));
			}
		}
		else 
		{
			if(missle.getPerformance().getMaxAttackField()==180)
			{
				if(missle.getPerformance().getProperty().contains("Command"))
				{
					return Locating.maxNum(3000, Correction*Correction*estimateHeadOnRange);
				}
				else
				{
					if(missle.getPerformance().getProperty().contains("Radar"))
					{
						return Locating.maxNum(3000, Locating.minNum(Correction*Correction*estimateHeadOnRange, RadarModel.getActualDetecRange(target.getRCS(), missle.getPerformance().getLockRange())));
					}
					else return Locating.maxNum(3000, Locating.minNum(Correction*Correction*estimateHeadOnRange, missle.getPerformance().getLockRange()));
				}
			}
			else return 0;
		}
	}
	
	/**
	 * this procedure is used to prevent the aircraft from crashed on the ground
	 * it will return true and excute the procedure when aircraft is below 2000 and continues going down
	 * @param aircraft the aircraft who excute this procedure
	 * @return true if this procedure is needed
	 */
	
	public static boolean AvoidCrash(Aircraft aircraft)
	{
		boolean Warning = false;
		if(aircraft.getY()<2000)
		{
			if(aircraft.getVelocity().getValueY()<0)
			{
				Warning = true;
			}
		}
		if(Warning == true)
		{
			if(aircraft.getVelocity().getDirection()<270)
			{
				aircraft.getMyPlayer().Dive();
			}
			else aircraft.getMyPlayer().Pitch();
		}
		return Warning;
	}
	
	/**
	 * this procedure is used to choose a proper weapon to use
	 * the best weapon is the weapon whose envelop covered the target and has the min lauch range
	 * @param aircraft the aircraft who excute this procedure
	 * @param target The target to be attacked
	 * @return FinalWeapon the weapon selected
	 */
	
	public static ExternalLoad ChooseMinRangeWeapon(Aircraft aircraft, Object target)
	{
		ExternalLoad FinalWeapon = null;
		for(int i=0; i<aircraft.getExternalLoadList().size();i++)
		{
			if(FinalWeapon == null&&aircraft.getExternalLoadList().get(i).isEffective()==true&&aircraft.getExternalLoadList().get(i).isLauched()==false
					&&EstimateMissleLauchEnvelope(aircraft.getExternalLoadList().get(i),target)>Locating.getDistance(aircraft, target))
			{
				FinalWeapon = aircraft.getExternalLoadList().get(i);
			}
			
			if(aircraft.getExternalLoadList().get(i).isEffective()==true&&aircraft.getExternalLoadList().get(i).isLauched()==false
					&&EstimateMissleLauchEnvelope(aircraft.getExternalLoadList().get(i),target)>Locating.getDistance(aircraft, target)&&EstimateMissleLauchEnvelope(aircraft.getExternalLoadList().get(i),target)<EstimateMissleLauchEnvelope(FinalWeapon,target))
			{
				FinalWeapon = aircraft.getExternalLoadList().get(i);
			}
		}
		return FinalWeapon;
	}
	
	/**
	 * this procedure is used set Command order to guide missles which have command guide function by AI
	 * @param aircraft the aircraft who excute this procedure
	 * @param target The target to be attacked
	 */
	
	public static void AutoCommand(Aircraft aircraft, Object target)
	{
		for(int i = 0;i<aircraft.getExternalLoadList().size();i++)
		{
			if(aircraft.getExternalLoadList().get(i).getPerformance().getProperty().contains("Command")&&aircraft.getExternalLoadList().get(i).isLauched()==true&&aircraft.getExternalLoadList().get(i).isEffective()==true)
			{
				aircraft.getMyPlayer().setCommandOrder(Locating.Guiding(aircraft.getExternalLoadList().get(i), target));
			}
		}
	}
	
	/**
	 * this procedure is turn the helmet aimer by AI
	 * @param aircraft the aircraft who excute this procedure
	 * @param target The target to be attacked
	 */
	
	public static void AutoHeadTurning(Aircraft aircraft, Object target)
	{
		if(Locating.Guiding(aircraft, target)==1)
		{
			aircraft.getMyPlayer().HeadTurning(true);
		}
		else if(Locating.Guiding(aircraft, target)==-1)
		{
			aircraft.getMyPlayer().HeadTurning(false);
		}
	}
	
	/**
	 * to detect and warn the missle lauched from enemy by AI
	 * @param AIObserver the AI excute this method
	 * @param object the enemy
	 * @return true if is in range and owner is caputured
	 */
	
	@SuppressWarnings("unused")
	public static boolean checkWarningByView(Object AIObserver, Object object)
	{
		for(int i = 0;i<object.getExternalLoadList().size();i++)
		{
			if(Locating.getDistance(AIObserver,object.getExternalLoadList().get(i))<960&&object.getExternalLoadList().get(i).isLauched()==true&&object.getExternalLoadList().get(i).isEffective()==true)
			{
				return true;
			}
			else return false;
		}
		return false;
	}
}
