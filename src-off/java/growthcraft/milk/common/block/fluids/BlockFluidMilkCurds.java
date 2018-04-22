package growthcraft.milk.common.block.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;

public class BlockFluidMilkCurds extends GrowthcraftBlockFluid {

    public BlockFluidMilkCurds(Fluid fluidMilkCurds) {
        super(fluidMilkCurds, Material.WATER);
        refreshColor();
    }
}
