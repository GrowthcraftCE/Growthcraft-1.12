package growthcraft.grapes.common.blocks;

import java.util.List;

import javax.annotation.Nullable;

import growthcraft.core.api.utils.BlockFlags;
import growthcraft.core.utils.BlockCheck;
import growthcraft.grapes.GrowthcraftGrapesConfig;
import growthcraft.grapes.Reference;
import growthcraft.grapes.init.GrowthcraftGrapesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGrapeVine1 extends BlockGrapeVineBase {
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 6, 0.0625 * 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 16, 0.0625 * 10);
	
	public BlockGrapeVine1(String unlocalizedName) {
		super();
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		setGrowthRateMultiplier(GrowthcraftGrapesConfig.grapeVineTrunkGrowthRate);
		setTickRandomly(true);
		setHardness(2.0F);
		setResistance(5.0F);
	}
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }
    
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
	protected boolean canUpdateGrowth(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		int age = getAge(state);
		return age == 0 || world.isAirBlock(pos.up());
	}

	@Override
	protected void doGrowth(World world, BlockPos pos, IBlockState state)
	{
		final BlockPos posAbove = pos.up();
		final IBlockState above = world.getBlockState(posAbove);
		/* Is there a rope block above this? */
		if (BlockCheck.isRope(above.getBlock()))
		{
			incrementGrowth(world, pos, state);
			world.setBlockState(posAbove, GrowthcraftGrapesBlocks.grapeLeaves.getDefaultState(), BlockFlags.UPDATE_AND_SYNC);
		}
		else if (world.isAirBlock(posAbove))
		{
			incrementGrowth(world, pos, state);
			world.setBlockState(posAbove, getDefaultState().withProperty(AGE, 0), BlockFlags.UPDATE_AND_SYNC);
		}
		else if (GrowthcraftGrapesBlocks.grapeLeaves.getBlock() == above.getBlock())
		{
			incrementGrowth(world, pos, state);
		}
	}
	
    @Override
    protected boolean canSustainBush(IBlockState state)
    {
    	return state.getBlock() == Blocks.FARMLAND || state.getBlock() instanceof BlockGrapeVine1;
    }


	@Override
	protected float getGrowthRate(World world, BlockPos pos)
	{
		if (world.getBlockState(pos.down(1)).getBlock() == this && world.getBlockState(pos.down(2)).getBlock() == Blocks.FARMLAND)
		{
			return super.getGrowthRate(world, pos.down());
		}
		return super.getGrowthRate(world, pos);
	}
	
	/************
	 * CONDITIONS
	 ************/
/*	@Override
	public boolean canBlockStay(World world, BlockPos pos)
	{
		BlockPos down = pos.down();
		return BlockCheck.canSustainPlant(world, down, EnumFacing.UP, this) ||
			this == world.getBlockState(down).getBlock();
	} */
}
