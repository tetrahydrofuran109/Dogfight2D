package Property;

/**
 * This class defines the performance of a type of externalLoad
 * the data is stored into a xml file
 * @author WangShuzheng
 */

public class ExternalLoadPerformance {
	
	/**
	 * Attributes of externalLoad
	 * Properties include InfraredAAM, SemiRadarAAM, and so on
	 * maxAttackField is used to determin whether the missle can lauch when head on the target
     * if a missle can attack when head on, maxAttackField is 180
     * lock field and range defines the envelop before launch, guide field defines envelop after launch
	 */
	
	private String name;
	private String Property;
	
	private double mass;
	private double RefArea;
	private double Cl;
	private double SubSonicCd0;
	private double SuperSonicCd0;
	private double maxLoadFactor;
	private int Duration;
	
	private double ThrustA;
	private double ThrustB;
	private int ThrustATime;
	private int ThrustBTime;
	
	private int guideTimeInterval;
	private double guideField;
	private double LockRange;
	private double LockField;
	private double MaxAttackField;
	
	private int AntiJam;
	
	private double TNTMass;
	private double ExplosionRange;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProperty() {
		return Property;
	}
	public void setProperty(String property) {
		Property = property;
	}
	
	public int getDuration() {
		return Duration;
	}
	public void setDuration(int duration) {
		Duration = duration;
	}
	public double getTNTMass() {
		return TNTMass;
	}
	
	public void setTNTMass(double tNTMass) {
		TNTMass = tNTMass;
	}
	
	public double getExplosionRange() {
		return ExplosionRange;
	}
	public void setExplosionRange(double explosionRange) {
		ExplosionRange = explosionRange;
	}
	public double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public double getRefArea() {
		return RefArea;
	}
	public void setRefArea(double refArea) {
		RefArea = refArea;
	}
	public double getCl() {
		return Cl;
	}
	public void setCl(double cl) {
		Cl = cl;
	}
	public double getSubSonicCd0() {
		return SubSonicCd0;
	}
	public void setSubSonicCd0(double subSonicCd0) {
		SubSonicCd0 = subSonicCd0;
	}
	public double getSuperSonicCd0() {
		return SuperSonicCd0;
	}
	public void setSuperSonicCd0(double superSonicCd0) {
		SuperSonicCd0 = superSonicCd0;
	}
	public double getMaxAttackField() {
		return MaxAttackField;
	}
	public void setMaxAttackField(double maxAttackField) {
		MaxAttackField = maxAttackField;
	}
	public double getMaxLoadFactor() {
		return maxLoadFactor;
	}
	public void setMaxLoadFactor(double maxLoadFactor) {
		this.maxLoadFactor = maxLoadFactor;
	}
	public double getThrustA() {
		return ThrustA;
	}
	public void setThrustA(double thrustA) {
		ThrustA = thrustA;
	}
	public double getThrustB() {
		return ThrustB;
	}
	public void setThrustB(double thrustB) {
		ThrustB = thrustB;
	}
	public int getThrustATime() {
		return ThrustATime;
	}
	public void setThrustATime(int thrustATime) {
		ThrustATime = thrustATime;
	}
	public int getThrustBTime() {
		return ThrustBTime;
	}
	public void setThrustBTime(int thrustBTime) {
		ThrustBTime = thrustBTime;
	}
	public int getGuideTimeInterval() {
		return guideTimeInterval;
	}
	public void setGuideTimeInterval(int guideTimeInterval) {
		this.guideTimeInterval = guideTimeInterval;
	}
	public double getGuideField() {
		return guideField;
	}
	public void setGuideField(double guideField) {
		this.guideField = guideField;
	}
	public double getLockRange() {
		return LockRange;
	}
	public void setLockRange(double lockRange) {
		LockRange = lockRange;
	}
	public double getLockField() {
		return LockField;
	}
	public void setLockField(double lockField) {
		LockField = lockField;
	}
	public int getAntiJam() {
		return AntiJam;
	}
	public void setAntiJam(int antiJam) {
		AntiJam = antiJam;
	}
}
