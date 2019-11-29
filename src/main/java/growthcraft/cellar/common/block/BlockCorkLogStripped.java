package growthcraft.cellar.common.block;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.init.GrowthcraftCellarBlocks;
import growthcraft.core.shared.block.GrowthcraftBlockLog;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockCorkLogStripped extends GrowthcraftBlockLog {

    private static final AxisAlignedBB AABB_FULL_BLOCK = new AxisAlignedBB(
            0.0625 * 1, 0.0625 * 0, 0.0625 * 1,
            0.0625 * 15, 0.0625 * 16, 0.0625 * 15);

    public BlockCorkLogStripped(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName);
        this.setTickRandomly(true);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB_FULL_BLOCK;
    }


    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_FULL_BLOCK);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (canChangeToLog(worldIn, pos) && rand.nextInt(100) <= 50) {
            worldIn.setBlockState(pos, GrowthcraftCellarBlocks.blockCorkLog.getDefaultState());
        }
    }

    /**
     * Checks to determine if the tree has been broken and whether it is ok to continue growing.
     *
     * @param worldIn World of this instance.
     * @param pos BlockPos if this instance of the block.
     * @return Return a boolean on whether this block can change.
     */
    private boolean canChangeToLog(World worldIn, BlockPos pos) {
        return lookForValidCorkBlocks(worldIn, pos, EnumFacing.UP, 7) && lookForValidCorkBlocks(worldIn, pos, EnumFacing.DOWN, 7);
    }

    private boolean lookForValidCorkBlocks(World worldIn, BlockPos pos, EnumFacing facing, int range) {

        for (int i = 1; i <= range; i++) {
            IBlockState state;
            if (facing == EnumFacing.UP) {
                state = worldIn.getBlockState(pos.up(i));
            } else {
                state = worldIn.getBlockState(pos.down(i));
            }

            if (state.getBlock() instanceof BlockCorkLeaves
                    || state.getBlock() instanceof BlockGrass
                    || state.getBlock() instanceof BlockDirt) {
                return true;
            }

            if (!(state.getBlock() instanceof BlockCorkLog || state.getBlock() instanceof BlockCorkLogStripped)) {
                return false;
            }
        }

        return false;
    }

}
