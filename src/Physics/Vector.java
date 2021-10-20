package Physics;

import java.lang.Math;

/**
 * Vector is a type of variable with magnitude and direction
 * it is useful in physics calculation
 * @author WangShuzheng
 */

public class Vector {
	
	/**
	 * Attributes of vector
	 * it can be expressed by x and y value or magnitude and direction
     */
	
	protected double valueX;
	protected double valueY;
	
	protected double direction;
	protected double value;
	
	public Vector(double direction, double value) {
		this.direction = direction;
		this.Periodic();
		this.value = value;
		this.CalledIfValueIsNegtive();
		this.SetZeroVector();
		this.valueX = this.getValueX();
		this.valueY = this.getValueY();
	}
	
	/**
	 * To implement the periodic of a vector
	 * its direction is from 0 to 360
	 */
	
	public void Periodic()
	{
		if(this.direction<0)
		{
			this.direction = this.direction + 360;
		}
		if(this.direction>360)
		{
			this.direction = this.direction - 360;
		}
	}
	
	/**
	 * magnitude is always positive
	 * call this method to deal with negtive magnitude
	 */
	
	public void CalledIfValueIsNegtive()
	{
		if(this.value <0)
		{
			this.value = -this.value;
			this.direction = this.direction + 180;
		}
	}
	
	/**
	 * set of zero vector
	 */
	
	public void SetZeroVector()
	{
		if(this.value == 0)
		{
			this.direction = 0;
		}
	}

	/**
	 * define the add of two vector
	 * @param A another vector
	 * @return the vector add result of this vector and A
	 */
	
	public Vector vectorAdd(Vector A)
	{
		double x = A.valueX + this.valueX;
		double y = A.valueY + this.valueY;
		Vector B;
		if(x == 0 && y == 0)
		{
			B = new Vector(0, 0);
		}
		else if(y>0)
		{
			B = new Vector(Math.toDegrees(Math.acos(x/Math.sqrt(x*x+y*y))), Math.sqrt(x*x+y*y));
		}
		else B = new Vector(360-Math.toDegrees(Math.acos(x/Math.sqrt(x*x+y*y))), Math.sqrt(x*x+y*y));
		return B;
	}
	
	/**
	 * define the result of vector times a value
	 * @param a a value
	 * @return result of this vector times a
	 */
	
	public Vector vectorTime(double a)
	{
		return new Vector(this.getDirection(),a*this.getValue());
	}
	
	/**
	 * define the Multplication of of two vector
	 * @param A another vector
	 * @return the vector multiplication result of this vector and A
	 */
	
	public Double vectorMult(Vector A)
	{
		double x = A.valueX * this.valueX;
		double y = A.valueY * this.valueY;
		double f = x + y;	
		return f;
	}
	
	/**
	 * get vector reversed
	 * @return the vector with reversed direction
	 */
	
	public Vector getReverseVector()
	{
		return new Vector(this.getDirection()+180,this.getValue());
	}
	
	/**
	 * get the angle between two vector
	 * @param v another vector
	 * @return the angle between this and v
	 */
	
	public double getAngle(Vector v)
	{
		if(v.getValue() == 0 || this.getValue() == 0)
		{
			return 0;
		}
		else return Math.toDegrees(Math.acos(this.vectorMult(v)/(this.getValue()*v.getValue())));
	}
	
	/**
	 * get the x value of vector from magnitude and direction
	 * @return the x value of this vector
	 */
	
	public double getValueX() {
		this.valueX = this.value*Math.cos(this.direction*Math.PI/180);
		return valueX;
	}
	
	/**
	 * get the y value of vector from magnitude and direction
	 * @return the y value of this vector
	 */
	
	public double getValueY() {
		this.valueY = this.value*Math.sin(this.direction*Math.PI/180);
		return valueY;
	}

	/**
	 * get the direction of the vector
	 * the main purpose is to make correction if it is zero vector or the direction is out of bound
	 * @return the y value of this vector
	 */
	
	public double getDirection() {
        this.Periodic();
        this.SetZeroVector();
		return direction;
	}
	
	/**
	 * get the magnitude of the vector
	 * @return the magnitude of this vector
	 */
	
	public double getValue() {
		return value;
	}
}
