package growthcraft.bamboo.blocks;

import growthcraft.bamboo.Reference;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class BlockBambooStairs extends BlockStairs {

    public BlockBambooStairs(String unlocalizedName, IBlockState state) {
        super(state);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setResistance(5.0F);
        this.setHardness(2.0F);
        this.setHarvestLevel("axe", 1);
        this.setSoundType(SoundType.WOOD);
        this.useNeighborBrightness = true;
    }
}
