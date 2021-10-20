package Property;

/**
 * This class defines the performance of a type of gun
 * the data is stored into a xml file
 * @author WangShuzheng
 */

import java.util.ArrayList;

import Object.GunBullet;

public class Gun {
	
	/**
	 * Attributes of gun
	 * Catridge and FlameColor defines the view of bullet
	 */
	
	private double InitialSpeed;
	private int ShootInterval;
	private String Cartridge;
	private double GunPower;
	private double PiercingArmor;
	private String FlameColor;
	private double BulletMass;
	
	private double gunLocation;
	private int AmmoAmount;
	private int AmmoCount;
	
	private String Audio;
	
	private int count;

	/**
	 * Reload bullets for this gun
	 * @param list used to store the bullet
	 * @param Signature signature of gun owner
	 */
	
	public void Reload(ArrayList<GunBullet> list, int Signature) {
		this.setAmmoCount(this.getAmmoAmount());
		for(int P = 0;P<this.getAmmoAmount();P++)
		{
				list.add(new GunBullet(this,Signature));
		}
	}
	
	public String getAudio() {
		return Audio;
	}

	public void setAudio(String audio) {
		Audio = audio;
	}
	
	public double getGunLocation() {
		return gunLocation;
	}

	public void setGunLocation(double gunLocation) {
		this.gunLocation = gunLocation;
	}

	public int getAmmoAmount() {
		return AmmoAmount;
	}

	public void setAmmoAmount(int ammoAmount) {
		AmmoAmount = ammoAmount;
	}

	public int getAmmoCount() {
		return AmmoCount;
	}

	public void setAmmoCount(int ammoCount) {
		AmmoCount = ammoCount;
	}
	
	public int IncreaseCount() {
		return count++;
	}
	
	public int DecreaseCount() {
		return count++;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount() {
		this.count = 0;
	}
	public double getBulletMass() {
		return BulletMass;
	}
	public void setBulletMass(double bulletMass) {
		BulletMass = bulletMass;
	}
	public String getFlameColor() {
		return FlameColor;
	}
	public void setFlameColor(String flameColor) {
		FlameColor = flameColor;
	}
	public double getPiercingArmor() {
		return PiercingArmor;
	}
	public void setPiercingArmor(double piercingArmor) {
		PiercingArmor = piercingArmor;
	}
	public double getInitialSpeed() {
		return InitialSpeed;
	}
	public void setInitialSpeed(double initialSpeed) {
		InitialSpeed = initialSpeed;
	}
	public int getShootInterval() {
		return ShootInterval;
	}
	public void setShootInterval(int shootInterval) {
		ShootInterval = shootInterval;
	}
	public String getCartridge() {
		return Cartridge;
	}
	public void setCartridge(String cartridge) {
		Cartridge = cartridge;
	}
	public double getGunPower() {
		return GunPower;
	}
	public void setGunPower(double gunPower) {
		GunPower = gunPower;
	}
}
