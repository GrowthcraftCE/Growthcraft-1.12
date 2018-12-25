package growthcraft.grapes.common.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.ICropDataProvider;
import growthcraft.grapes.common.utils.GrapeBlockCheck;
import growthcraft.core.shared.block.BlockCheck;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class BlockGrapeVineBase extends BlockBush implements IPlantable, ICropDataProvider, IGrowable {
	
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 1);
	public static final PropertyInteger SUBTYPE = PropertyInteger.create("type", 0, 7);
	
	private float growthRateMultiplier;
	
	public BlockGrapeVineBase()
	{
		this.growthRateMultiplier = 1.0f;
		setDefaultState(this.getBlockState().getBaseState().withProperty(AGE, 0).withProperty(SUBTYPE, 0));
	}
	
	public void setGrowthRateMultiplier(float rate)
	{
		this.growthRateMultiplier = rate;
	}

	public float getGrowthRateMultiplier()
	{
		return this.growthRateMultiplier;
	}

	public float getGrowthProgress(IBlockAccess world, BlockPos pos, IBlockState state)
	{
		int age = getAge(state);
		return (float)age / (float)getMaxAge();
	}

	protected boolean isGrapeVine(IBlockState state)
	{
		return GrapeBlockCheck.isGrapeVine(state.getBlock());
	}
	
	public int getAge(IBlockState state) {
		return state.getValue(AGE);
	}

	public void incrementGrowth(World world, BlockPos pos, IBlockState state)
	{
		int nextStage = getAge(state) + 1;
		if( nextStage > getMaxAge() )
			return; // Maximal stage
		world.setBlockState(pos, state.withProperty(AGE, nextStage), BlockFlags.SYNC);
	}
	
	public int getMaxAge() {
		return 1;
	}

	@SuppressWarnings("deprecation")
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return super.getDrops(world, pos, state, fortune);
    }
	
	protected float getGrowthRate(World world, BlockPos pos) {
		return BlockCrops.getGrowthChance(this, world, pos);
	}
	
    @Override
    protected boolean canSustainBush(IBlockState state)
    {
    	return state.getBlock() == Blocks.FARMLAND;
    }
	
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
		return BlockCheck.canSustainPlant(worldIn, pos.down(), EnumFacing.UP, this);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
	@SuppressWarnings("deprecation")
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
    
	/************
	 * IPLANTABLE
	 ************/
    

	@Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Crop;
	}

	@Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(pos);
	}

	/**
	 * If all conditions have passed, do plant growth
	 *
	 * @param world - world with block
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @param meta - block metadata
	 */
	protected abstract void doGrowth(World world, BlockPos pos, IBlockState state);

	/**
	 * Are the conditions right for this plant to grow?
	 *
	 * @param world - world with block
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @return true, it can grow, false otherwise
	 */
	protected abstract boolean canUpdateGrowth(World world, BlockPos pos);

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (canUpdateGrowth(worldIn, pos))
		{
			final Event.Result allowGrowthResult = Event.Result.DEFAULT; // TODO: AppleCore.validateGrowthTick(this, world, x, y, z, random);
			if (Event.Result.DENY == allowGrowthResult)
				return;

			final float f = this.getGrowthRate(worldIn, pos);

			final boolean continueGrowth = rand.nextInt((int)(getGrowthRateMultiplier() / f) + 1) == 0;
			if (Event.Result.ALLOW == allowGrowthResult || continueGrowth)
			{
				doGrowth(worldIn, pos, state);
				return;
			}
		}
		
		super.updateTick(worldIn, pos, state, rand);
	}
	
	/************
	 * IGROWABLE
	 ************/
    
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return canUpdateGrowth(worldIn, pos);
	}

	@Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if (rand.nextFloat() < 0.5D)
		{
			doGrowth(worldIn, pos, state);
		}
	}
	
	/************
	 * States
	 ************/
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, AGE, SUBTYPE);
	}

	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState().withProperty(AGE, meta & 0x1)
	    		.withProperty(SUBTYPE, (meta & 0xE) >> 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta |= state.getValue(AGE) & 0x1;
		meta |= (state.getValue(SUBTYPE) & 0xE) >> 1;
	    return meta;
	}
}
