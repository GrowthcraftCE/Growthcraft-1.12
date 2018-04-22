package growthcraft.milk.common.block.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;

public class BlockFluidRennet extends GrowthcraftBlockFluid {

    public BlockFluidRennet(Fluid fluidRennet) {
        super(fluidRennet, Material.WATER);
        refreshColor();
    }
}
