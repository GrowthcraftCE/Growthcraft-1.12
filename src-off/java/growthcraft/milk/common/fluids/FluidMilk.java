package growthcraft.milk.common.fluids;

import growthcraft.core.shared.fluids.GrowthcraftFluid;
import growthcraft.milk.shared.Reference;
import net.minecraft.util.ResourceLocation;

public class FluidMilk extends GrowthcraftFluid {

    public FluidMilk(String unlocalizedName) {
        super(unlocalizedName, new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_still"), new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_flow"));
        this.setUnlocalizedName(unlocalizedName);
        this.setDensity(1030);
        this.setViscosity(3000);
    }
}
