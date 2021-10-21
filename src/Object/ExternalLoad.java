package Object;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ControlLogic.TacticsLogic;
import Data.ExternalLoadData;
import Physics.DamageModel;
import Physics.Earth;
import Physics.Locating;
import Physics.Vector;
import Property.ExternalLoadPerformance;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * ExternalLoad object, which is carried and can be launched by other object
 * it has the similar physical properties as the aircraft
 * it can be rocket, bomb, missle and so on
 * missles have guide function, which can lead them fly to target automatically
 * guide methods include Infrared, Radar(Semi-active or active(not yet implemented)) and Command
 * @author WangShuzheng
 */

public class ExternalLoad extends Object {
	
	/**
	 * Attributes of externalLoad 
	 * CountA and CountB are the duration of two level thrust(Some have two, others have only one)
	 * LockInterval is the time interval between two guide operation
	 * Duration is the duration time for the missle's working
	 * GuideCondition defines the direction the missle should go, 1 is for counterClockWise, -1 for clockWise, 0 is level flight
	 * CapturedObject is the target this missle will attack on
	 * X and Y location defines the location on the owner object for this external Load
	 */
	
	private boolean isLauched;
	private double Cd;
	private double Cl;
	private float Mach;
	
	private ExternalLoadPerformance Performance;
	
	private Vector Gravity;
	private Vector Drag;
	private Vector Lift;
	private Vector Thrust;
	private int CountA;
	private int CountB;
	private int LockInterval;
	private int Duration;
	
	private int GuideCondition;
	private boolean isEffective;
	
	private Aircraft Owner;
	private Object CapturedObject;
	
	private double Ylocation;
	private double Xlocation;
	
	private Explosion explosion;
	
	public Aircraft getOwner() {
		return Owner;
	}

	public void setOwner(Aircraft owner) {
		Owner = owner;
	}

	public Explosion getExplosion() {
		return explosion;
	}

	public Object getCapturedObject() {
		return this.CapturedObject;
	}

	public void setCapturedObject(Object capturedObject) {
		this.CapturedObject = capturedObject;
	}

	public boolean isLauched() {
		return this.isLauched;
	}

	public boolean isEffective() {
		return isEffective;
	}

	public void setEffective(boolean isEffective) {
		this.isEffective = isEffective;
	}

	public void setLauched(boolean isLauched) {
		this.isLauched = isLauched;
	}
	
	public ExternalLoadPerformance getPerformance() {
		return Performance;
	}

	/**
	 * get the lift coefficient
	 * the KCl is the slop of the lift curve
	 * @return Cl
	 */
	
	public double getCl() {
		if(this.getDynamicPressure()*this.Performance.getCl()*this.Performance.getRefArea()<=this.Performance.getMaxLoadFactor()*this.getGravity().getValue())
		{
			this.Cl = this.Performance.getCl();
		}
		else this.Cl = this.Performance.getMaxLoadFactor()*this.getGravity().getValue()/this.getDynamicPressure()/this.Performance.getRefArea();
		return Cl;
	}

	/**
	 * Get gravity
	 * @return gravity
	 */
	
	public Vector getGravity()
	{
		this.Gravity = new Vector(-90,this.getMass()*Earth.g);
		return this.Gravity;
	}
	
	/**
	 * Get Lift, the direction of the lift is defined by the GuideCondition, and if GuideCcondition is 0, keep level flight
	 * @return Lift
	 */
	
	public Vector getLift()
	{
		if(this.GuideCondition == 1)
		{
			this.Lift = new Vector(this.Velocity.getDirection()+90,this.getDynamicPressure()*this.getCl()*this.Performance.getRefArea());
		}
		else if (this.GuideCondition == -1) this.Lift = new Vector(this.Velocity.getDirection()+270,this.getDynamicPressure()*this.getCl()*this.Performance.getRefArea());
		else
        {
			if(Math.cos(this.Velocity.getDirection())>0)
			{
				if(Math.cos(this.Velocity.getDirection())*this.getGravity().getValue()<this.getDynamicPressure()*this.getCl()*this.Performance.getRefArea())
	            {
					this.Lift = new Vector(this.Velocity.getDirection()+90,Math.cos(this.Velocity.getDirection())*this.getGravity().getValue());
	            }
				else
				{
					this.Lift = new Vector(this.Velocity.getDirection()+90,this.getDynamicPressure()*this.getCl()*this.Performance.getRefArea());
				}
			}
			else
			{
				if(Math.cos(this.Velocity.getDirection())*this.getGravity().getValue()>-this.getDynamicPressure()*this.getCl()*this.Performance.getRefArea())
	            {
					this.Lift = new Vector(this.Velocity.getDirection()+270,-Math.cos(this.Velocity.getDirection())*this.getGravity().getValue());
	            }
				else
				{
					this.Lift = new Vector(this.Velocity.getDirection()+270,this.getDynamicPressure()*this.getCl()*this.Performance.getRefArea());
				}
			}
        }
		return this.Lift;
	}
	
	/**
	 * calculate the total drag
	 * @return Drag
	 */
	
