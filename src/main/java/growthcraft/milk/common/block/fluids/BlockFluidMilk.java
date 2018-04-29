package growthcraft.milk.common.block.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;

public class BlockFluidMilk extends GrowthcraftBlockFluid {

    public BlockFluidMilk(Fluid fluidMilk) {
        super(fluidMilk, Material.WATER);
        refreshColor();
    }
}
