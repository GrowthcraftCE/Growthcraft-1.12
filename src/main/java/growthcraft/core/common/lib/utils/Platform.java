package growthcraft.core.common.lib.utils;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class Platform
{
	private Platform() {}

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
}
