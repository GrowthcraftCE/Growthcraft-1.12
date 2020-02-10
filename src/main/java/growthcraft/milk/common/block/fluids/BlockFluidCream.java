package growthcraft.milk.common.block.fluids;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidCream extends GrowthcraftBlockFluid {

    public BlockFluidCream(Fluid fluidCream) {
        super(fluidCream, Material.WATER);
        refreshColor();
    }
}
