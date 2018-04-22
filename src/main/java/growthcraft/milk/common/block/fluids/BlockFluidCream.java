package growthcraft.milk.common.block.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;

public class BlockFluidCream extends GrowthcraftBlockFluid {

    public BlockFluidCream(Fluid fluidCream) {
        super(fluidCream, Material.WATER);
        refreshColor();
    }
}
