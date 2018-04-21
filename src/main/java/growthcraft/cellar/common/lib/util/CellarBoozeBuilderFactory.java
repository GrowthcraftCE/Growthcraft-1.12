package growthcraft.cellar.common.lib.util;

import javax.annotation.Nonnull;

import net.minecraftforge.fluids.Fluid;

public class CellarBoozeBuilderFactory
{
	private UserApis userApis;

	public CellarBoozeBuilderFactory(@Nonnull UserApis apis)
	{
		this.userApis = apis;
	}

	public ICellarBoozeBuilder create(@Nonnull Fluid fluid)
	{
		return new UserApiCellarBoozeBuilder(userApis, fluid);
	}
}
