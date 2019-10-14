package growthcraft.grapes.common.blocks;

import growthcraft.core.shared.block.BlockCheck;
import growthcraft.core.shared.block.BlockFlags;
import growthcraft.grapes.shared.config.GrowthcraftGrapesConfig;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockGrapeVine1 extends BlockGrapeVineBase {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 6, 0.0625 * 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 16, 0.0625 * 10);

    public static final int MAX_GROWTH_HEIGHT = 5 - 1;    // 1 for the rope

    private final BlockGrapeLeaves blockLeaves;

    public BlockGrapeVine1(BlockGrapeLeaves blockLeaves) {
        super();
        setGrowthRateMultiplier(GrowthcraftGrapesConfig.grapeVineTrunkGrowthRate);
        setTickRandomly(true);
        setHardness(2.0F);
        setResistance(5.0F);
        setSoundType(SoundType.WOOD);

        this.blockLeaves = blockLeaves;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, getBoundingBox(state, worldIn, pos));
    }

    @Override
    public int getMaxAge() {
        return 1;
    }

    /************
     * TICK
     ************/
    @Override
    protected boolean canUpdateGrowth(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        IBlockState stateAbove = world.getBlockState(pos.up());
        int age = getAge(state);

        boolean isRopeAbove = BlockCheck.isRope(stateAbove.getBlock());
        boolean isLeavesOrRopeAbove = (stateAbove.getBlock() instanceof BlockGrapeLeaves) || isRopeAbove;
        boolean mayGrowHigher = world.isAirBlock(pos.up()) && canGrowHigher(world, pos, state);
        return (age == 0 && isLeavesOrRopeAbove) || mayGrowHigher || isRopeAbove;
    }

    public boolean canGrowHigher(World world, BlockPos pos, IBlockState state) {
        for (int i = 1; i < MAX_GROWTH_HEIGHT; i++) {
            if (!(world.getBlockState(pos.down(i)).getBlock() instanceof BlockGrapeVine1))
                return true;
        }

        return false;
    }

    @Override
    protected IBlockState doGrowth(World world, BlockPos pos, IBlockState state) {
        final BlockPos posAbove = pos.up();
        final IBlockState above = world.getBlockState(posAbove);
        final int type = state.getValue(SUBTYPE);
        IBlockState newState;

        /* Is there a rope block above this? */
        if (BlockCheck.isRope(above.getBlock())) {
            newState = incrementGrowth(world, pos, state);
            world.setBlockState(posAbove, blockLeaves.getDefaultState().withProperty(SUBTYPE, type), BlockFlags.UPDATE_AND_SYNC);
        } else if (world.isAirBlock(posAbove)) {
            if (canGrowHigher(world, pos, state)) {
                final IBlockState aboveAbove = world.getBlockState(posAbove.up());
                boolean connectToLeaves = aboveAbove.getBlock() instanceof BlockGrapeLeaves;

                newState = incrementGrowth(world, pos, state);
                world.setBlockState(posAbove, getDefaultState().withProperty(AGE, !connectToLeaves ? 0 : getMaxAge()).withProperty(SUBTYPE, type), BlockFlags.UPDATE_AND_SYNC);
            } else
                newState = state;
        } else if (above.getBlock() instanceof BlockGrapeLeaves) {
            newState = incrementGrowth(world, pos, state);
        } else
            newState = state;

        return newState;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.FARMLAND || state.getBlock() instanceof BlockGrapeVine1;
    }


    @Override
    protected float getGrowthRate(World world, BlockPos pos) {
        if (world.getBlockState(pos.down(1)).getBlock() == this && world.getBlockState(pos.down(2)).getBlock() == Blocks.FARMLAND) {
            return super.getGrowthRate(world, pos.down());
        }
        return super.getGrowthRate(world, pos);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    /************
     * CONDITIONS
     ************/
    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos down = pos.down();
        return BlockCheck.canSustainPlant(worldIn, down, EnumFacing.UP, this)
                || this == worldIn.getBlockState(down).getBlock()
                || worldIn.getBlockState(down).getBlock() instanceof BlockDirt
                || worldIn.getBlockState(down).getBlock() instanceof BlockGrass;
    }
}
