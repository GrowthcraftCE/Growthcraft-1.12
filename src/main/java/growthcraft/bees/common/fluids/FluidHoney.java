package growthcraft.bees.common.fluids;

import growthcraft.bees.shared.Reference;
import growthcraft.core.shared.fluids.GrowthcraftFluid;
import net.minecraft.util.ResourceLocation;

public class FluidHoney extends GrowthcraftFluid {

    public FluidHoney(String unlocalizedName) {
        super(unlocalizedName, new ResourceLocation(Reference.MODID, "blocks/fluids/fluid_honey_still"), new ResourceLocation(Reference.MODID, "blocks/fluids/fluid_honey_flow"));
        this.setUnlocalizedName(unlocalizedName);
        this.setColor(0xffac01);
        this.setDensity(1420);
        this.setViscosity(73600);
    }
}
