package growthcraft.milk.blocks.fluids;

import growthcraft.core.api.GrowthcraftFluid;
import growthcraft.milk.Reference;
import net.minecraft.util.ResourceLocation;

public class FluidCheese extends GrowthcraftFluid {

    public FluidCheese(String unlocalizedName) {
        super(unlocalizedName, new ResourceLocation(Reference.MODID, "blocks/fluids/fluid_milk_still"), new ResourceLocation(Reference.MODID, "blocks/fluids/fluid_milk_flow"));
        this.setUnlocalizedName(unlocalizedName);
    }
}
