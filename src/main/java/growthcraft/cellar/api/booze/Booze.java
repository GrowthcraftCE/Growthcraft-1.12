package growthcraft.cellar.api.booze;

import java.util.Locale;

import javax.annotation.Nonnull;

import growthcraft.cellar.Reference;
import growthcraft.core.api.GrowthcraftFluid;
import net.minecraft.util.ResourceLocation;

public class Booze extends GrowthcraftFluid
{
	// REVISE_ME 0
	
	private static final ResourceLocation ICON_STILL = new ResourceLocation(Reference.MODID.toLowerCase(Locale.US), "blocks/fluids/booze_still");
	private static final ResourceLocation ICON_FLOW = new ResourceLocation(Reference.MODID.toLowerCase(Locale.US), "blocks/fluids/booze_flow");
	
	public Booze(@Nonnull String fluidName )
	{
		super(fluidName, ICON_STILL, ICON_FLOW);
	}
}
