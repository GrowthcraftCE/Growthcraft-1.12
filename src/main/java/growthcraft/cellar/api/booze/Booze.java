package growthcraft.cellar.api.booze;

import javax.annotation.Nonnull;

import growthcraft.core.api.GrowthcraftFluid;
import net.minecraft.util.ResourceLocation;

public class Booze extends GrowthcraftFluid
{
	// REVISE_ME 0
	
	public Booze(@Nonnull String fluidName, ResourceLocation still, ResourceLocation flowing)
	{
		super(fluidName, still, flowing);
	}
}
