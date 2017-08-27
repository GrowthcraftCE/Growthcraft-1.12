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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockHopsTrunk extends BlockCrops implements IGrowable {

    public BlockHopsTrunk(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    /* Private */
    private void growHopPlant(World worldIn, BlockPos pos, IBlockState state) {
        Block blockUp = worldIn.getBlockState(pos.up()).getBlock();
        if ( blockUp instanceof BlockRopeFence) {
            worldIn.setBlockState(pos.up(), GrowthcraftHopsBlocks.hops_bush.getDefaultState());
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
        Block blockUp = worldIn.getBlockState(pos.up()).getBlock();
        if ( blockUp instanceof BlockRopeFence) {
            return true;
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
