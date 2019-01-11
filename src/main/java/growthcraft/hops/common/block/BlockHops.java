package growthcraft.hops.common.block;

import growthcraft.core.shared.block.BlockCheck;
import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.IBlockRope;
import growthcraft.core.shared.block.ICropDataProvider;
import growthcraft.core.shared.init.GrowthcraftCoreBlocks;
import growthcraft.core.shared.init.GrowthcraftCoreItems;
import growthcraft.hops.shared.Reference;
import growthcraft.hops.shared.config.GrowthcraftHopsConfig;
import growthcraft.hops.shared.init.GrowthcraftHopsItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockHops extends BlockBush implements IBlockRope, IPlantable, ICropDataProvider, IGrowable {
	
	public static class HopsStage
	{
		public static final int BINE = 0;
		public static final int SMALL = 1;
		public static final int BIG = 2;
		public static final int FRUIT = 3;

		private HopsStage() {}
	}

	private final int hopVineMaxYield = GrowthcraftHopsConfig.hopVineMaxYield;
	private final float hopVineGrowthRate = GrowthcraftHopsConfig.hopVineGrowthRate;
	private final float hopVineFlowerSpawnRate = GrowthcraftHopsConfig.hopVineFlowerSpawnRate;
	
	private static final AxisAlignedBB BOUNDING_BINE = new AxisAlignedBB(6*0.0625F, 0.0F, 6*0.0625F, 10*0.0625F, 5*0.0625F, 10*0.0625F);
	private static final AxisAlignedBB BOUNDING_SMALL = new AxisAlignedBB(4*0.0625F, 0.0F, 4*0.0625F, 12*0.0625F, 8*0.0625F, 12*0.0625F);
	private static final AxisAlignedBB BOUNDING_BIG = new AxisAlignedBB(0*0.0625F, 0.0F, 0*0.0625F, 16*0.0625F, 16*0.0625F, 16*0.0625F);

	public static final PropertyInteger AGE = PropertyInteger.create("age", HopsStage.BINE, HopsStage.FRUIT);
	public static final PropertyBool OPAQUEBELOW = PropertyBool.create("opaquebelow");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    
	private static Random rand = new Random();
	
    public BlockHops(String unlocalizedName) {
    	super(Material.PLANTS);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    	setHardness(0.0F);
		setSoundType(SoundType.PLANT);
		setDefaultState(this.getBlockState().getBaseState()
				.withProperty(AGE, 0)
				.withProperty(OPAQUEBELOW, Boolean.valueOf(true))
                .withProperty(NORTH, Boolean.valueOf(false))
                .withProperty(EAST, Boolean.valueOf(false))
                .withProperty(SOUTH, Boolean.valueOf(false))
                .withProperty(WEST, Boolean.valueOf(false))
                .withProperty(UP, Boolean.valueOf(false))
                .withProperty(DOWN, Boolean.valueOf(false)));
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	int age = getAge(state);
    	switch( age ) {
    	case HopsStage.BINE:
    		return BOUNDING_BINE;
    	case HopsStage.SMALL:
    		return BOUNDING_SMALL;
    	default:
    		return BOUNDING_BIG;
    	}
    }

	@SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
    	// TODO: Add ropes.
    	
    	int age = getAge(state);
    	if( age > HopsStage.BINE )
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, getBoundingBox(state, worldIn, pos));
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

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState other = blockAccess.getBlockState(pos.offset(side));
        Block block = other.getBlock();
        return block != this || getAge( blockState ) < HopsStage.BIG || getAge( other ) < HopsStage.BIG;
    }
	
	/************
	 * TICK
	 ************/
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if( worldIn.isRemote )
			return;
		if (!this.isSupported(worldIn, pos, state))
		{
			worldIn.setBlockState(pos, GrowthcraftCoreBlocks.rope_fence.getDefaultState());
			List<ItemStack> drops = super.getDrops(worldIn, pos, state, 0);
			for( ItemStack drop : drops ) {
				spawnAsEntity(worldIn, pos, drop);
			}
		}
		else
		{
			// NOTE: Same as in BlockReed.updateTick(World, BlockPos, IBlockState, Random)
			
			if( ForgeHooks.onCropsGrowPre(worldIn, pos, state, true) ) {
				grow(worldIn, rand, pos, state);
				ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
			}
		}
	}
	
    public int getAge(IBlockState state) {
    	return state.getValue(AGE);
    }
    
	public boolean isMature(IBlockAccess world, BlockPos pos, IBlockState state)
	{
		return getAge(state) >= HopsStage.FRUIT;
	}

	public float getGrowthProgress(IBlockAccess world, BlockPos pos, IBlockState state)
	{
		return (float)getAge(state) / (float)HopsStage.FRUIT;
	}

	protected void incrementGrowth(World world, BlockPos pos, IBlockState state)
	{
		int nextAge = getAge(state) + 1;
		if( nextAge <= HopsStage.FRUIT ) {
			world.setBlockState(pos, state.withProperty(AGE, nextAge), BlockFlags.SYNC);
			// TODO: AppleCore.announceGrowthTick(this, world, x, y, z, previousMetadata);
		}
	}

	public void spreadLeaves(World world, BlockPos pos)
	{
		world.setBlockState(pos.up(), getDefaultState().withProperty(AGE, HopsStage.SMALL), BlockFlags.UPDATE_AND_SYNC);
	}

	public boolean canSpreadLeaves(World world, BlockPos pos)
	{
		BlockPos up = pos.up();
		IBlockState upState = world.getBlockState(up);
		return BlockCheck.isRope(upState.getBlock()) && this.isSupported(world, up, upState);
	}
	
	private float getGrowthRateLoop(World world, BlockPos pos, IBlockState state)
	{
		if (BlockCheck.canSustainPlant(world, pos.down(), EnumFacing.UP, this))
		{
			return getGrowthRate(world, pos);
		}
		else
		{
			for (int loop = 1; loop < 5; ++loop)
			{
				BlockPos dwn = pos.down(loop);
				IBlockState dwnState = world.getBlockState(dwn);
				
				if (dwnState.getBlock() != this)
				{
					return getGrowthRate(world, pos);
				}

				if (isVineRoot(world, dwn, dwnState))
				{
					return getGrowthRate(world, dwn);
				}
			}

			return getGrowthRate(world, pos);
		}
	}
	
    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    	return getAge(state) < HopsStage.FRUIT || canSpreadLeaves(worldIn, pos);
    }
    
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		final Event.Result allowGrowthResult = Event.Result.DEFAULT; // TODO: AppleCore.validateGrowthTick(this, world, x, y, z, random);
		if (allowGrowthResult == Event.Result.DENY)
			return;

		int age = getAge(state);
		final float f = this.getGrowthRateLoop(worldIn, pos, state);

		if (age < HopsStage.BIG)
		{
			if (allowGrowthResult == Event.Result.ALLOW || (rand.nextInt((int)(this.hopVineGrowthRate / f) + 1) == 0))
			{
				incrementGrowth(worldIn, pos, state);
			}
		}
		else if ((age >= HopsStage.BIG) && canSpreadLeaves(worldIn, pos))
		{
			if (allowGrowthResult == Event.Result.ALLOW || (rand.nextInt((int)(this.hopVineGrowthRate / f) + 1) == 0))
			{
				spreadLeaves(worldIn, pos);
			}
		}
		else
		{
			if (allowGrowthResult == Event.Result.ALLOW || (rand.nextInt((int)(this.hopVineFlowerSpawnRate / f) + 1) == 0))
			{
				incrementGrowth(worldIn, pos, state);
			}
		}
	}	
	
	private float getGrowthRate(World world, BlockPos pos)
	{
		return BlockCrops.getGrowthChance(this, world, pos);
	}
	
	public void removeFruit(World world, BlockPos pos, IBlockState state)
	{
		if( getAge(state) >= HopsStage.FRUIT )
			world.setBlockState(pos, state.withProperty(AGE, HopsStage.BIG), BlockFlags.UPDATE_AND_SYNC);
	}

	/************
	 * TRIGGERS
	 ************/
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (getAge(state) >= HopsStage.FRUIT)
		{
			if (!worldIn.isRemote)
			{
				removeFruit(worldIn, pos, state);
				dropFruitAsItemWithChance(worldIn, getAdjacentBlockPosToPlayer(pos, playerIn), state, 1.0f, 0);
			}
			return true;
		}
		return false;
	}
	
    private void dropFruitAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
    	if (getAge(state) < HopsStage.FRUIT)
    		return;
    	
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            if (worldIn.rand.nextFloat() <= chance)
            {
                spawnAsEntity(worldIn, pos, getFruitDrop(fortune));
            }
        }
    }
    
    private BlockPos getAdjacentBlockPosToPlayer(BlockPos pos, EntityPlayer playerIn) {
    	BlockPos playerBlock = playerIn.getPosition();
    	int offsX = 0;
    	if( pos.getX() > playerBlock.getX() )
    		offsX = -1;
    	else if( pos.getX() < playerBlock.getX() )
    		offsX = 1;
    	
    	int offsZ = 0;
    	if( pos.getZ() > playerBlock.getZ() )
    		offsZ = -1;
    	else if( pos.getZ() < playerBlock.getZ() )
    		offsZ = 1;

    	return pos.east(offsX).south(offsZ);
    }

	/************
	 * CONDITIONS
	 ************/
	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
		// Decaying by itself
		return true;
	}
	
	@Override
    protected boolean canSustainBush(IBlockState state)
    {
    	return state.getBlock() == this || state.getBlock() == Blocks.FARMLAND;
    }
	
	public boolean isSupported(World world, BlockPos pos, IBlockState state)
	{
		if (BlockCheck.canSustainPlant(world, pos.down(), EnumFacing.UP, this))
		{
			return true;
		}
		else
		{
			int loop = 1;

			while (loop < 5)
			{
				BlockPos dwn = pos.down(loop);
				IBlockState dwnState = world.getBlockState(dwn);
				
				if (dwnState.getBlock() != this)
				{
					return false;
				}

				if (isVineRoot(world, dwn, dwnState))
				{
					return true;
				}
				loop++;
			}

			return false;
		}
	}

	private boolean isVineRoot(World world, BlockPos pos, IBlockState state)
	{
		return state.getBlock() == this &&
			BlockCheck.canSustainPlant(world, pos.down(), EnumFacing.UP, this) &&
			getAge(state) >= HopsStage.BIG;
	}
    
	/************
	 * STUFF
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
//		final int meta = world.getBlockMetadata(x, y, z);
		return getAge(state) < HopsStage.FRUIT ? GrowthcraftHopsItems.hop_seeds.asStack() : GrowthcraftHopsItems.hops.asStack();
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return false;
	}

	@Override
	public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        return block instanceof IBlockRope;
	}
	
    @Override
    public boolean canRopeBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
    	// TODO: Check if this method is correct! Remove explicit dependencies of BlockRopeFence and BlockRopeKnot!
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        return block == GrowthcraftCoreBlocks.rope_fence.getBlock() || block == GrowthcraftCoreBlocks.rope_knot.getBlock();
    }
	
	/************
	 * DROPS
	 ************/
	@SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	List<ItemStack> drops = new java.util.ArrayList<ItemStack>();

    	drops.add(GrowthcraftCoreItems.rope.asStack(1));
    	int age = getAge(state); 
    	if( age >= HopsStage.BIG ) {
    		drops.add(getFruitDrop(fortune));
    	}
    	if( age == HopsStage.BINE || rand.nextInt(3) == 0 ) {
    		drops.add(GrowthcraftHopsItems.hop_seeds.asStack(1));
    	}
    	return drops;
    }
    
    public ItemStack getFruitDrop(int fortune) {
    	return GrowthcraftHopsItems.hops.asStack(1 + rand.nextInt(hopVineMaxYield));
    }
    
    @Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
