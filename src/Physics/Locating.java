package Physics;

import Object.Object;

/**
 * Locating contains methods used for calculate the geometry relations between objects
 * @author WangShuzheng
 */

public class Locating {
	
	/**
	 * get the distance on x axis of two object
	 * @param objectA objectA
	 * @param objectB objectB
	 * @return the distance on x axis of A and B
	 */
	
	public static double getDistanceX(Object objectA, Object objectB)
	{
		return Math.abs(objectB.getX()-objectA.getX());
	}
	
	/**
	 * get the distance on y axis of two object
	 * @param objectA objectA
	 * @param objectB objectB
	 * @return the distance on y axis of A and B
	 */
	
	public static double getDistanceY(Object objectA, Object objectB)
	{
		return Math.abs(objectB.getY()-objectA.getY());
	}
	
	/**
	 * get the distance two object
	 * @param objectA objectA
	 * @param objectB objectB
	 * @return the distance of A and B
	 */
	
	public static double getDistance(Object objectA, Object objectB)
	{
		return Math.sqrt(getDistanceX(objectA, objectB)*getDistanceX(objectA, objectB)+getDistanceY(objectA, objectB)*getDistanceY(objectA, objectB));
	}
	
	/**
	 * this method calculate the relative direction of B compare to A
	 * this means draw a straight line from A to B, set A as the center, the direction of this line is what we want
	 * @param objectA objectA
	 * @param objectB objectB
	 * @return relative direction of B compare to A
	 */
	
	public static double getRelativeAngle(Object objectA, Object objectB)
	{
		double A;
		if(objectB.getY()-objectA.getY()>0)
		{
			A = Math.toDegrees(Math.acos((objectB.getX()-objectA.getX())/getDistance(objectA, objectB)));
		}
		else if(getDistanceX(objectA, objectB)==0&&getDistance(objectA, objectB)==0) A = 0;
		else A = 360-Math.toDegrees(Math.acos((objectB.getX()-objectA.getX())/getDistance(objectA, objectB)));
		return A;
	}

	/**
	 * this method is to check whether B is locate in the field created by A
	 * it is a sector area in front of object A
	 * @param DirectionA DirectionA
	 * @param DirectionB DirectionB
	 * @param field the sector area
	 * @return if in the field return true
	 */
	
	public static boolean isInsideField(double DirectionA, double DirectionB, double field)
	{
		if(DirectionA-DirectionB>180)
		{
			DirectionA = DirectionA - 360;
		}
		else if(DirectionB-DirectionA>180)
		{
			DirectionB = DirectionB - 360;
		}
		
		if(DirectionA<=DirectionB+field&DirectionA>=DirectionB-field)
		{
            return true;
		}
		else return false;
	}
	
	/**
	 * this method is to compare the two directions
	 * the key is to get in which way an object turn from A direction to B direction is faster
	 * @param DirectionA DirectionA
	 * @param DirectionB DirectionB
	 * @return return true if clockWise, false if counterClockWise
	 */
	
	public static boolean AngleComparing(double DirectionA, double DirectionB)
	{
		if(DirectionA-DirectionB>180)
		{
			DirectionA = DirectionA - 360;
		}
		else if(DirectionB-DirectionA>180)
		{
			DirectionB = DirectionB - 360;
		}
		
		if(DirectionA>=DirectionB)
		{
            return true;
		}
		else return false;
	}
	
	/**
	 * Guide method is important for missle guide and AI aiming
	 * its an application of getRelativeAngle
	 * @param A Object needs guide
	 * @param B Target
	 * @return return 1 if need turn clockWise, -1 if need turn counterClockWise
	 */
	
	public static int Guiding(Object A, Object B)
	{	
		if(AngleComparing(Locating.getRelativeAngle(A,B),A.getVelocity().getDirection()))
		{
			return 1;
		}
		else return -1;
	}
	
	/**
	 * return the smaller one of A and B
	 * @param A A
	 * @param B B
	 * @return return the smaller one
	 */
	
	public static double minNum(double A, double B)
	{
		if(A<B)
		{
			return A;
		}
		else 
		{
			return B;
		}
	}
	
	/**
	 * return the larger one of A and B
	 * @param A A
	 * @param B B
	 * @return return the larger one
	 */
	
	public static double maxNum(double A, double B)
	{
		if(A>B)
		{
			return A;
		}
		else 
		{
			return B;
		}
	}
	
	/**
	 * this method is to check whether A is locate an area created by B
	 * this area is a circle with a radius, B is the center
	 * @param A objectA
	 * @param B objectB
	 * @param Radius radius of the circle area
	 * @return if in the field return true
	 */
	
	public static boolean isAInRadiusOfB(Object A, Object B, double Radius)
	{
		if(getDistance(A, B)<Radius)
		{
			return true;
		}
		else return false;
	}
	
	/**
	 * this method is to check whether A is locate an area created by B
	 * this area is a square with a length and width, B is the center
	 * @param A objectA
	 * @param B objectB
	 * @param dx length
	 * @param dy width
	 * @return if in the field return true
	 */
	
	public static boolean isAInSquareOfB(Object A, Object B, double dx, double dy)
	{
		if(getDistanceX(A, B)<dx&&getDistanceY(A, B)<dy)
		{
			return true;
		}
		else return false;
	}
	
	/**
	 * this method is to calculate the direction A attack on B
	 * it is useful for AI control
	 * @param A objectA
	 * @param B objectB
	 * @return if A chase B or B chase A return 1, head on return -1, else 0
	 */
	
	public static int AttackDirection(Object A, Object B)
	{
		if(A.getVelocity().getAngle(B.getVelocity())<90)
		{
			return 1;
		}
		else if(A.getVelocity().getAngle(B.getVelocity())>90)
		{
			return -1;
		}
		else return 0;
	}
}
