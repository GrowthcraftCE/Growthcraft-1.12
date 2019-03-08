package growthcraft.milk.common.fluids;

import growthcraft.core.shared.fluids.GrowthcraftFluid;
import growthcraft.milk.shared.Reference;
import net.minecraft.util.ResourceLocation;

public class FluidKumis extends GrowthcraftFluid {

    public FluidKumis(String unlocalizedName) {
        super(unlocalizedName, new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_still"), new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_flow"));
        this.setUnlocalizedName(unlocalizedName);
    }
}
