package growthcraft.bees.api;

import growthcraft.core.api.CoreRegistry;
import growthcraft.core.api.fluids.FluidTag;

public class BeesFluidTag
{
	public static final FluidTag MEAD = CoreRegistry.instance().fluidTags().createTag("mead");
	public static final FluidTag HONEY = CoreRegistry.instance().fluidTags().createTag("honey");

	private BeesFluidTag() {}
}
