package Object;

import Physics.Earth;
import Physics.Vector;
import javafx.scene.image.ImageView;

/**
 * CounterMeasures, which is launched by object to disturb the enemy's missles
 * it only have gravity and initial speed
 * @author WangShuzheng
 */

public class CounterMeasures extends Object {
	
	/**
	 * Attributes of CounterMeasures
	 */
	
	private String property;
	private Vector Gravity;
	
	private boolean isEffective;
	private int Duration;
	
	public final static int flarePower = 20;
	public final static int ChaffPower = 20;
	public final static double LaunchSpeed = 500;
	
	/**
	 * Constructor of this CounterMeasures
	 * @param property property: chaff or flare
	 */
	
	public CounterMeasures(String property) {
		super();
		this.property = property;
		this.mass = 1;
		this.View = new ImageView("/Image/Effect/"+property+".png");
		this.View.setVisible(false);
		this.isEffective = false;
		this.Duration = 150;
	}
	
	/**
	 * launch the counterMeasures
	 * @param object the object launched this counterMeasures
	 */
	
	public void Enable(Object object){
		this.x = object.getX();
		this.y = object.getY();
		this.Velocity = new Vector(object.getVelocity().getDirection(), object.getVelocity().getValue()-LaunchSpeed);
		this.View.setVisible(true);
		this.isEffective = true;
	}

	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public boolean isEffective() {
		return isEffective;
	}

	public void setEffective(boolean isEffective) {
		this.isEffective = isEffective;
	}

	public int getDuration() {
		return Duration;
	}

	public void setDuration(int duration) {
		Duration = duration;
	}

	/**
	 * Get gravity of this counterMeasures
	 * @return gravity
	 */
	
	public Vector getGravity()
	{
		this.Gravity = new Vector(270,this.mass*Earth.g);
		return this.Gravity;
	}
	
	/**
	 * add forces of this counterMeasures
	 */
	
	public void ForceAdd()
	{
		this.totalForce = new Vector(0,0);
		this.totalForce = this.totalForce.vectorAdd(this.getGravity());
	}
	
	/**
	 * move operation of this object, they should get the velocity and location at next timeInterval by newton's 2nd law
	 * it will stop and disappear after duration
	 * it will be called in GameWindowController
	 */
	
	public void move()
	{
		this.Velocity = this.Velocity.vectorAdd(this.getAcceleration().vectorTime(Earth.TimeInterval/1000));
		this.x = this.x + this.Velocity.getValueX()*Earth.TimeInterval/1000;
		this.y = this.y + this.Velocity.getValueY()*Earth.TimeInterval/1000;
		this.Duration--;
		if(this.Duration == 0)
		{
			this.isEffective = false;
			this.View.setVisible(false);
		}
	}
}
