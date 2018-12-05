package growthcraft.milk.common.block.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;

public class BlockFluidPasteurizedMilk extends GrowthcraftBlockFluid {

    public BlockFluidPasteurizedMilk(Fluid fluidPasteurizedMilk) {
        super(fluidPasteurizedMilk, Material.WATER);
        refreshColor();
    }


}
