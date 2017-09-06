package growthcraft.hops.blocks;

import growthcraft.core.blocks.BlockRopeFence;
import growthcraft.hops.Reference;
import growthcraft.hops.init.GrowthcraftHopsBlocks;
import growthcraft.hops.init.GrowthcraftHopsItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockHopsTrunk extends BlockCrops implements IGrowable {

    private int maxHeight = 3;

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 6, 0.0625 * 0, 0.0625 * 6,
            0.0625 * 10, 0.0625 * 16, 0.0625 * 10);

    public BlockHopsTrunk(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BOUNDING_BOX;
    }

    /* Private */
    private void growHopPlant(World worldIn, BlockPos pos, IBlockState state) {
        for ( int i = 1; i <= maxHeight; i++ ) {
            Block blockUp = worldIn.getBlockState(pos.up(i)).getBlock();
            if (blockUp instanceof BlockRopeFence) {
                worldIn.setBlockState(pos.up(), GrowthcraftHopsBlocks.hops_bush.getDefaultState());
                break;
            }
        }
    }

    /* BlockCrops */

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    protected Item getCrop() {
        return null;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return super.getDrops(world, pos, state, fortune);
    }

    @Override
    protected Item getSeed() {
        return GrowthcraftHopsItems.hop_seeds;
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        super.grow(worldIn, pos, state);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (this.getAge(state) == 7) {
            this.growHopPlant(worldIn, pos, state);
        }
    }

    /* IGrowable */

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        for ( int i = 1; i <= maxHeight; i++ ) {
            Block blockUp = worldIn.getBlockState(pos.up(i)).getBlock();
            if (blockUp instanceof BlockRopeFence) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        super.grow(worldIn, pos, state);
        if(this.getAge(state) == 7) {
            this.growHopPlant(worldIn, pos, state);
        }
    }


}
