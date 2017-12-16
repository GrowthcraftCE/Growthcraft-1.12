package growthcraft.core.api.utils;

//Helper class for handling tick calculations, moved from YATM
public class TickUtils
{
	public static final int TICKS_PER_DECI_SECOND = 2;
	public static final int TICKS_PER_SECOND = 20;

	private TickUtils() {}

	public static int deciSeconds(int num)
	{
		return TICKS_PER_DECI_SECOND * num;
	}

	public static int seconds(int num)
	{
		return TICKS_PER_SECOND * num;
	}

	public static int minutes(int num)
	{
		return seconds(60 * num);
	}

	public static int hours(int num)
	{
		return minutes(60 * num);
	}

	public static int days(int num)
	{
		return hours(24 * num);
	}
}