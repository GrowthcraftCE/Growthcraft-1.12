package growthcraft.apples.blocks;

import growthcraft.apples.Reference;
import growthcraft.apples.init.GrowthcraftApplesBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockAppleLeaves extends Block implements IGrowable {
    private static final AxisAlignedBB BOUNDING_BOX =
            new AxisAlignedBB(0.0625 * 0, 0.0625 * 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

    public BlockAppleLeaves(String unlocalizedName) {
        super(Material.LEAVES);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setTickRandomly(true);
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.PLANT);
        Blocks.FIRE.setFireInfo(this, 5, 20);

    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
        return super.recolorBlock(world, pos, side, EnumDyeColor.GREEN);
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
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }
/**
    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return true;
    }
*/
    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        if ( block instanceof BlockAir) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if ( worldIn.getBlockState(pos.down()).getBlock() instanceof BlockAir ){
            worldIn.setBlockState(pos.down(), GrowthcraftApplesBlocks.blockApple.getDefaultState());
        }
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
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
        Block blockDown = worldIn.getBlockState(pos.down()).getBlock();
        if ( blockDown instanceof BlockApple) {
            worldIn.destroyBlock(pos.down(), false);
        }
    }

}
