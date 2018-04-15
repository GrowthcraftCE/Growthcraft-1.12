package growthcraft.milk.blocks.fluids;

import growthcraft.core.api.GrowthcraftFluid;
import growthcraft.milk.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidMilk extends GrowthcraftFluid {

    public FluidMilk(String unlocalizedName) {
        super(unlocalizedName, new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_still"), new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_flow"));
        this.setUnlocalizedName(unlocalizedName);
        this.setDensity(1030);
        this.setViscosity(3000);
    }
}
