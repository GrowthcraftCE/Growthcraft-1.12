package growthcraft.grapes.common.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import growthcraft.core.api.utils.BlockFlags;
import growthcraft.core.common.block.GrowthcraftBlockBase;
import growthcraft.core.common.block.ICropDataProvider;
import growthcraft.core.utils.BlockCheck;
import growthcraft.grapes.utils.GrapeBlockCheck;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class BlockGrapeVineBase extends BlockCrops implements IPlantable, ICropDataProvider, IGrowable {
	
//	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 1);
	public static final PropertyInteger SUBTYPE = PropertyInteger.create("type", 0, 7);
	
//	private ItemStack itemDrop;
	private float growthRateMultiplier;
	
	public BlockGrapeVineBase()
	{
//		super(Material.PLANTS);
//		this.itemDrop = ItemStack.EMPTY;
		this.growthRateMultiplier = 1.0f;
		setDefaultState(this.getBlockState().getBaseState().withProperty(AGE, 0).withProperty(SUBTYPE, 0));
	}
	
/*	public void setItemDrop(ItemStack itemstack)
	{
		this.itemDrop = itemstack;
	}

	public ItemStack getItemDrop()
	{
		return this.itemDrop;
	}*/
	
//	@Override
//    protected PropertyInteger getAgeProperty()
//    {
//        return AGE;
//    }
	
/*	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return itemDrop.getItem();
	}

	@Override
	public int quantityDropped(Random random)
	{
		return itemDrop.getCount();
	} */

	public void setGrowthRateMultiplier(float rate)
	{
		this.growthRateMultiplier = rate;
	}

	public float getGrowthRateMultiplier()
	{
		return this.growthRateMultiplier;
	}

/*	public int getGrowthMax()
	{
		return 1;
	} */
	
	public float getGrowthProgress(IBlockAccess world, BlockPos pos, IBlockState state)
	{
		int age = getAge(state);
		return (float)age / (float)getMaxAge();
	}

	protected boolean isGrapeVine(IBlockState state)
	{
		return GrapeBlockCheck.isGrapeVine(state.getBlock());
	}
	
//	public int getAge(IBlockState state) {
//		return state.getValue(AGE);
//	}

	public void incrementGrowth(World world, BlockPos pos, IBlockState state)
	{
		int nextStage = getAge(state) + 1;
		if( nextStage > getMaxAge() /*!AGE.getAllowedValues().contains(nextStage)*/ )
			return; // Maximal stage
		world.setBlockState(pos, state.withProperty(AGE, nextStage), BlockFlags.SYNC);
	}
	
	@Override
	public int getMaxAge() {
		return 1;
	}
	
    @Override
    protected Item getSeed() {
        // Only the BlockGrapeLeaves should return a seed.
        return null;
    }

    @Override
    protected Item getCrop() {
        // Only the BlockGrapeLeaves should return a seed.
        return null;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return super.getDrops(world, pos, state, fortune);
    }
	
/*
	protected float getGrowthRate(World worldIn, BlockPos posIn)
	{		
        float f = 1.0F;
        BlockPos blockpos = posIn.down();

        for (int i = -1; i <= 1; ++i)
        {
            for (int j = -1; j <= 1; ++j)
            {
                float f1 = 0.0F;
                IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));

//                if (iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, blockpos.add(i, 0, j), net.minecraft.util.EnumFacing.UP, this))
                if( iblockstate.getBlock() == Blocks.FARMLAND )
                {
                    f1 = 1.0F;

                    if (iblockstate.getBlock().isFertile(worldIn, blockpos.add(i, 0, j)))
                    {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = posIn.north();
        BlockPos blockpos2 = posIn.south();
        BlockPos blockpos3 = posIn.west();
        BlockPos blockpos4 = posIn.east();
		final boolean flag = this.isGrapeVine(worldIn.getBlockState(blockpos3)) || this.isGrapeVine(worldIn.getBlockState(blockpos4));
		final boolean flag1 = this.isGrapeVine(worldIn.getBlockState(blockpos1)) || this.isGrapeVine(worldIn.getBlockState(blockpos2));

        if (flag && flag1)
        {
            f /= 2.0F;
        }
        else
        {
    		final boolean flag2 = this.isGrapeVine(worldIn.getBlockState(blockpos3.north())) || this.isGrapeVine(worldIn.getBlockState(blockpos4.north())) || this.isGrapeVine(worldIn.getBlockState(blockpos4.south())) || this.isGrapeVine(worldIn.getBlockState(blockpos3.south()));

            if (flag2)
            {
                f /= 2.0F;
            }
        }

        return f;
	}*/
	
	protected float getGrowthRate(World world, BlockPos pos) {
		return getGrowthChance(this, world, pos);
	}
	
    @Override
    protected boolean canSustainBush(IBlockState state)
    {
    	return state.getBlock() == Blocks.FARMLAND;
    }
	
/*	public boolean canBlockStay(World world, BlockPos pos)
	{
		return BlockCheck.canSustainPlant(world, pos.down(), EnumFacing.UP, this);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canBlockStay(worldIn, pos))
		{
			this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
			worldIn.setBlockToAir(pos);
		}
	} */

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

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
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
		// super.updateTick(worldIn, pos, state, rand);
		if (canUpdateGrowth(worldIn, pos))
		{
			final Event.Result allowGrowthResult = Event.Result.DEFAULT; // TODO: AppleCore.validateGrowthTick(this, world, x, y, z, random);
			if (Event.Result.DENY == allowGrowthResult)
				return;

//			final int meta = world.getBlockMetadata(x, y, z);
//			final IBlockState state = worldIn.getBlockState(pos);
			final float f = this.getGrowthRate(worldIn, pos);

			final boolean continueGrowth = rand.nextInt((int)(getGrowthRateMultiplier() / f) + 1) == 0;
			if (Event.Result.ALLOW == allowGrowthResult || continueGrowth)
			{
				doGrowth(worldIn, pos, state);
				return;
			}
		}
		
		this.checkAndDropBlock(worldIn, pos, state);
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
