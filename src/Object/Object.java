package Object;

import Physics.Vector;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import ControlLogic.ControlLogic;
import Physics.Earth;

/**
 * class Object defines an object with view and Physical properties
 * it has x,y location, velocity, mass and can excute operations such as move and accelerate
 * Its Subclass include Aircraft, ExternalLoad, GunBullet and so on
 * @author WangShuzheng
 */

public class Object {
	
	/**
	 * attributes of an object
	 * RCS is the value can affect the radar detect range, the lower RCS the harder the object can be seen by radar
	 * Signature is used to identify every instance of objects, it is helpful to decide the enemy or friend
	 * the aircraft with signature is larger than 0 is the teammate of player, signature is less than 0 means it is the enemy
	 * Its Subclass include Aircraft, ExternalLoad, GunBullet and so on
	 */
	
	protected String name;
	protected ControlLogic MyPlayer;
	
	protected double x;
	protected double y;
	protected double mass;
	protected Vector Velocity;
	protected Vector Acceleration;
	protected double kineticEnergy;
	protected double AOA;
	protected Vector totalForce;
	protected double RCS;
	
	protected double Life;
	protected float Armor;
	protected int Signature;
	
	protected ArrayList<ExternalLoad> ExternalLoadList;
	
	protected ImageView View;
	
	public String getName() {
		return name;
	}
	
	public ControlLogic getMyPlayer() {
		return MyPlayer;
	}
	
	public ImageView getView() {
		return this.View;
	}
	
	public double getRCS() {
		return RCS;
	}

	public void setRCS(double rCS) {
		RCS = rCS;
	}

	public double getAOA() {
		return this.AOA;
	}
	
	public void setAOA(double aOA) {
		this.AOA = aOA;
	}
	
	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public Vector getVelocity() {
		return this.Velocity;
	}

	public void setVelocity(Vector velocity) {
		this.Velocity = velocity;
	}
	
	public int getSignature() {
		// TODO Auto-generated method stub
		return this.Signature;
	}

	public double getLife() {
		// TODO Auto-generated method stub
		return this.Life;
	}
	
	public float getArmor() {
		return Armor;
	}

	public void setArmor(float armor) {
		Armor = armor;
	}
	
	public ArrayList<ExternalLoad> getExternalLoadList() {
		return ExternalLoadList;
	}

	public void setExternalLoadList(ArrayList<ExternalLoad> externalLoadList) {
		ExternalLoadList = externalLoadList;
	}
	
	/**
	 * Add all the force vector, it should be override by subclasses
	 */
	
	public void ForceAdd()
	{

	}
	
	/**
	 * Set the view of this object
	 * it will be called in GameWindowController
	 * @param CameraX the x location of camera
	 * @param CameraY the y location of camera
	 */
	
	public void setView(double CameraX, double CameraY) {
		this.View.setLayoutX(this.getX()-CameraX-this.View.getImage().getWidth()/2);
		this.View.setLayoutY(-this.getY()+CameraY-this.View.getImage().getHeight()/2);
		this.View.setRotate(-this.getVelocity().getDirection()-this.getAOA());
		
		if(this.getAOA()>0)
		{
			this.View.setScaleY(1);
		}
		else if(this.getAOA()<0)
		{
			this.View.setScaleY(-1);
		}	
	}
	
	/**
	 * move operation of this object, they should get the velocity and location at next timeInterval by newton's 2nd law
	 * it will be called in GameWindowController
	 */
	
	public void move()
	{
		this.Velocity = this.Velocity.vectorAdd(this.getAcceleration().vectorTime(Earth.TimeInterval/1000));
		this.x = this.x + this.Velocity.getValueX()*Earth.TimeInterval/1000;
		this.y = this.y + this.Velocity.getValueY()*Earth.TimeInterval/1000;
	}
	
	/**
	 * these special getters also contain the formula to get this value
	 * @return the total force
	 */
	
	public Vector getTotalForce() {
		this.ForceAdd();
		return this.totalForce;
	}

	public double getKineticEnergy() {
		this.kineticEnergy = 0.5*this.mass*this.Velocity.getValue()*this.Velocity.getValue();
		return kineticEnergy;
	}

	public Vector getAcceleration() {
		this.Acceleration = this.getTotalForce().vectorTime(1/this.mass);
		return this.Acceleration;
	}

	public void getDamage(double Amount)
	{
		this.Life = this.Life - Amount;
	}
}
