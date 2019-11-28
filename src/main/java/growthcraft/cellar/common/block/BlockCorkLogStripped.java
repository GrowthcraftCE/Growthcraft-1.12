package growthcraft.cellar.common.block;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.block.GrowthcraftBlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        // Need to check for randomness and rate of bark regrowth.
    }
}
