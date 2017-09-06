package growthcraft.grapes.blocks;

import growthcraft.core.blocks.BlockRopeFence;
import growthcraft.grapes.Reference;
import growthcraft.grapes.init.GrowthcraftGrapesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockGrapeVineBush extends BlockBush implements IGrowable {

    private static final AxisAlignedBB BOUNDING_BOX =
            new AxisAlignedBB(0.0625 * 0, 0.0625 * 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

    public BlockGrapeVineBush(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
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

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if ( !worldIn.isRemote ) {
            super.updateTick(worldIn, pos, state, rand);
            // check the light level and pick a randomness for growth.
            if ( worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0 ) {
                this.grow( worldIn, rand, pos, state );
            }
        }
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        BlockPos[] blockPositions = new BlockPos[] { pos.north(), pos.east(), pos.south(), pos.west(), pos.down() };

        for ( BlockPos blockPosition : blockPositions ) {
            Block block = worldIn.getBlockState(blockPosition).getBlock();
            if ( block instanceof BlockRopeFence || block instanceof BlockAir ) {
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
        BlockPos[] blockPositions = new BlockPos[] { pos.north(), pos.east(), pos.south(), pos.west()  };

        for ( BlockPos blockPosition : blockPositions ) {
            Block block = worldIn.getBlockState(blockPosition).getBlock();
            if ( block instanceof BlockRopeFence ) {
                if ( !worldIn.isRemote ) {
                    worldIn.setBlockState(blockPosition, GrowthcraftGrapesBlocks.grape_vine_bush.getDefaultState());
                }
                return;
            }
        }

        if ( worldIn.getBlockState(pos.down()).getBlock() instanceof BlockAir ){
            worldIn.setBlockState(pos.down(), GrowthcraftGrapesBlocks.grape_vine_fruit.getDefaultState());
        }
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }


    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
        Block blockDown = worldIn.getBlockState(pos.down()).getBlock();
        if ( blockDown instanceof BlockGrapeVineFruit) {
            worldIn.destroyBlock(pos.down(), false);
        }
    }
}
