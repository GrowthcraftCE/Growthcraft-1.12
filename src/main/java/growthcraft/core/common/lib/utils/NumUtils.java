package growthcraft.core.common.lib.utils;

public class NumUtils
{
	private NumUtils() {}

	public static boolean isFlagged(int src, int flag)
	{
		return (src & flag) == flag;
	}

	public static int roundedBlocks(int num, int blocksize)
	{
		int n = num / blocksize;
		if (n < num) n += blocksize;
		return n;
	}

	public static int closestPowerOf2(int num)
	{
		int start = 1;
		while (start < num)
		{
			start *= 2;
		}
		return start;
	}

	public static boolean between(int num, int bot, int top)
	{
		return num >= bot && num <= top;
	}

	public static int[] newIntRangeArray(int start, int length)
	{
		final int[] result = new int[length];
		for (int i = 0; i < result.length; ++i)
		{
			result[i] = start + i;
		}
		return result;
	}

	public static int[] newIndexArray(int length)
	{
		return newIntRangeArray(0, length);
	}
}
