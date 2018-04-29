package growthcraft.milk.common.block.fluids;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidWhey extends GrowthcraftBlockFluid {

    public BlockFluidWhey(Fluid fluidWhey) {
        super(fluidWhey, Material.WATER);
        refreshColor();
    }
}
