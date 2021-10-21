package Object;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ControlLogic.ControlLogic;
import Controller.SettingWindowController;
import Data.AircraftData;
import Data.EngineData;
import Data.GunData;
import Data.HelmetData;
import Data.LoadChoiceData;
import Data.RadarData;
import Physics.Earth;
import Physics.Vector;
import Property.AircraftPerformance;
import Property.Engine;
import Property.Gun;
import Property.Helmet;
import Property.Radar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * aircraft is the most important object in this game
 * it has mainly four forces: thrust, lift, gravity and drag
 * It has several subSystems: Radar, Helmet, Engine, guns and ExternalLoads
 * @author WangShuzheng
 */

public class Aircraft extends Object {
	
	/**
	 * attributes of an aircraft
	 * cd and cl is the drag coefficient and lift coefficient, used to calculate drag and lift
	 * Friction is used to brake on the groud
	 * G is the load factor of the aircraft, G = Lift/Gravity
	 * Mach and IAS is another two method to show speed
	 * Performance stored detailed performance of an aircraft
	 * PylonIndexList stored the drag index of every ExternalLoad
	 */
	
	private Vector Gravity;
	private Vector Drag;
	private Vector Lift;
	private Vector Thrust;
	private Vector Friction;
	private double Cd;
	private double Cl;
	private float G;
	private double FuelWeight;
	private int Throttle;
	private boolean WEP;
	private float Mach;
	private float IAS;
	private boolean brake;
	private boolean AirBrake;
	
	private AircraftPerformance Performance;
	
	private Engine Engine;
	private int EngineNumber;
	
	private Radar Radar;
	private Helmet Helmet;
	
	public Helmet getHelmet() {
		return Helmet;
	}

	public float getArmor() {
		this.Armor = this.getPerformance().getArmor();
		return Armor;
	}
	
	public AircraftPerformance getPerformance() {
		return this.Performance;
	}

	public Radar getRadar() {
		return Radar;
	}
	
	public int getEngineNumber() {
		return this.EngineNumber;
	}
	
	public void setThrottle(int throttle) {
		Throttle = throttle;
	}
	
	public boolean isWEP() {
		return this.WEP;
	}

	public void setWEP(boolean wEP) {
	    this.WEP = wEP;
	}

	public boolean isBrake() {
		return brake;
	}

	public void setBrake(boolean brake) {
		this.brake = brake;
	}
	
	public void setAirBrake(boolean airBrake) {
		this.AirBrake = airBrake;
	}

	public boolean getAirBrake()
	{
		return this.AirBrake;
	}
	
	public Vector getFriction()
	{
		this.Friction = new Vector(this.Velocity.getDirection()+180,this.getGravity().getValue()*0.5);
        return this.Friction;
	}
	
	public int getThrottle() {
		return this.Throttle;
	}
	
	public Engine getEngine() {
		return this.Engine;
	}

	public double getFuelWeight() {
		return this.FuelWeight;
	}
	
	public double setFuelWeight() {
		return this.FuelWeight;
	}
	
	/**
	 * set the Engine audio of this aircraft
	 */
	
