package growthcraft.milk.common.block.fluids;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidCondensedMilk extends GrowthcraftBlockFluid {

    public BlockFluidCondensedMilk(Fluid fluidCondensedMilk) {
        super(fluidCondensedMilk, Material.WATER);
        refreshColor();
    }


}
