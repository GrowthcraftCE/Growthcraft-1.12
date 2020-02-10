package growthcraft.cellar.shared.fluids;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.fluids.GrowthcraftFluid;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class Booze extends GrowthcraftFluid {
    // REVISE_ME 0

    private static final ResourceLocation ICON_STILL = new ResourceLocation(Reference.MODID, "blocks/fluids/booze_still");
    private static final ResourceLocation ICON_FLOW = new ResourceLocation(Reference.MODID, "blocks/fluids/booze_flow");

    public Booze(@Nonnull String fluidName) {
        super("fluid_booze_" + fluidName, ICON_STILL, ICON_FLOW);
    }
}
