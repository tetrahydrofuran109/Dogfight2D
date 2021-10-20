package Physics;

import java.util.ArrayList;

import Object.ExternalLoad;
import Object.Object;
import Property.Radar;

/**
 * RadarModel is used for calculate and implement radar event
 * @author WangShuzheng
 */

public class RadarModel {
	
	/**
	 * Attributes
	 */
	
	private Object owner;
	private Radar myRadar;
	
	private int RadarScanPointer;
	private ArrayList<Object> radarTargetList;
	
	/**
	 * Constructor
	 * @param owner owner of this Radar model
	 * @param myRadar radar which use this model
	 */
	
	public RadarModel(Object owner, Radar myRadar) {
		super();
		this.owner = owner;
		this.myRadar = myRadar;
		this.radarTargetList = new ArrayList<Object>();
		this.RadarScanPointer = 0;
	}
	
	public ArrayList<Object> getRadarTargetList() {
		return radarTargetList;
	}
	
	public void RadarScanPointerIncreament() {
		RadarScanPointer++;
	}
	
	/**
	 * Calculate the actual detect range of a radar detecter
	 * the detect range is Proportional to the 1/4th power of RCS
	 * @param RCS Radar reflection cross-sectional area
	 * @param Range standard detect range(in this game, standard data is the range when detect target with RCS=2)
	 * @return actual detect range
	 */

	public static double getActualDetecRange(double RCS,double Range)
	{
		return Range*Math.sqrt(Math.sqrt(RCS))/Math.sqrt(Math.sqrt(2));
	}
	
	/**
	 * Detect an object by radar system
	 * the target need to be covered in the radar detect envelop and is alive
	 * also it will implement the function of radar guide for radar missle
	 * some radar has look down limitaion, it can't detect target below them for more than a certain angle
	 * @param object the object detected
	 */
	
	public void radarDetect(Object object)
	{
		if(RadarScanPointer == 0)
		{
			if(radarTargetList.contains(object))
			{
				radarTargetList.remove(object);
			}
			if(Locating.getDistance(owner, object)<=myRadar.getActualDetecRange(object.getRCS())&&Locating.isInsideField(Locating.getRelativeAngle(owner, object), owner.getVelocity().getDirection()+owner.getAOA(), myRadar.getMaxDetectField()))
			{
				if(Locating.getRelativeAngle(owner, object)>360-myRadar.getMaxLookDownAngle()||Locating.getRelativeAngle(owner, object)<180+myRadar.getMaxLookDownAngle())
				{
					if(radarTargetList.size()<myRadar.getMaxTagertNumber())
					{
						if(object.getLife() > 0)
						{
							radarTargetList.add(object);
						}
					}
				}
			}
			this.RadarGuide(object);
	    }
		
		if(RadarScanPointer>=myRadar.getScanInterval())
		{
			this.RadarScanPointer = -1;
		}
	}
	
	/**
	 * give the radar guide missle the target detected by radar
	 * @param object the target
	 */
	
	public void RadarGuide(Object object)
	{
		if(radarTargetList.contains(object))
		{			
			for(int j=0;j<owner.getExternalLoadList().size();j++)
			{
				if(owner.getExternalLoadList().get(j).getPerformance().getProperty().contains("RadarAAM"))
				{
					if(Locating.getDistance(owner, object)<=RadarModel.getActualDetecRange(object.getRCS(), owner.getExternalLoadList().get(j).getPerformance().getLockRange()))
					{
						if(owner.getExternalLoadList().get(j).getCapturedObject()==null)
						{
							owner.getExternalLoadList().get(j).setCapturedObject(object);
						}
						else if(Locating.getDistance(owner.getExternalLoadList().get(j), object)<Locating.getDistance(owner.getExternalLoadList().get(j), owner.getExternalLoadList().get(j).getCapturedObject()))
						{
							owner.getExternalLoadList().get(j).setCapturedObject(object);
						}
					}
				}
			}
		}
	}
	
	/**
	 * give the missle the target detected by helmet aimer
	 * @param object the target
	 * @param helmetDirection the sight direction of helmet aimer
	 * @param detectRange the visual detect range, defined to be 3000
	 */
	
	public void helmetGuide(Object object, double helmetDirection, double detectRange)
	{
		for(int j=0;j<owner.getExternalLoadList().size();j++)
		{
			if(owner.getExternalLoadList().get(j).getPerformance().getProperty().contains("SemiRadarAAM"))
			{
				if(Locating.isInsideField(Locating.getRelativeAngle(owner,object), owner.getVelocity().getDirection()+owner.getAOA()+helmetDirection, myRadar.getMaxDetectField())&&Locating.getDistance(owner,object)<=detectRange)
				{
					owner.getExternalLoadList().get(j).setCapturedObject(object);
				}
			}
		}
	}
	
	/**
	 * the radar warning function, to detect and warn the radar missle lauched from enemy
	 * @param load the missle from enemy
	 * @param enemyRadar the radar which guide this missle
	 * @return true if is in range of radar warning distance and owner is captured by enemy
	 */
	
	public boolean RadarWarning(ExternalLoad load, RadarModel enemyRadar)
	{
		if(myRadar!=null)
		{
			if(Locating.getDistance(owner,load)<=myRadar.getRadarWarningDistance()&&load.isLauched()==true&&load.isEffective()==true&&load.getPerformance().getProperty().contains("Radar")&&(load.getCapturedObject() == owner||enemyRadar.getRadarTargetList().contains(owner)))
			{
				return true;
			}
			else return false;
		}
		else
		{
			return false;
		}
	}
}
