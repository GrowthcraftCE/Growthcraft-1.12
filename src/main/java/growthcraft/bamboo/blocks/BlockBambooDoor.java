package growthcraft.bamboo.blocks;

import growthcraft.bamboo.Reference;
import growthcraft.bamboo.init.GrowthcraftBambooItems;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBambooDoor extends BlockDoor {

    public BlockBambooDoor(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setSoundType(SoundType.WOOD);
        this.disableStats();
        this.setHardness(3F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return GrowthcraftBambooItems.itemBambooDoor;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(GrowthcraftBambooItems.itemBambooDoor, 1);
    }
}
