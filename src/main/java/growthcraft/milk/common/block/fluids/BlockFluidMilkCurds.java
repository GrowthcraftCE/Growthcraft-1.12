package growthcraft.milk.common.block.fluids;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidMilkCurds extends GrowthcraftBlockFluid {

    public BlockFluidMilkCurds(Fluid fluidMilkCurds) {
        super(fluidMilkCurds, Material.WATER);
        refreshColor();
    }
}
