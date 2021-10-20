package Property;

import java.util.ArrayList;

/**
 * This class defines the performance of a type of aircraft
 * the data is stored into a xml file
 * @author WangShuzheng
 */

public class AircraftPerformance {
	
	/**
	 * Attributes of a type of aircraft
	 */
	
	private String name;
	
	private double Cd0[];
	private int AOAmax;
	private double A[];
	private float Glimit;
	private double BasicWeight;
	private double FuelCapacity;
	private double DesignWeight;
	private double KCl;
	private float WingArea;
	private double PitchRate[];
	private int EngineNumber;
	private boolean hasBrake;
	private double RCS;
	
	private double Life;
	private float Armor;
	private double LimitIAS;
	private float LimitMach;
	
	private float Gmax;
	
	private ArrayList<String> gunName = new ArrayList<String>();
	private ArrayList<Double> gunLocation = new ArrayList<Double>();
	private ArrayList<Integer> ammoAmount = new ArrayList<Integer>();

	private String Radar;
	private String Helmet;
	
	public String getHelmet() {
		return Helmet;
	}

	public void setHelmet(String helmet) {
		Helmet = helmet;
	}

	public float getLimitMach() {
		return LimitMach;
	}

	public void setLimitMach(float limitMach) {
		LimitMach = limitMach;
	}

	public double getRCS() {
		return RCS;
	}

	public void setRCS(double rCS) {
		RCS = rCS;
	}

	public String getRadar() {
		return Radar;
	}

	public void setRadar(String radar) {
		Radar = radar;
	}
	
	public double getLimitIAS() {
		return LimitIAS;
	}

	public void setLimitIAS(double limitSpeed) {
		LimitIAS = limitSpeed;
	}

	public float getGmax() {
		return Gmax;
	}

	public void setGmax(float gmax) {
		Gmax = gmax;
	}

	public double getLife() {
		return Life;
	}

	public void setLife(double life) {
		Life = life;
	}

	public float getArmor() {
		return Armor;
	}

	public void setArmor(float armor) {
		Armor = armor;
	}

	public ArrayList<Integer> getAmmoAmount() {
		return ammoAmount;
	}

	public void setAmmoAmount(ArrayList<Integer> ammoAmount) {
		this.ammoAmount = ammoAmount;
	}

	public ArrayList<String> getGunName() {
		return gunName;
	}

	public void setGunName(ArrayList<String> gunName) {
		this.gunName = gunName;
	}

	public ArrayList<Double> getGunLocation() {
		return gunLocation;
	}

	public void setGunLocation(ArrayList<Double> gunLocation) {
		this.gunLocation = gunLocation;
	}

	public boolean isHasBrake() {
		return hasBrake;
	}

	public void setHasBrake(boolean hasBrake) {
		this.hasBrake = hasBrake;
	}

	public int getEngineNumber() {
		return EngineNumber;
	}

	public void setEngineNumber(int engineNumber) {
		EngineNumber = engineNumber;
	}

	private String EngineName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AircraftPerformance() {
		super();
	}

	public double[] getCd0() {
		return Cd0;
	}

	public void setCd0(double[] cd0) {
		Cd0 = cd0;
	}

	public int getAOAmax() {
		return AOAmax;
	}

	public void setAOAmax(int aOAmax) {
		AOAmax = aOAmax;
	}

	public double[] getA() {
		return A;
	}

	public void setA(double[] a) {
		A = a;
	}

	public float getGlimit() {
		return Glimit;
	}

	public void setGlimit(float glimit) {
		Glimit = glimit;
	}

	public double getBasicWeight() {
		return BasicWeight;
	}

	public void setBasicWeight(double basicWeight) {
		BasicWeight = basicWeight;
	}

	public double getFuelCapacity() {
		return FuelCapacity;
	}

	public void setFuelCapacity(double fuelCapacity) {
		FuelCapacity = fuelCapacity;
	}

	public double getDesignWeight() {
		return DesignWeight;
	}

	public void setDesignWeight(double designWeight) {
		DesignWeight = designWeight;
	}

	public double getKCl() {
		return KCl;
	}

	public void setKCl(double kCl) {
		KCl = kCl;
	}

	public float getWingArea() {
		return WingArea;
	}

	public void setWingArea(float wingArea) {
		WingArea = wingArea;
	}

	public double[] getPitchRate() {
		return PitchRate;
	}

	public void setPitchRate(double[] pitchRate) {
		PitchRate = pitchRate;
	}

	public String getEngineName() {
		return EngineName;
	}

	public void setEngineName(String engineName) {
		EngineName = engineName;
	}
	
	/**
	 * get max load factor of current mass
	 * the max load factor is determind by mass of aircraft
	 * if mass is less than design weight, maxG is design G limit, if is more than design weight, Gmax = Gdesign*designWeight/curretnWeight
	 * @param mass current weight
	 * @return the g limit of current weight
	 */
	
	public float getGmax(double mass)
	{
		if(mass<=this.DesignWeight)
		{
			this.Gmax = this.Glimit;
		}
		else this.Gmax = (float) (this.Glimit*this.DesignWeight/mass);
		return this.Gmax;
	}
}
