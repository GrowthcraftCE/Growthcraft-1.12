package growthcraft.grapes.common.blocks;

import java.util.Random;

import javax.annotation.Nonnull;

import growthcraft.core.api.utils.BlockFlags;
import growthcraft.core.common.block.GrowthcraftBlockBase;
import growthcraft.core.common.block.ICropDataProvider;
import growthcraft.core.utils.BlockCheck;
import growthcraft.grapes.utils.GrapeBlockCheck;
import net.minecraft.block.Block;
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

public abstract class BlockGrapeVineBase extends GrowthcraftBlockBase implements IPlantable, ICropDataProvider, IGrowable {
	// TODO: Derive from BlockCrops instead!
	
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
	
	private ItemStack itemDrop;
	private float growthRateMultiplier;
	
	public BlockGrapeVineBase()
	{
		super(Material.PLANTS);
		this.itemDrop = ItemStack.EMPTY;
		this.growthRateMultiplier = 1.0f;
		setDefaultState(this.getBlockState().getBaseState().withProperty(AGE, 0));
	}
	
	public void setItemDrop(ItemStack itemstack)
	{
		this.itemDrop = itemstack;
	}

	public ItemStack getItemDrop()
	{
		return this.itemDrop;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return itemDrop.getItem();
	}

	@Override
	public int quantityDropped(Random random)
	{
		return itemDrop.getCount();
	}

	public void setGrowthRateMultiplier(float rate)
	{
		this.growthRateMultiplier = rate;
	}

	public float getGrowthRateMultiplier()
	{
		return this.growthRateMultiplier;
	}

	public int getGrowthMax()
	{
		return 1;
	}
	
	public float getGrowthProgress(IBlockAccess world, int x, int y, int z, int meta)
	{
		return (float)meta / (float)getGrowthMax();
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
		if( !AGE.getAllowedValues().contains(nextStage) )
			return; // Maximal stage
		world.setBlockState(pos, state.withProperty(AGE, nextStage), BlockFlags.SYNC);
//		world.setBlockMetadataWithNotify(x, y, z, meta + 1, BlockFlags.SYNC);
//		AppleCore.announceGrowthTick(this, world, x, y, z, meta);
	}

	protected float getGrowthRate(World worldIn, BlockPos posIn)
	{
/*		final IBlockState l = world.getBlockState(pos.add(0, 0, -1)); //(x, y, z - 1);
		final IBlockState i1 = world.getBlockState(pos.add(0, 0, 1));//(x, y, z + 1);
		final IBlockState j1 = world.getBlockState(pos.add(-1, 0, 0));// (x - 1, y, z);
		final IBlockState k1 = world.getBlockState(pos.add(1, 0, 0)); // (x + 1, y, z);
		final IBlockState l1 = world.getBlockState(pos.add(-1, 0, -1));//(x - 1, y, z - 1);
		final IBlockState i2 = world.getBlockState(pos.add(1, 0, -1)); //(x + 1, y, z - 1);
		final IBlockState j2 = world.getBlockState(pos.add(-1, 0, 0)); // (x + 1, y, z + 1);
		final IBlockState k2 = world.getBlockState(pos.add(-1, 0, 1)); // (x - 1, y, z + 1);
		final boolean flag = this.isGrapeVine(j1) || this.isGrapeVine(k1);
		final boolean flag1 = this.isGrapeVine(l) || this.isGrapeVine(i1);
		final boolean flag2 = this.isGrapeVine(l1) || this.isGrapeVine(i2) || this.isGrapeVine(j2) || this.isGrapeVine(k2);
		float f = 1.0F;
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		for (int l2 = x - 1; l2 <= x + 1; ++l2)
		{
			for (int i3 = z - 1; i3 <= z + 1; ++i3)
			{
				BlockPos curPos = new BlockPos(l2, y - 1, i3);
				final Block block = world.getBlockState(curPos).getBlock();
				float f1 = 0.0F;

				if (block != null && block == Blocks.FARMLAND)
				{
					f1 = 1.0F;

					if (block.isFertile(world, l2, y - 1, i3))
					{
						f1 = 3.0F;
					}
				}

				if (l2 != x || i3 != z)
				{
					f1 /= 4.0F;
				}

				f += f1;
			}
		}

		if (flag2 || flag && flag1)
		{
			f /= 2.0F;
		}

		return f; */
		
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
//        boolean flag = this == worldIn.getBlockState(blockpos3).getBlock() || this == worldIn.getBlockState(blockpos4).getBlock();
//        boolean flag1 = this == worldIn.getBlockState(blockpos1).getBlock() || this == worldIn.getBlockState(blockpos2).getBlock();
		final boolean flag = this.isGrapeVine(worldIn.getBlockState(blockpos3)) || this.isGrapeVine(worldIn.getBlockState(blockpos4));
		final boolean flag1 = this.isGrapeVine(worldIn.getBlockState(blockpos1)) || this.isGrapeVine(worldIn.getBlockState(blockpos2));

        if (flag && flag1)
        {
            f /= 2.0F;
        }
        else
        {
//            boolean flag2 = this == worldIn.getBlockState(blockpos3.north()).getBlock() || this == worldIn.getBlockState(blockpos4.north()).getBlock() || this == worldIn.getBlockState(blockpos4.south()).getBlock() || this == worldIn.getBlockState(blockpos3.south()).getBlock();
    		final boolean flag2 = this.isGrapeVine(worldIn.getBlockState(blockpos3.north())) || this.isGrapeVine(worldIn.getBlockState(blockpos4.north())) || this.isGrapeVine(worldIn.getBlockState(blockpos4.south())) || this.isGrapeVine(worldIn.getBlockState(blockpos3.south()));

            if (flag2)
            {
                f /= 2.0F;
            }
        }

        return f;
	}
	
	public boolean canBlockStay(World world, BlockPos pos)
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
		super.updateTick(worldIn, pos, state, rand);
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
			}
		}
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
	    return new BlockStateContainer(this, AGE);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    return state.getValue(AGE);
	}
}
