package growthcraft.bamboo.blocks;

import growthcraft.bamboo.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class BlockBambooPlank extends Block {

    public BlockBambooPlank() {
        super(Material.WOOD);
        this.setUnlocalizedName("bamboo_plank");
        this.setRegistryName(new ResourceLocation(Reference.MODID, "bamboo_plank"));
        this.setResistance(5.0F);
        this.setHardness(2.0F);
        this.setHarvestLevel("axe", 1);
        this.setSoundType(SoundType.WOOD);
        Blocks.FIRE.setFireInfo(this, 5, 20);

    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return 1;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return 1;
    }
}
