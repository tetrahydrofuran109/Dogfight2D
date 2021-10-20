package Property;

/**
 * This class defines the KeyCode to control aircraft and set of audio volume
 * the data is stored into a xml file
 * @author WangShuzheng
 */

public class ControlSettings {
	
	/**
	 * Attributes
	 */
	
	private String PitchCode;
	private String DiveCode;
	private String ThrottleUPCode;
	private String ThrottleDownCode;
	private String TrimCode;
	private String FiringCode;
	private String AirbrakeCode;
	private String OpenInfraredModeCode;
	private String OpenRadarModeCode;
	private String LaunchCode;
	private String ChangeWeaponCode;
	private String HelmetAimingCodeUP;
	private String HelmetAimingCodeDown;
	private String CommandCodeUP;
	private String CommandCodeDown;
	
	private boolean haveEngineAudio;
	private boolean haveGunAudio;
	private boolean haveLaunchAudio;
	private boolean haveEffectAudio;
	private boolean haveWarningAudio;
	
	private double EngineAudioVolume;
	private double GunAudioVolume;
	private double LaunchAudioVolume;
	private double EffectAudioVolume;
	private double WarningAudioVolume;
	
	public boolean isHaveEngineAudio() {
		return haveEngineAudio;
	}
	public void setHaveEngineAudio(boolean haveEngineAudio) {
		this.haveEngineAudio = haveEngineAudio;
	}
	public boolean isHaveGunAudio() {
		return haveGunAudio;
	}
	public void setHaveGunAudio(boolean haveGunAudio) {
		this.haveGunAudio = haveGunAudio;
	}
	public boolean isHaveLaunchAudio() {
		return haveLaunchAudio;
	}
	public void setHaveLaunchAudio(boolean haveLaunchAudio) {
		this.haveLaunchAudio = haveLaunchAudio;
	}
	public boolean isHaveEffectAudio() {
		return haveEffectAudio;
	}
	public void setHaveEffectAudio(boolean haveEffectAudio) {
		this.haveEffectAudio = haveEffectAudio;
	}
	public boolean isHaveWarningAudio() {
		return haveWarningAudio;
	}
	public void setHaveWarningAudio(boolean haveWarningAudio) {
		this.haveWarningAudio = haveWarningAudio;
	}
	public double getEngineAudioVolume() {
		return EngineAudioVolume;
	}
	public void setEngineAudioVolume(double engineAudioVolume) {
		EngineAudioVolume = engineAudioVolume;
	}
	public double getGunAudioVolume() {
		return GunAudioVolume;
	}
	public void setGunAudioVolume(double gunAudioVolume) {
		GunAudioVolume = gunAudioVolume;
	}
	public double getLaunchAudioVolume() {
		return LaunchAudioVolume;
	}
	public void setLaunchAudioVolume(double launchAudioVolume) {
		LaunchAudioVolume = launchAudioVolume;
	}
	public double getEffectAudioVolume() {
		return EffectAudioVolume;
	}
	public void setEffectAudioVolume(double effectAudioVolume) {
		EffectAudioVolume = effectAudioVolume;
	}
	public double getWarningAudioVolume() {
		return WarningAudioVolume;
	}
	public void setWarningAudioVolume(double warningAudioVolume) {
		WarningAudioVolume = warningAudioVolume;
	}
	public String getPitchCode() {
		return PitchCode;
	}
	public void setPitchCode(String pitchCode) {
		PitchCode = pitchCode;
	}
	public String getDiveCode() {
		return DiveCode;
	}
	public void setDiveCode(String diveCode) {
		DiveCode = diveCode;
	}
	public String getThrottleUPCode() {
		return ThrottleUPCode;
	}
	public void setThrottleUPCode(String throttleUPCode) {
		ThrottleUPCode = throttleUPCode;
	}
	public String getThrottleDownCode() {
		return ThrottleDownCode;
	}
	public void setThrottleDownCode(String throttleDownCode) {
		ThrottleDownCode = throttleDownCode;
	}
	public String getTrimCode() {
		return TrimCode;
	}
	public void setTrimCode(String trimCode) {
		TrimCode = trimCode;
	}
	public String getFiringCode() {
		return FiringCode;
	}
	public void setFiringCode(String firingCode) {
		FiringCode = firingCode;
	}
	public String getAirbrakeCode() {
		return AirbrakeCode;
	}
	public void setAirbrakeCode(String airbrakeCode) {
		AirbrakeCode = airbrakeCode;
	}
	public String getOpenInfraredModeCode() {
		return OpenInfraredModeCode;
	}
	public void setOpenInfraredModeCode(String openInfraredModeCode) {
		OpenInfraredModeCode = openInfraredModeCode;
	}
	public String getOpenRadarModeCode() {
		return OpenRadarModeCode;
	}
	public void setOpenRadarModeCode(String openRadarModeCode) {
		OpenRadarModeCode = openRadarModeCode;
	}
	public String getLaunchCode() {
		return LaunchCode;
	}
	public void setLaunchCode(String launchCode) {
		LaunchCode = launchCode;
	}
	public String getChangeWeaponCode() {
		return ChangeWeaponCode;
	}
	public void setChangeWeaponCode(String changeWeaponCode) {
		ChangeWeaponCode = changeWeaponCode;
	}
	public String getHelmetAimingCodeUP() {
		return HelmetAimingCodeUP;
	}
	public void setHelmetAimingCodeUP(String helmetAimingCodeUP) {
		HelmetAimingCodeUP = helmetAimingCodeUP;
	}
	public String getHelmetAimingCodeDown() {
		return HelmetAimingCodeDown;
	}
	public void setHelmetAimingCodeDown(String helmetAimingCodeDown) {
		HelmetAimingCodeDown = helmetAimingCodeDown;
	}
	public String getCommandCodeUP() {
		return CommandCodeUP;
	}
	public void setCommandCodeUP(String commandCodeUP) {
		CommandCodeUP = commandCodeUP;
	}
	public String getCommandCodeDown() {
		return CommandCodeDown;
	}
	public void setCommandCodeDown(String commandCodeDown) {
		CommandCodeDown = commandCodeDown;
	}	
}
