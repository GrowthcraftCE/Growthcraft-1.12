package growthcraft.milk.blocks;

import growthcraft.core.common.block.GrowthcraftBlockFluid;
import growthcraft.milk.blocks.fluids.FluidWhey;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidWhey extends GrowthcraftBlockFluid {

    public BlockFluidWhey(Fluid fluidWhey) {
        super(fluidWhey, Material.WATER);
//        this.setUnlocalizedName(unlocalizedName);
//        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
//        this.setCreativeTab(tabGrowthcraft);
        refreshColor();
    }

/*    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }*/

}
