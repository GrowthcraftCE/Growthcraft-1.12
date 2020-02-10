package growthcraft.milk.common.block.fluids;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidMilk extends GrowthcraftBlockFluid {

    public BlockFluidMilk(Fluid fluidMilk) {
        super(fluidMilk, Material.WATER);
        refreshColor();
    }
}
