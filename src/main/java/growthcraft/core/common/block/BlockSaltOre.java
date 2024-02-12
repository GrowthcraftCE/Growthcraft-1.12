package growthcraft.core.common.block;

import growthcraft.core.shared.Reference;
import growthcraft.core.shared.init.GrowthcraftCoreItems;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSaltOre extends BlockOre {

    public BlockSaltOre(String unlocalizedName) {
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 1);
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return GrowthcraftCoreItems.salt.getItem();
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random random) {
        return MathHelper.getInt(random, 1, 2);
    }

    @Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
            return MathHelper.getInt(rand, 0, 2);
        }
        return 0;
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }
}
