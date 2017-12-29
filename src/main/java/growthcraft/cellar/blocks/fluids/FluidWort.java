package growthcraft.cellar.blocks.fluids;

import growthcraft.cellar.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidWort extends Fluid {

    public FluidWort(String unlocalizedName) {
        super(unlocalizedName, new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_still"), new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_flow"));
        this.setUnlocalizedName(unlocalizedName);
    }

}