	public Vector getDrag()
	{
		if(this.getMach()<1)
		{
			this.Cd = this.Performance.getSubSonicCd0() + 0.01*this.getCl()*this.getCl();
		}
		else this.Cd = this.Performance.getSuperSonicCd0() + 0.01*this.getCl()*this.getCl();
		this.Drag = new Vector(this.Velocity.getDirection()+180,this.getDynamicPressure()*this.Cd*this.Performance.getRefArea());
		return this.Drag;
	}
	
	/**
	 * Calculate the dynamic pressure of this aircraft
	 * @return Dynamic pressure
	 */
	
	public double getDynamicPressure()
	{
		return 0.5*Earth.getAtmosphericDensity(this.y)*this.Velocity.getValue()*this.Velocity.getValue();
	}
	
	/**
	 * Calculate the mach number
	 * @return mach number
	 */
	
	public float getMach()
	{
		this.Mach = (float) (this.Velocity.getValue()/Earth.getSonicSpeed(this.y));
		return this.Mach;
	}
	
	/**
	 * get the thrust of the externalLoad
	 * It has one or two Stage of Rocket Engine, there will be no thrust after these two stage burn out
	 * @return Thrust
	 */
	
	public Vector getThrust()
	{
		if(CountA > 0)
		{
			this.Thrust = new Vector(this.getVelocity().getDirection(),this.Performance.getThrustA());
			CountA--;
		}
		else if(CountB > 0)
		{
			this.Thrust = new Vector(this.getVelocity().getDirection(),this.Performance.getThrustB());
			CountB--;
		}
		else this.Thrust = new Vector(0,0);
		return this.Thrust;
	}
	
	/**
	 * add forces of this aircraft
	 */
	
	public void ForceAdd()
	{
		this.totalForce = new Vector(0,0);
		this.totalForce = this.totalForce.vectorAdd(this.getGravity());
		this.totalForce = this.totalForce.vectorAdd(this.getLift());
		this.totalForce = this.totalForce.vectorAdd(this.getDrag());
		this.totalForce = this.totalForce.vectorAdd(this.getThrust());
	}

	/**
	 * Constructor of this externalLoad
	 * load the performance from xml file
	 * Initialize the attribute
	 * @param name type of externalLoad, used to set the performance
	 */
	