	public void setSound()
	{
		if(SettingWindowController.Setting.isHaveEngineAudio())
		{
			if(this.FuelWeight!=0)
			{
				double volume;
				if(this.WEP==true)
				{
					volume = this.Life*0.6/this.getPerformance().getLife();
				}
				else volume = this.Life*this.Throttle/200/this.getPerformance().getLife();
				Media sound = new Media(new File("src/Audio/Engine/JetEngine.wav").toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setStartTime(Duration.seconds(10));
				mediaPlayer.setStopTime(Duration.seconds(12));
			    mediaPlayer.setVolume(SettingWindowController.Setting.getEngineAudioVolume()*volume);
				mediaPlayer.play();
			}
		}	
	}
	
	/**
	 * the aircraft's life is shown by its brightness
	 * the lower the life, the darker the aircraft is
	 */
	
	public void setAccordingToLife()
	{
		if(this.Life<0)
		{
			this.Life = 0;
		}
		
		if(this.Life==0 && this.y<=0)
		{
			this.View.setVisible(false);
		}
		else this.View.setVisible(true);
		
		ColorAdjust ShowLife = new ColorAdjust();
		ShowLife.setBrightness((this.Life-this.Performance.getLife())/this.Performance.getLife());
		this.View.setEffect(ShowLife);
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
	 * Calculate the IAS
	 * @return IAS
	 */
	
	public float getIAS()
	{
		this.IAS = (float) Math.sqrt(2*this.getDynamicPressure()/Earth.getAtmosphericDensity(0));
		return this.IAS;
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
	 * Calculate the mass of aircraft
	 * its mass includes the basicWeight, FuelWeight and the total weight of external loads
	 * @return mass
	 */
	
	public double getMass()
	{
		this.mass = this.Performance.getBasicWeight() + this.FuelWeight;
		for(int i = 0;i<this.ExternalLoadList.size();i++)
		{
			if(this.ExternalLoadList.get(i).isLauched()==false)
			{
				this.mass = this.mass + this.ExternalLoadList.get(i).getMass();
			}
		}
		return this.mass;
	}
	
	/**
	 * Get gravity of this aircraft
	 * @return gravity
	 */
	
	public Vector getGravity()
	{
		this.Gravity = new Vector(-90,this.getMass()*Earth.g);
		return this.Gravity;
	}
	
	/**
	 * Get Cd0(drag coefficient when lift is 0)
	 * @return Cd0
	 */
	
	public double getCd0()
	{
		double Cd0 = this.Performance.getCd0()[Earth.getIndexByMach(this.getMach(), this.Performance.getCd0().length)];
		if(this.AirBrake == true)
		{
			Cd0 = Cd0 + 1.5/this.Performance.getWingArea();
		}
		return Cd0;
	}
	
	/**
	 * Get Induced Drag Index(used to calculate the drag induced by lift)
	 * @return A
	 */
	
	public double getA()
	{
		double A = this.Performance.getA()[Earth.getIndexByMach(this.getMach(), this.Performance.getA().length)];
		double Kai;
		if(this.getMach()<=0.8)
		{
			Kai = 0.05;
		}
		else if(this.getMach()<1)
		{
			Kai = 0.025;
		}
		else
		{
			Kai = 0;
		}
		if(this.getCl()>0.1)
		{
			A = A*(1+Kai*this.getCl()*this.getCl());
		}
		return A;
	}
	
	/**
	 * Get the pitch rate of the aircraft, it defines the change rate of AOA
	 * @return pitchRate
	 */
	
	public double getPitchRate()
	{
		double PitchRate = this.Life*this.Performance.getPitchRate()[Earth.getIndexByMach(this.getMach(), this.Performance.getPitchRate().length)]/this.Performance.getLife();
		return PitchRate;
	}

	/**
	 * Limit the load facter of this aircraft by set proper aoa
	 */
	
	public void Glimiter()
	{
		if(this.getG()>this.Performance.getGmax(this.mass))
		{
			if(AOA>0)
			{
				this.AOA = this.Performance.getGmax(this.mass)*this.Gravity.getValue()/this.getDynamicPressure()/this.Performance.getWingArea()/this.Performance.getKCl();
			}
			else if(AOA<0) this.AOA = -this.Performance.getGmax(this.mass)*this.Gravity.getValue()/this.getDynamicPressure()/this.Performance.getWingArea()/this.Performance.getKCl();
		}
	}

	/**
	 * get the lift coefficient
	 * the KCl is the slop of the lift curve
	 * @return Cl
	 */
	
	public double getCl()
	{
		this.Cl = this.AOA*this.Performance.getKCl();
		return this.Cl;
	}
	
	/**
	 * calculate the lift
	 * @return Lift
	 */
	
	public Vector getLift()
	{
		this.Lift = new Vector(this.Velocity.getDirection()+90,this.Life*this.getDynamicPressure()*this.getCl()*this.Performance.getWingArea()/this.Performance.getLife());
		return this.Lift;
	}
	
	/**
	 * calculate the load factor
	 * @return G
	 */
	
	public float getG()
	{
		this.G = (float) Math.abs(this.getLift().getValue()/this.getGravity().getValue());
		return this.G;
	}
	
	/**
	 * calculate the total drag
	 * @return Drag
	 */
	
	public Vector getDrag()
	{
		this.Cd = this.getCd0() + this.getA()*this.getCl()*this.getCl();
		for(int i=0;i<this.ExternalLoadList.size();i++)
		{
			if(this.ExternalLoadList.get(i).isLauched()==false)
			{
				this.Cd = this.Cd + this.PylonIndexList.get(i)*this.ExternalLoadList.get(i).getPerformance().getSubSonicCd0()*this.ExternalLoadList.get(i).getPerformance().getRefArea()/this.Performance.getWingArea(); 
			}
		}
		this.Drag = new Vector(this.Velocity.getDirection()+180,this.getDynamicPressure()*this.Cd*this.Performance.getWingArea());
		return this.Drag;
	}

	/**
	 * excute the consumption of the fuel
	 */
	
	public void FuelConsump()
	{
		if(WEP == true)
		{
			this.FuelWeight = this.FuelWeight - this.EngineNumber*this.Engine.getWEPFuelConsumptionRate()*Earth.TimeInterval/1000/3600;
		}
		else this.FuelWeight = this.FuelWeight - this.Throttle*this.EngineNumber*this.Engine.getFuelConsumptionRate()*Earth.TimeInterval/1000/3600/100;
		if(this.FuelWeight<0)
		{
			this.FuelWeight = 0;
		}
	}
	
	/**
	 * get the thrust according to engine, throttle and life
	 * @return Thrust
	 */
	
	public Vector getThrust()
	{
		if(this.FuelWeight == 0)
		{
			this.Thrust = new Vector(0,0);
		}
		else if(this.WEP == true)
		{
			this.Thrust = new Vector(this.Velocity.getDirection()+this.AOA, this.Life*this.EngineNumber*this.Engine.getWEPThrust(this.getMach(), this.y)*Earth.g/this.Performance.getLife());
		}
		else this.Thrust = new Vector(this.Velocity.getDirection()+this.AOA, this.Life*this.Throttle*this.EngineNumber*this.Engine.getThrust(this.getMach(), this.y)*Earth.g/100/this.Performance.getLife());
		return this.Thrust;
	}
	
	/**
	 * add forces of this aircraft
	 */
	
	public void ForceAdd()
	{
		this.totalForce = new Vector(0,0);
		if(this.y>0 || (this.getLift().vectorAdd(this.getGravity()).getValueY()+this.getThrust().getValue()*Math.sin(this.AOA))>0)
		{
			this.totalForce = this.totalForce.vectorAdd(this.getGravity());
			this.totalForce = this.totalForce.vectorAdd(this.getLift());
			this.Glimiter();
		}
		this.totalForce = this.totalForce.vectorAdd(this.getDrag());
		this.totalForce = this.totalForce.vectorAdd(this.getThrust());
		if(this.brake == true)
		{
			this.totalForce = this.totalForce.vectorAdd(this.getFriction());
		}
	}

	/**
	 * Constructor of this aircraft
	 * load the parameters of five sub systems from xml file
	 * set start consition include speed, location, fuel and throttle
	 * @param name type of aircraft, used to set the performance
	 * @param x start x location
	 * @param y start y location
	 * @param Velocity start velocity
	 * @param FuelPercentage start fuel percentage
	 * @param Throttle Start throttle
	 * @param Signature Signature
	 * @param Player Control logic of this aircraft(AI or Player)
	 * @param LoadChoice ExternalLoads for this aircraft
	 */
	
	public Aircraft(String name, double x, double y, Vector Velocity, double FuelPercentage, int Throttle, int Signature, ControlLogic Player, String LoadChoice) {
		super();
		this.name = name;
		this.Signature = Signature;
		this.MyPlayer = Player;
		this.MyPlayer.setMyAircraft(this);
		this.MyPlayer.setRadarModel();
		this.MyPlayer.setInfraredModel();
		this.guns = new ArrayList<Gun>();
		this.BulletList = new ArrayList<GunBullet>();
		this.ExternalLoadList = new ArrayList<ExternalLoad>();
		this.PylonIndexList = new ArrayList<Double>();
		this.flareList = new ArrayList<CounterMeasures>();
		this.chaffList = new ArrayList<CounterMeasures>();
		this.Radar = null;
        try {
			JAXBContext context = JAXBContext
			        .newInstance(AircraftData.class);
			Unmarshaller um = context.createUnmarshaller();
			AircraftData data = (AircraftData) um.unmarshal(new File("src/Data/Aircraft/"+this.name+".xml"));
			this.name = data.getData().getName();
			this.Performance = data.getData();
			this.EngineNumber = data.getData().getEngineNumber();
			Image img = new Image("/Image/Aircraft/"+this.name+".png");
			this.View = new ImageView(img);
			this.View.setVisible(false);
			this.setRCS(data.getData().getRCS());
			for(int i = 0;i<data.getData().getFlareAmount();i++)
			{
				this.flareList.add(new CounterMeasures("Flare"));
			}
			for(int i = 0;i<data.getData().getChaffAmount();i++)
			{
				this.chaffList.add(new CounterMeasures("Chaff"));
			}
	        try {
				JAXBContext context1 = JAXBContext
				        .newInstance(EngineData.class);
				Unmarshaller um1 = context1.createUnmarshaller();
				EngineData data1 = (EngineData) um1.unmarshal(new File("src/Data/Engine/"+data.getData().getEngineName()+".xml"));
				this.Engine = data1.getData();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}            	        
	        for(int i = 0;i<data.getData().getGunName().size();i++)
	        {
		        try {
					JAXBContext context2 = JAXBContext
					        .newInstance(GunData.class);
					Unmarshaller um2 = context2.createUnmarshaller();
					GunData data2 = (GunData) um2.unmarshal(new File("src/Data/Gun/"+data.getData().getGunName().get(i)+".xml"));
					this.guns.add(data2.getData());
					this.guns.get(i).setAmmoAmount(data.getData().getAmmoAmount().get(i));
					this.guns.get(i).setAmmoCount(0);
					this.guns.get(i).setGunLocation(data.getData().getGunLocation().get(i));
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        try {
				JAXBContext context3 = JAXBContext
				        .newInstance(RadarData.class);
				Unmarshaller um3 = context3.createUnmarshaller();
				if(data.getData().getRadar()!=null)
				{
					RadarData data3 = (RadarData) um3.unmarshal(new File("src/Data/Radar/"+data.getData().getRadar()+".xml"));
					this.Radar = data3.getData();
				}

			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        try {
				JAXBContext context5 = JAXBContext
				        .newInstance(HelmetData.class);
				Unmarshaller um5 = context5.createUnmarshaller();
				if(data.getData().getHelmet()!=null)
				{
					HelmetData data5 = (HelmetData) um5.unmarshal(new File("src/Data/Helmet/"+data.getData().getHelmet()+".xml"));
					this.Helmet = data5.getData();
				}

			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        if(!LoadChoice.contains("Clean")||LoadChoice == null)
	        {
		        try {
					JAXBContext context4 = JAXBContext
					        .newInstance(LoadChoiceData.class);
					Unmarshaller um4 = context4.createUnmarshaller();
					LoadChoiceData data4 = (LoadChoiceData) um4.unmarshal(new File("src/Data/Aircraft/"+this.name+"/"+LoadChoice+".xml"));
					for(int i = 0;i<data4.getData().getLoadName().size();i++)
					{
						this.ExternalLoadList.add(new ExternalLoad(data4.getData().getLoadName().get(i)));
						this.PylonIndexList.add(data4.getData().getPylonIndex().get(i));
						this.ExternalLoadList.get(i).Load(this, data4.getData().getLoadLocationX().get(i), data4.getData().getLoadLocationY().get(i));
					}

				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		this.Velocity = Velocity;
		this.AOA = 0;
		this.FuelWeight = FuelPercentage*this.Performance.getFuelCapacity()/100;
		this.mass = this.Performance.getBasicWeight() + this.FuelWeight;
		this.Throttle = Throttle;
		this.WEP = false;
		this.brake = false;
		this.totalForce = new Vector(0,0);
		this.Reload();
		this.Life = this.Performance.getLife();
	}

	/**
	 * Set the flight condition of this aircraft
	 * if the aircraft is out of border of map, it will reverse its direction
	 * if its altitude is lower than 0, it will crashed
	 * If it is overSpeed, it will get damaged
	 * @param Xmax X border of current map
	 */
	
	public void setFlightCondition(double Xmax)
	{
		if(this.getX()>Xmax)
		{
			this.setVelocity(new Vector(180,this.getVelocity().getValue()));
		}
		if(this.getX()<0)
		{
			this.setVelocity(new Vector(0,this.getVelocity().getValue()));
		}
		if(this.getY()<1)
		{
			if(this.getVelocity().getValueY()<-25)
			{
				this.Life = 0;
			}
			
			if(this.getVelocity().getValueX()>0)
			{
				this.setVelocity(new Vector(0,this.getVelocity().getValue()));
			}
			else this.setVelocity(new Vector(180,this.getVelocity().getValue()));
		}
		if(this.getIAS()>(this.Performance.getLimitIAS()+50)/3.6 || this.getMach()>(this.Performance.getLimitMach()+0.05))
		{
			this.Life = this.Life - 0.1;
			if(this.Life<0)
			{
				this.Life = 0;
			}
		}
	}
	
	public String getIndicateThrottle()
	{
		String t;
        if(this.WEP == true)
		{
			t = "WEP";
		}
        else if(this.brake == true)
        {
        	t = "Brake";
        }
		else t = Integer.toString(this.getThrottle())+"%";
		return t;
	}
	
	public String getIndicateAltitude()
	{
		return Integer.toString((int)this.getY())+"m";
	}
	
	public String getIndicateTAS()
	{
		return Integer.toString((int)(this.getVelocity().getValue()*3.6))+"km/h";
	}
	
	public String getIndicateIAS()
	{
		return Integer.toString((int)(this.getIAS()*3.6))+"km/h";
	}
	
	public String getIndicateMach()
	{
		return String.format("%.2f", this.getMach())+"M";
	}
	
	public String getIndicateG()
	{
		return String.format("%.1f", this.getG())+"G";
	}
	
	public String getIndicateFuel()
	{
		return String.format("%.1f", (100*this.getFuelWeight()/this.Performance.getFuelCapacity()))+"%";
	}
	
	public String getIndicateX()
	{
		return Integer.toString((int)this.x);
	}
	
	public String getIndicateAmmo()
	{
		return Integer.toString(this.getTotalAmmo());
	}
	
	public String getIndicateFlare()
	{
		return Integer.toString(this.getTotalFlare());
	}
	
	public String getIndicateChaff()
	{
		return Integer.toString(this.getTotalChaff());
	}
}