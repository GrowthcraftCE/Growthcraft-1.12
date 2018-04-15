package growthcraft.core.utils;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class Utils
{
	private Utils() {}

	public static void debug(String msg)
	{
		final boolean flag = true;
		if (flag) { System.out.println(msg); }
	}

	public static boolean isIDInList(int id, String list)
	{
		final String[] itemArray = list.split(";");
		for (int i = 0; i < itemArray.length; i++)
		{
			final String[] values = itemArray[i].split(",");
			final int tempID = parseInt(values[0], 2147483647);

			if (tempID != 2147483647)
			{
				if (tempID == id) return true;
			}
		}
		return false;
	}

	public static int parseInt(String string, int defaultValue)
	{
		try
		{
			return Integer.parseInt(string.trim());
		}
		catch (NumberFormatException ex)
		{
		}
		return defaultValue;
	}

	public static final boolean isIntegerInRange(int i, int floor, int ceiling)
	{
		if (i < floor || i > ceiling)
		{
			return false;
		}
		return true;
	}

	public static void spawnExp(int amount, float exp, EntityPlayer player)
	{
		int j;

		if (exp == 0.0F)
		{
			amount = 0;
		}
		else if (exp < 1.0F)
		{
			j = MathHelper.floor((float)amount * exp);

			if (j < MathHelper.ceil((float)amount * exp) && (float)Math.random() < (float)amount * exp - (float)j)
			{
				++j;
			}

			amount = j;
		}

		while (amount > 0)
		{
			j = EntityXPOrb.getXPSplit(amount);
			amount -= j;
			player.world.spawnEntity(new EntityXPOrb(player.world, player.posX, player.posY + 0.5D, player.posZ + 0.5D, j));
		}
	}
}