	public ExternalLoad(String name) {
		super();
		this.isLauched = false;
	       try {
				JAXBContext context = JAXBContext
				        .newInstance(ExternalLoadData.class);
				Unmarshaller um = context.createUnmarshaller();
				ExternalLoadData data = (ExternalLoadData) um.unmarshal(new File("src/Data/ExternalLoad/"+name+".xml"));
				this.Performance = data.getData();
				Image img = new Image("/Image/ExternalLoad/"+name+".png");
				this.View = new ImageView(img);
				this.View.setVisible(false);
				
				CountA = data.getData().getThrustATime();
				CountB = data.getData().getThrustBTime();
				this.mass = data.getData().getMass();
				this.LockInterval = 0;
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		GuideCondition = 0;
		this.isEffective = false;
		this.CapturedObject = null;
	}
	
	/**
	 * set this externalLoad on its owner
	 * @param Owner the object which carries this externalLoad
	 * @param XLocation the location where the externaLoad Mounted
	 * @param YLocation the location where the externaLoad Mounted
	 */
	
	public void Load(Aircraft Owner, double XLocation, double YLocation)
	{
		this.Owner = Owner;
		this.isEffective = true;
		this.Xlocation = XLocation;
		this.Ylocation = YLocation;
		this.Velocity = this.Owner.getVelocity();
		this.Duration = this.getPerformance().getDuration();
		this.Signature = this.Owner.getSignature();
	}
	
	/**
	 * set this externalLoad on its owner
	 * @param CameraX the location where the externaLoad Mounted
	 * @param CameraY the location where the externaLoad Mounted
	 */
	
	public void setView(double CameraX, double CameraY) {
		if(this.isEffective == true)
		{
			if(this.View.isVisible() == false)
			{
				this.View.setVisible(true);
			}
			this.View.setLayoutX(this.getX()-CameraX-this.View.getImage().getWidth()/2);
			this.View.setLayoutY(-this.getY()+CameraY-this.View.getImage().getHeight()/2);
			if(this.isLauched == false)
			{
				this.View.setRotate(-this.getVelocity().getDirection()-this.Owner.getAOA());
			}
			else this.View.setRotate(-this.getVelocity().getDirection());
		}
		else this.View.setVisible(false);
	}
	
	/**
	 * move method of externalLoad
	 * before launch it move as its owner
	 * after Lauch move by themselves
	 */
	
	public void move()
	{
		if(this.isEffective == true&&this.isLauched == false)
		{
			this.Velocity = this.Owner.getVelocity();
		
		    if(this.Owner.getView().getScaleY()==1)
		    {
		    	this.x = this.Owner.getX()+this.Xlocation*Math.cos(Math.PI*(this.Owner.getVelocity().getDirection()+this.Owner.getAOA())/180)-this.Ylocation*Math.sin(Math.PI*(this.Owner.getVelocity().getDirection()+this.Owner.getAOA())/180);
				this.y = this.Owner.getY()+this.Xlocation*Math.sin(Math.PI*(this.Owner.getVelocity().getDirection()+this.Owner.getAOA())/180)+this.Ylocation*Math.cos(Math.PI*(this.Owner.getVelocity().getDirection()+this.Owner.getAOA())/180);
		    }
		    else if(this.Owner.getView().getScaleY()==-1)
		    {
		    	this.x = this.Owner.getX()+this.Xlocation*Math.cos(Math.PI*(this.Owner.getVelocity().getDirection()+this.Owner.getAOA())/180)+this.Ylocation*Math.sin(Math.PI*(this.Owner.getVelocity().getDirection()+this.Owner.getAOA())/180);
				this.y = this.Owner.getY()+this.Xlocation*Math.sin(Math.PI*(this.Owner.getVelocity().getDirection()+this.Owner.getAOA())/180)-this.Ylocation*Math.cos(Math.PI*(this.Owner.getVelocity().getDirection()+this.Owner.getAOA())/180);
		    }
		}
		else if(this.isEffective == true&&this.isLauched == true)
		{
			this.Guide();
			this.Velocity = this.Velocity.vectorAdd(this.getAcceleration().vectorTime(Earth.TimeInterval/1000));
			this.x = this.x + this.Velocity.getValueX()*Earth.TimeInterval/1000;
			this.y = this.y + this.Velocity.getValueY()*Earth.TimeInterval/1000;
			this.Duration--;
			
			//System.out.println(this.getVelocity().getValue());
			//System.out.println(this.getX());
			//System.out.println(this.getY());
			//System.out.println("");
			
			if(this.Duration == 0)
			{
				this.isEffective = false;
			}
			else if(this.y<0)
			{
				this.isEffective = false;
			}
		}
		else
		{
			this.View.setVisible(false);
		}
	}
	
	/**
	 * Guidemove method of missle type externalLoad
	 * Infrared missle guide by Infrared target, Radar missle guide by Radar target
	 * it has a guide field and won't guide when target is out of field except it has helmet guide
	 * If the missle is semi radar guide, it will empty its tagert after guide, so it need continues lock by owner's radar
	 * Guide by Command order for missles with Command guide function
	 */
	
	private void Guide() {
		// TODO Auto-generated method stub
		if(this.LockInterval==this.Performance.getGuideTimeInterval()&&this.CapturedObject!=null)
		{
			if(Locating.isInsideField(Locating.getRelativeAngle(this,CapturedObject), this.getVelocity().getDirection(), this.Performance.getGuideField())
			|| (this.getOwner().getHelmet()!=null&&this.getOwner().getHelmet().getAllowedWeapon().contains(this.getOwner().getMyPlayer().getCurrentWeapon().getPerformance().getName())))
			{
				this.GuideCondition = Locating.Guiding(this, CapturedObject);
			}
			else this.GuideCondition = 0;
		}
		else this.GuideCondition = 0;
		this.LockInterval++;
		if(this.LockInterval>this.Performance.getGuideTimeInterval())
		{
			this.LockInterval = 0;
		}
		
		if(this.Performance.getProperty().contains("Command")&&this.getOwner().getMyPlayer().getCommandOrder()!=0)
		{
			this.GuideCondition = this.getOwner().getMyPlayer().getCommandOrder();
			this.getOwner().getMyPlayer().setCommandOrder(0);
		}
		
		if(this.CapturedObject!=null)
		{
			if(this.Performance.getProperty().contains("Radar")&&this.getPerformance().getAntiJam()<this.CapturedObject.getAntiRadarValue())
			{
				this.GuideCondition = 0;
				if(2*this.getPerformance().getAntiJam()<this.CapturedObject.getAntiRadarValue())
				{
					this.CapturedObject = null;
				}
			}
			if(this.Performance.getProperty().contains("Infrared")&&this.getPerformance().getAntiJam()<this.CapturedObject.getAntiInfraredValue())
			{
				this.GuideCondition = 0;
				if(2*this.getPerformance().getAntiJam()<this.CapturedObject.getAntiInfraredValue())
				{
					this.CapturedObject = null;
				}
			}
		}
		
		if(this.Performance.getProperty().contains("SemiRadarAAM"))
		{
			this.CapturedObject = null;
		}
	}
	
	/**
	 * Create explosion by its tnt mass and explosion range, the fuze will work when target is inside its explosion range + hit radius
	 * @param object object which will active the fuze
	 */
	
	public void CreateExplosion(Object object)
	{
		if((this.y==0||(Locating.isAInRadiusOfB(object, this, DamageModel.HitRadius+this.getPerformance().getExplosionRange())&&TacticsLogic.IsEnemy(this, object)))&&this.isEffective == true&&this.isLauched==true)
		{
			this.explosion = new Explosion(this, this.getPerformance().getTNTMass(),this.getPerformance().getExplosionRange());
	    }
	}
}
