package growthcraft.core.shared.utils;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class Platform
{
	private Platform() {}

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
}