//    	int typeID = state.getValue(SUBTYPE);
//    	IGrapeType type = GrapeTypeUtils.getTypeBySubID(grapeTypes, typeID);
//		return type.asSeedsStack().getItem();
    	return null;
	}
    
    @Override
    public int damageDropped(IBlockState state) {
//   	int typeID = state.getValue(SUBTYPE);
//    	IGrapeType type = GrapeTypeUtils.getTypeBySubID(grapeTypes, typeID);
//    	return type.asSeedsStack().getItemDamage();
    	return 0;
    }

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
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
		return getDefaultState();
	}
	
	/************
	 * STATES
	 ************/
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, AGE, NORTH, EAST, SOUTH, WEST, UP, DOWN, OPAQUEBELOW);
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState().withProperty(AGE, meta & 0x3);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta |= state.getValue(AGE) & 0x3;
	    return meta;
	}
	
    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	IBlockState stateBelow = worldIn.getBlockState(pos.down());
    	
        return state
        		.withProperty(OPAQUEBELOW, stateBelow.isOpaqueCube())
        		.withProperty(NORTH, canConnectRopeTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, canConnectRopeTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, canConnectRopeTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canConnectRopeTo(worldIn, pos, EnumFacing.WEST))
                .withProperty(UP, canConnectRopeTo(worldIn, pos, EnumFacing.UP))
                .withProperty(DOWN, canConnectRopeTo(worldIn, pos, EnumFacing.DOWN));
    }
}
