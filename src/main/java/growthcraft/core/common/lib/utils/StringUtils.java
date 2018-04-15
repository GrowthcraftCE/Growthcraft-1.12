package growthcraft.core.common.lib.utils;

import java.util.Arrays;

public class StringUtils
{
	private StringUtils() {}

	public static String capitalize(String str)
	{
		if (str.length() <= 1)
		{
			return str.toUpperCase();
		}
		else
		{
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
	}

	public static String inspect(Object obj)
	{
		if (obj == null)
		{
			return "@null";
		}
		else if (obj.getClass().isArray())
		{
			if (obj.getClass().getComponentType().isArray())
			{
				return Arrays.deepToString((Object[][])obj);
			}
			else
			{
				return Arrays.toString((Object[])obj);
			}
		}
		return obj.toString();
	}
}