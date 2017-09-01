package growthcraft.core.blocks;

import growthcraft.core.Reference;
import growthcraft.core.init.GrowthcraftCoreItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class BlockSalt extends Block {

    public BlockSalt(String unlocalizedName) {
        super(Material.CLAY);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 13);
    }

    @Override
    public int quantityDropped(Random random) {
        return 9;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return GrowthcraftCoreItems.salt;
    }
}
