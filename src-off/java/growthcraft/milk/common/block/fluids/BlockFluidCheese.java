package growthcraft.milk.common.block.fluids;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidCheese extends GrowthcraftBlockFluid {

    public BlockFluidCheese(Fluid fluidButterMilk) {
        super(fluidButterMilk, Material.WATER);
        refreshColor();
    }
}