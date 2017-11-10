package growthcraft.apples.blocks;

import growthcraft.apples.Reference;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class BlockAppleStairs extends BlockStairs {

    public BlockAppleStairs(String unlocalizedName, IBlockState state) {
        super(state);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setResistance(5.0F);
        this.setHardness(2.0F);
        this.setHarvestLevel("axe", 1);
        this.setSoundType(SoundType.WOOD);
        this.useNeighborBrightness = true;
        Blocks.FIRE.setFireInfo(this, 5, 20);

    }
}
