package growthcraft.milk.shared.fluids;

import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.fluids.FluidTag;

public class MilkFluidTags {
    public static final FluidTag CHEESE = CoreRegistry.instance().fluidTags().createTag("cheese");
    public static final FluidTag CREAM = CoreRegistry.instance().fluidTags().createTag("cream");
    public static final FluidTag MILK = CoreRegistry.instance().fluidTags().createTag("milk");
    public static final FluidTag MILK_CURDS = CoreRegistry.instance().fluidTags().createTag("milk_curds");
    public static final FluidTag RENNET = CoreRegistry.instance().fluidTags().createTag("rennet");
    public static final FluidTag WHEY = CoreRegistry.instance().fluidTags().createTag("whey");

    private MilkFluidTags() {
    }
}
