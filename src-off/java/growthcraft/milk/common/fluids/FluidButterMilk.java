package growthcraft.milk.common.fluids;

import growthcraft.core.shared.fluids.GrowthcraftFluid;
import growthcraft.milk.shared.Reference;
import net.minecraft.util.ResourceLocation;

public class FluidButterMilk extends GrowthcraftFluid {

    public FluidButterMilk(String unlocalizedName) {
        super(unlocalizedName, new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_still"), new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_flow"));
        this.setUnlocalizedName(unlocalizedName);
    }
}
