package Property;

/**
 * This class defines the performance of a type of radar
 * the data is stored into a xml file
 * @author WangShuzheng
 */

public class Radar {
	
	/**
	 * Attributes of radar
	 */
	
	private double maxDetectRange;
	private double maxDetectField;
	private int ScanInterval;
	
	private double maxLookDownAngle;
	private double ShowDistanceRange;
	
	private int maxTagertNumber;
	private int maxIndicateRadarAAM;
	private int maxIndicateInfraredAAM;
	
	private boolean hasIRST;
	private double IRSTRange;
	private double IRSTField;
	
	private double IFFDistance;
	private double RadarWarningDistance;
	
	public double getRadarWarningDistance() {
		return RadarWarningDistance;
	}

	public void setRadarWarningDistance(double radarWarningDistance) {
		RadarWarningDistance = radarWarningDistance;
	}

	public double getIFFDistance() {
		return IFFDistance;
	}

	public void setIFFDistance(double iFFDistance) {
		IFFDistance = iFFDistance;
	}

	public boolean isHasIRST() {
		return hasIRST;
	}

	public void setHasIRST(boolean hasIRST) {
		this.hasIRST = hasIRST;
	}

	public double getIRSTRange() {
		return IRSTRange;
	}

	public void setIRSTRange(double iRSTRange) {
		IRSTRange = iRSTRange;
	}

	public double getIRSTField() {
		return IRSTField;
	}

	public void setIRSTField(double iRSTField) {
		IRSTField = iRSTField;
	}

	public double getShowDistanceRange() {
		return ShowDistanceRange;
	}

	public void setShowDistanceRange(double showDistanceRange) {
		ShowDistanceRange = showDistanceRange;
	}

	public int getMaxIndicateRadarAAM() {
		return maxIndicateRadarAAM;
	}

	public void setMaxIndicateRadarAAM(int maxIndicateRadarAAM) {
		this.maxIndicateRadarAAM = maxIndicateRadarAAM;
	}

	public int getMaxIndicateInfraredAAM() {
		return maxIndicateInfraredAAM;
	}

	public void setMaxIndicateInfraredAAM(int maxIndicateInfraredAAM) {
		this.maxIndicateInfraredAAM = maxIndicateInfraredAAM;
	}

	public int getMaxTagertNumber() {
		return maxTagertNumber;
	}

	public void setMaxTagertNumber(int maxTagertNumber) {
		this.maxTagertNumber = maxTagertNumber;
	}

	public double getMaxDetectRange() {
		return maxDetectRange;
	}

	public void setMaxDetectRange(double maxDetectRange) {
		this.maxDetectRange = maxDetectRange;
	}

	public double getMaxDetectField() {
		return maxDetectField;
	}

	public void setMaxDetectField(double maxDetectField) {
		this.maxDetectField = maxDetectField;
	}

	public int getScanInterval() {
		return ScanInterval;
	}

	public void setScanInterval(int scanInterval) {
		ScanInterval = scanInterval;
	}

	public double getMaxLookDownAngle() {
		return maxLookDownAngle;
	}

	public void setMaxLookDownAngle(double maxLookDownAngle) {
		this.maxLookDownAngle = maxLookDownAngle;
	}
    
	public double getActualDetecRange(double RCS)
	{
		return this.maxDetectRange*Math.sqrt(Math.sqrt(RCS))/Math.sqrt(Math.sqrt(2));
	}
}
