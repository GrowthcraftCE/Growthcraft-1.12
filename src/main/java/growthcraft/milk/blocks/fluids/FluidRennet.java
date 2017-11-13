package growthcraft.milk.blocks.fluids;

import growthcraft.milk.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidRennet extends Fluid {

    public FluidRennet(String unlocalizedName) {
        super(unlocalizedName, new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_still"), new ResourceLocation(Reference.MODID, "blocks/fluids/" + unlocalizedName + "_flow"));
        this.setUnlocalizedName(unlocalizedName);
    }
}
