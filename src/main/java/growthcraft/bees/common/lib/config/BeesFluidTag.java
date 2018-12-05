package growthcraft.bees.common.lib.config;

import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.fluids.FluidTag;

public class BeesFluidTag
{
	public static final FluidTag MEAD = CoreRegistry.instance().fluidTags().createTag("mead");
	public static final FluidTag HONEY = CoreRegistry.instance().fluidTags().createTag("honey");

	private BeesFluidTag() {}
}
