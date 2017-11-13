package growthcraft.milk.blocks;

import growthcraft.milk.Reference;
import growthcraft.milk.init.GrowthcraftMilkFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidMilk extends BlockFluidClassic {

    public BlockFluidMilk(String unlocalizedName) {
        super(GrowthcraftMilkFluids.fluidMilk, Material.WATER);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));

    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
