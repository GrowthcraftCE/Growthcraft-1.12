package growthcraft.milk.common.block.fluids;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidSkimMilk extends GrowthcraftBlockFluid {

    public BlockFluidSkimMilk(Fluid fluidSkimMilk) {
        super(fluidSkimMilk, Material.WATER);
    }

/*    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
*/

}
