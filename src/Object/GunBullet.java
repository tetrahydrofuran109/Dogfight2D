package Object;

import java.io.File;

import Controller.SettingWindowController;
import Physics.Earth;
import Physics.Vector;
import Property.Gun;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * GunBullet object, which is launched by gun
 * it only have gravity and initial speed
 * the object inside bullet's hit radius will get gun damage
 * @author WangShuzheng
 */

public class GunBullet extends Object {
	
	/**
	 * Attributes of GunBullet
	 * count implement the duration of a bullet
	 */
	
	private int Signature;
	
	private Vector Gravity;
	private double GunPower;
	private double PiercingArmor;
	
	private boolean effective;
	private int count;
	
	private Gun gun;
	
	/**Getters and Setters**/
	
	public int getSignature() {
		return Signature;
	}

	public void setSignature(int signature) {
		Signature = signature;
	}

	public boolean isEffective() {
		return effective;
	}

	public double getGunPower() {
		return GunPower;
	}

	public void setGunPower(double gunPower) {
		GunPower = gunPower;
	}

	public double getPiercingArmor() {
		return PiercingArmor;
	}

	public void setPiercingArmor(double piercingArmor) {
		PiercingArmor = piercingArmor;
	}
	
	public void setEffective(boolean effective)
	{
		this.effective = effective;
	}

	/**
	 * Get gravity of this bullet
	 * @return gravity
	 */
	
	public Vector getGravity()
	{
		this.Gravity = new Vector(270,this.mass*Earth.g);
		return this.Gravity;
	}
	
	/**
	 * add forces of this aircraft
	 */
	
	public void ForceAdd()
	{
		this.totalForce = new Vector(0,0);
		this.totalForce = this.totalForce.vectorAdd(this.getGravity());
	}

	/**
	 * Constructor of this gunBullet
	 * @param gun name of gun which the bullet belongs to
	 * @param Signature Signature
	 */
	
	public GunBullet(Gun gun, int Signature) {
		this.Signature = Signature;
		this.gun = gun;
		this.effective = false;
		this.mass = this.gun.getBulletMass();
		this.View = new ImageView(new Image("/Image/Bullet/"+this.gun.getCartridge()+this.gun.getFlameColor()+"Bullet.png"));
		this.GunPower = this.gun.getGunPower();
		this.PiercingArmor = this.gun.getPiercingArmor();
		this.View.setVisible(false);
	}
	
	/**
	 * Lauch the bullet from the gun
	 * @param x the start x location
	 * @param y the start x location
	 * @param location the location of the gun
	 * @param v the initial speed of bullet
	 * @param direction the initial direction of bullet
	 */
	
	public void Enable(double x, double y, double location, double v, double direction) 
	{
		this.x = x + location*Math.sin(direction);
		this.y = y + location*Math.cos(direction);
		if(SettingWindowController.Setting.isHaveGunAudio())
		{
			//if(Math.abs(this.x - GameWindowController.CameraX)<960&&Math.abs(this.y - GameWindowController.CameraY)<540)
			if(this.Signature == 0)
			{
				Media sound = new Media(new File("src/Audio/Weapon/"+this.gun.getAudio()+".wav").toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setVolume(SettingWindowController.Setting.getGunAudioVolume());
				mediaPlayer.play();
			}
		}
		this.Velocity = new Vector(direction, this.gun.getInitialSpeed()+v);
		this.effective = true;
		this.count = 150;
	}
	
	/**
	 * move operation of this object, they should get the velocity and location at next timeInterval by newton's 2nd law
	 * it will stop and disappear after duration
	 * it will be called in GameWindowController
	 */
	
	public void move()
	{
		if(this.effective == true)
		{
			this.count--;
			this.Velocity = this.Velocity.vectorAdd(this.getAcceleration().vectorTime(Earth.TimeInterval/1000));
			this.x = this.x + this.Velocity.getValueX()*Earth.TimeInterval/1000;
			this.y = this.y + this.Velocity.getValueY()*Earth.TimeInterval/1000;
			if(this.count<0)
			{
				this.effective = false;
				this.View.setVisible(false);
			}
		}
	}
}
