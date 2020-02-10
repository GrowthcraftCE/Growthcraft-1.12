package growthcraft.milk.common.block.fluids;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidPasteurizedMilk extends GrowthcraftBlockFluid {

    public BlockFluidPasteurizedMilk(Fluid fluidPasteurizedMilk) {
        super(fluidPasteurizedMilk, Material.WATER);
        refreshColor();
    }


}
