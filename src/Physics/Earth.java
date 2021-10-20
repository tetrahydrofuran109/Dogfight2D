package Physics;

/**
 * defines basic earth physical properties
 * such as value of g, timeInterval in this game, atmosphere density and mach number of an altitude
 * @author WangShuzheng
 */

public class Earth{
	public static double g = 9.8;
	public static final double TimeInterval = 20;
	
	/**
	 * get index of alititude array by a double value
	 * @param alt altitude
	 * @return index of altitude array
	 */
	
	public static int getIndexByAltitude(double alt)
	{
		int f = 0;
		if(alt<0)
		{
			f = 0;
		}
		else if(alt<1000)
		{
			f = 0;
		}
		else if(alt<2000)
		{
			f = 1;
		}
		else if(alt<3000)
		{
			f = 2;
		}
		else if(alt<4000)
		{
			f = 3;
		}
		else if(alt<5000)
		{
			f = 4;
		}
		else if(alt<6000)
		{
			f = 5;
		}
		else if(alt<7000)
		{
			f = 6;
		}
		else if(alt<8000)
		{
			f = 7;
		}
		else if(alt<9000)
		{
			f = 8;
		}
		else if(alt<10000)
		{
			f = 9;
		}
		else if(alt<11000)
		{
			f = 10;
		}
		else if(alt<12000)
		{
			f = 11;
		}
		else if(alt<13000)
		{
			f = 12;
		}
		else if(alt<14000)
		{
			f = 13;
		}
		else if(alt<15000)
		{
			f = 14;
		}
		else if(alt<16000)
		{
			f = 15;
		}
		
		else if(alt<17000)
		{
			f = 16;
		}
		else if(alt<18000)
		{
			f = 17;
		}
		else if(alt<19000)
		{
			f = 18;
		}
		else if(alt<20000)
		{
			f = 19;
		}
		else if(alt<21000)
		{
			f = 20;
		}
		else if(alt<22000)
		{
			f = 21;
		}
		else if(alt<23000)
		{
			f = 22;
		}
		else if(alt<24000)
		{
			f = 23;
		}
		else if(alt<25000)
		{
			f = 24;
		}
		else f = 25;
		
		return f;
	}
	
	/**
	 * get index of Mach array by a double value
	 * @param mach Mach
	 * @param maxMach max allowable mach to get index
	 * @return index of Mach array
	 */
	
	public static int getIndexByMach(double mach, int maxMach)
	{
		int f = 0;
		if(mach<0)
		{
			f = 0;
		}
		else if(mach<0.1)
		{
			f = 0;
		}
		else if(mach<0.2)
		{
			f = 1;
		}
		else if(mach<0.3)
		{
			f = 2;
		}
		else if(mach<0.4)
		{
			f = 3;
		}
		else if(mach<0.5)
		{
			f = 4;
		}
		else if(mach<0.6)
		{
			f = 5;
		}
		else if(mach<0.7)
		{
			f = 6;
		}
		else if(mach<0.8)
		{
			f = 7;
		}
		else if(mach<0.9)
		{
			f = 8;
		}
		else if(mach<1)
		{
			f = 9;
		}
		else if(mach<1.1)
		{
			f = 10;
		}
		else if(mach<1.2)
		{
			f = 11;
		}
		else if(mach<1.3)
		{
			f = 12;
		}
		else if(mach<1.4)
		{
			f = 13;
		}
		else if(mach<1.5)
		{
			f = 14;
		}
		else if(mach<1.6)
		{
			f = 15;
		}
		else if(mach<1.7)
		{
			f = 16;
		}
		else if(mach<1.8)
		{
			f = 17;
		}
		else if(mach<1.9)
		{
			f = 18;
		}
		else if(mach<2)
		{
			f = 19;
		}
		else if(mach<2.1)
		{
			f = 20;
		}
		else if(mach<2.2)
		{
			f = 21;
		}
		else if(mach<2.3)
		{
			f = 22;
		}
		else if(mach<2.4)
		{
			f = 23;
		}
		else if(mach<2.5)
		{
			f = 24;
		}
		else if(mach<2.6)
		{
			f = 25;
		}
		else if(mach<2.7)
		{
			f = 26;
		}
		else if(mach<2.8)
		{
			f = 27;
		}
		else if(mach<2.9)
		{
			f = 28;
		}
		else if(mach<3)
		{
			f = 29;
		}
		else f = 30;
		
		if(f>=maxMach)
		{
			f = maxMach - 1;
		}
		
		return f;
	}
	
	/**
	 * get Atmospheric Density by an altitude from an array
	 * @param altitude altitude
	 * @return Atmospheric Density
	 */
	
    public static double getAtmosphericDensity(double altitude)
    {
    	double AtmosphericDensityArray[] = {1.225,1.112,1.007,0.909,0.819,0.736,0.660,0.590,0.525,0.466,0.413,0.364,0.311,0.265,0.227,0.194,0.165,0.141,0.121,0.103,0.088,0.075,0.064,0.054,0.046,0.039};
    	double AtmosphericDensity = AtmosphericDensityArray[getIndexByAltitude(altitude)];
		return AtmosphericDensity;
    }
    
	/**
	 * get SonicSpeed by an altitude from an array
	 * @param altitude altitude
	 * @return SonicSpeed
	 */
    
    public static double getSonicSpeed(double altitude)
    {
    	double SonicSpeedArray[] = {340,336,333,329,325,321,316,312,308,304,299,295,295,295,295,295,295,295,295,295,295,296,296,297,298,298};
    	double SonicSpeed = SonicSpeedArray[getIndexByAltitude(altitude)];
		return SonicSpeed;
    }
}
