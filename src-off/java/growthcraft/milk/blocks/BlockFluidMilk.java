package growthcraft.milk.blocks;

import growthcraft.milk.Reference;
import growthcraft.milk.blocks.fluids.FluidMilk;
import growthcraft.milk.init.GrowthcraftMilkFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

import growthcraft.core.common.block.GrowthcraftBlockFluid;

public class BlockFluidMilk extends GrowthcraftBlockFluid {

    public BlockFluidMilk(Fluid fluidMilk) {
        super(fluidMilk, Material.WATER);
//        this.setUnlocalizedName(unlocalizedName);
//        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
//        this.setCreativeTab(tabGrowthcraft);
        refreshColor();
    }

/*    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    } */
}
