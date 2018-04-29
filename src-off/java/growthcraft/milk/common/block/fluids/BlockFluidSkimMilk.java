package growthcraft.milk.common.block.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.Fluid;

import growthcraft.core.shared.block.GrowthcraftBlockFluid;

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
