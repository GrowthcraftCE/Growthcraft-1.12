package growthcraft.milk.blocks;

import growthcraft.core.common.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidCheese extends GrowthcraftBlockFluid {

    public BlockFluidCheese(Fluid fluidButterMilk) {
        super(fluidButterMilk, Material.WATER);
        refreshColor();
    }
}