package growthcraft.milk.common.block.fluids;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidRennet extends GrowthcraftBlockFluid {

    public BlockFluidRennet(Fluid fluidRennet) {
        super(fluidRennet, Material.WATER);
        refreshColor();
    }
}
