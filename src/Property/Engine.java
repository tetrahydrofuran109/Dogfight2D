package Property;

import Physics.Earth;

/**
 * This class defines the performance of a type of engine
 * the data is stored into a xml file
 * @author WangShuzheng
 */

public class Engine {
	
	/**
	 * Attributes of engine
	 * AB means afterburn, it can be open when engine has WEP
	 * thrust is fuction of mach number and altitude
	 */
	
	protected double Thrust;
	protected double FuelConsumptionRate;
	protected double WEPFuelConsumptionRate;
	protected boolean HaveWEP;
	protected double WEPThrust;
	
	private double MaxThrust[];
	private double ABThrust[];
	private double FuelConsumptionRatePerThrust;
	private double FuelConsumptionPerThrustAB;
	
	private String EngineType;

	public String getEngineType() {
		return this.EngineType;
	}

	public void setEngineType(String engineType) {
		this.EngineType = engineType;
	}

	public boolean isHaveWEP() {
		return HaveWEP;
	}
	
	public void setHaveWEP(boolean haveWEP) {
		this.HaveWEP = haveWEP;
	}
	
	public double getThrust(double Mach,double altitude)
	{	
		if(this.EngineType.contains("Jet"))
		{
			this.Thrust = this.MaxThrust[Earth.getIndexByMach(Mach, this.MaxThrust.length)];
			/*
			if(altitude <= 11000)
			{
				this.Thrust = this.Thrust*(1-0.06*altitude/1000);
			}
			else this.Thrust = this.Thrust*(1-0.06*11)*Earth.getAtmosphericDensity(altitude)/Earth.getAtmosphericDensity(11);
			*/
			this.Thrust = this.Thrust*Earth.getAtmosphericDensity(altitude)/Earth.getAtmosphericDensity(0);
		}
        return this.Thrust;
	}
	
	public double getWEPThrust(double Mach,double altitude)
	{
		if(this.EngineType.contains("Jet"))
		{
	        this.WEPThrust = this.ABThrust[Earth.getIndexByMach(Mach, this.ABThrust.length)];
	        /*
			if(altitude <= 11000)
			{
				this.WEPThrust = this.WEPThrust*(1-0.06*altitude/1000);
			}
			else this.WEPThrust = this.WEPThrust*(1-0.06*11)*Earth.getAtmosphericDensity(altitude)/Earth.getAtmosphericDensity(11);
			*/
	        this.WEPThrust = this.WEPThrust*Earth.getAtmosphericDensity(altitude)/Earth.getAtmosphericDensity(0);
		}
        return this.WEPThrust;
	}
	
	public double getFuelConsumptionRate()
	{
		if(this.EngineType.contains("Jet"))
		{
			this.FuelConsumptionRate = this.Thrust*this.FuelConsumptionRatePerThrust;
		}
		return this.FuelConsumptionRate;
	}
	
	public double getWEPFuelConsumptionRate()
	{
		if(this.EngineType.contains("Jet"))
		{
			this.WEPFuelConsumptionRate = this.WEPThrust*this.FuelConsumptionPerThrustAB;
		}
		return this.WEPFuelConsumptionRate;
	}

	public double[] getMaxThrust() {
		return this.MaxThrust;
	}

	public void setMaxThrust(double[] maxThrust) {
		this.MaxThrust = maxThrust;
	}

	public double getFuelConsumptionRatePerThrust() {
		return this.FuelConsumptionRatePerThrust;
	}

	public void setFuelConsumptionRatePerThrust(double fuelConsumptionRatePerThrust) {
		this.FuelConsumptionRatePerThrust = fuelConsumptionRatePerThrust;
	}

	public double[] getABThrust() {
		return this.ABThrust;
	}

	public void setABThrust(double[] aBThrust) {
		this.ABThrust = aBThrust;
	}

	public double getFuelConsumptionPerThrustAB() {
		return this.FuelConsumptionPerThrustAB;
	}

	public void setFuelConsumptionPerThrustAB(double fuelConsumptionPerThrustAB) {
		this.FuelConsumptionPerThrustAB = fuelConsumptionPerThrustAB;
	}
}
