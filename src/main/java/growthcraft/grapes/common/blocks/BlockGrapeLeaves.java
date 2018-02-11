package growthcraft.grapes.common.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.api.utils.BlockFlags;
import growthcraft.core.blocks.BlockRopeFence;
import growthcraft.core.blocks.BlockRopeKnot;
import growthcraft.core.common.block.IBlockRope;
import growthcraft.core.init.GrowthcraftCoreBlocks;
import growthcraft.core.init.GrowthcraftCoreItems;
import growthcraft.core.utils.BlockCheck;
import growthcraft.grapes.GrowthcraftGrapesConfig;
import growthcraft.grapes.utils.GrapeBlockCheck;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrapeLeaves extends BlockBush implements IGrowable, IBlockRope {

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 0, 0.0625 * 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

	private final int grapeLeavesGrowthRate = GrowthcraftGrapesConfig.grapeLeavesGrowthRate;
	private final int grapeSpawnRate = GrowthcraftGrapesConfig.grapeSpawnRate;
	// how far can a grape leaf grow before it requires support from a trunk
	private final int grapeVineSupportedLength = GrowthcraftGrapesConfig.grapeVineSupportedLength;
	
	public static final PropertyInteger SUBTYPE = BlockGrapeVineBase.SUBTYPE;
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
	
	private final BlockGrapeFruit blockFruit;
	
	public BlockGrapeLeaves(BlockGrapeFruit blockFruit/*String unlocalizedName*/) {
		super();
//        this.setUnlocalizedName(unlocalizedName);
//        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		setDefaultState(this.getBlockState().getBaseState().withProperty(SUBTYPE, 0));
		setTickRandomly(true);
		setHardness(0.2F);
		setLightOpacity(1);
		setSoundType(SoundType.PLANT);
		
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Boolean.valueOf(false))
                .withProperty(EAST, Boolean.valueOf(false))
                .withProperty(SOUTH, Boolean.valueOf(false))
                .withProperty(WEST, Boolean.valueOf(false))
                .withProperty(UP, Boolean.valueOf(false))
                .withProperty(DOWN, Boolean.valueOf(false)));
		
		this.blockFruit = blockFruit;
    }
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, getBoundingBox(state, worldIn, pos));
    }
    
	private boolean isTrunk(World world, BlockPos pos)
	{
		return GrapeBlockCheck.isGrapeVineTrunk(world.getBlockState(pos).getBlock());
	}

	public boolean isSupportedByTrunk(World world, BlockPos pos)
	{
		return isTrunk(world, pos.down());
	}
    
	/**
	 * Use this method to check if the block can grow outwards on a rope
	 *
	 * @param world - the world
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @return true if the block can grow here, false otherwise
	 */
	public boolean canGrowOutwardsOnRope(World world, BlockPos pos)
	{
		if (BlockCheck.isRope(world.getBlockState(pos.add(1, 0, 0)).getBlock())) return true;
		if (BlockCheck.isRope(world.getBlockState(pos.add(-1, 0, 0)).getBlock())) return true;
		if (BlockCheck.isRope(world.getBlockState(pos.add(0, 0, 1)).getBlock())) return true;
		if (BlockCheck.isRope(world.getBlockState(pos.add(0, 0, -1)).getBlock())) return true;
		return false;
	}
	
	public boolean canGrowOutwards(World world, BlockPos pos)
	{
		final boolean leavesTotheSouth = world.getBlockState(pos.add(0, 0, 1)).getBlock() == this;
		final boolean leavesToTheNorth = world.getBlockState(pos.add(0, 0, -1)).getBlock() == this;
		final boolean leavesToTheEast = world.getBlockState(pos.add(1, 0, 0)).getBlock() == this;
		final boolean leavesToTheWest = world.getBlockState(pos.add(-1, 0, 0)).getBlock() == this;

		if (!leavesTotheSouth && !leavesToTheNorth && !leavesToTheEast && !leavesToTheWest) return false;

		for (int i = 1; i <= grapeVineSupportedLength; ++i)
		{
			if (leavesTotheSouth && isTrunk(world, pos.add(0, -1, i))) return true;
			if (leavesToTheNorth && isTrunk(world, pos.add(0, -1, -i))) return true;
			if (leavesToTheEast && isTrunk(world, pos.add(i, -1, 0))) return true;
			if (leavesToTheWest && isTrunk(world, pos.add(-i, -1, 0))) return true;
		}
		return false;
	}

	/**
	 * Variation of canGrowOutwards, use this method to check rope blocks
	 *
	 * @param world - the world
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @return true if the block can grow here, false otherwise
	 */
	public boolean canGrowHere(World world, BlockPos pos)
	{
		if (BlockCheck.isRope(world.getBlockState(pos).getBlock()))
		{
			return canGrowOutwards(world, pos);
		}
		return false;
	}
	
	private void setGrapeBlock(World world, BlockPos pos, IBlockState state)
	{
		int type = state.getValue(SUBTYPE);
		world.setBlockState(pos, blockFruit.getDefaultState().withProperty(SUBTYPE, type), BlockFlags.UPDATE_AND_SYNC);
	}
	
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		BlockPos posDown = pos.down();
		if (worldIn.isAirBlock(posDown) && (rand.nextInt(this.grapeSpawnRate) == 0))
		{
			setGrapeBlock(worldIn, posDown, state);
		}

		if (worldIn.rand.nextInt(this.grapeLeavesGrowthRate) == 0)
		{
			if (canGrowOutwardsOnRope(worldIn, pos))
			{
				final EnumFacing dir = BlockCheck.DIR4[rand.nextInt(4)];

				BlockPos posDir = pos.add(dir.getFrontOffsetX(), 0, dir.getFrontOffsetZ());
				if (canGrowHere(worldIn, posDir))
				{
					int type = state.getValue(SUBTYPE);
					worldIn.setBlockState(posDir, getDefaultState().withProperty(SUBTYPE, type), BlockFlags.UPDATE_AND_SYNC);
					
					BlockPos below = posDir.down();
					IBlockState stateBelow = worldIn.getBlockState(below);
					if( (stateBelow.getBlock() instanceof BlockGrapeVine1) ) {
						BlockGrapeVine1 blockBelow = (BlockGrapeVine1)stateBelow.getBlock();
						if( stateBelow.getValue(BlockGrapeVineBase.AGE) < blockBelow.getMaxAge() ) {
							worldIn.setBlockState(below, stateBelow.withProperty(BlockGrapeVineBase.AGE, blockBelow.getMaxAge()), BlockFlags.UPDATE_AND_SYNC);
						}
					}
				}
			}
		}
	}
	
    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		BlockPos posDown = pos.down();
		if( worldIn.isAirBlock(posDown) )
			return true;
    	
    	for( int i = 0; i < 4; i ++ ) {
			final EnumFacing dir = BlockCheck.DIR4[i];
			BlockPos posDir = pos.add(dir.getFrontOffsetX(), 0, dir.getFrontOffsetZ());

			if (canGrowHere(worldIn, posDir)) {
				return true;
			}
    	}
    	
    	return false;
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState other = blockAccess.getBlockState(pos.offset(side));
        Block block = other.getBlock();

/*        if (this == Blocks.GLASS || this == Blocks.STAINED_GLASS)
        {
            if (blockState != iblockstate)
            {
                return true;
            }

            if (block == this)
            {
                return false;
            }
        }

        return !this.ignoreSimilarity && block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);*/
        return block != this || blockState.getValue(SUBTYPE) != other.getValue(SUBTYPE);
    }
    
	/************
	 * TICK
	 ************/
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if( worldIn.isRemote )
			return;
		if (!this.canBlockStay(worldIn, pos, state))
		{
			worldIn.setBlockState(pos, GrowthcraftCoreBlocks.rope_fence.getDefaultState());
		}
		else
		{
			grow(worldIn, rand, pos, state);
		}
	}

	/************
	 * CONDITIONS
	 ************/
	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		if (this.isSupportedByTrunk(world, pos))
		{
			return true;
		}
		else
		{
			for (EnumFacing dir : BlockCheck.DIR4)
			{
				for (int i = 1; i <= grapeVineSupportedLength; ++i)
				{
					BlockPos posOfs = pos.add(dir.getFrontOffsetX()*i, 0, dir.getFrontOffsetZ()*i);
//					final int bx = x + dir.offsetX * i;
//					final int bz = z + dir.offsetZ * i;
					if (world.getBlockState(posOfs).getBlock() != this)
					{
						break;
					}
					else if (isSupportedByTrunk(world, posOfs))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	/************
	 * STUFF
	 ************/
	
	@Override
	public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return false;
	}

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }
    
    @Override
    public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        // return block instanceof BlockRopeFence || block instanceof BlockRopeKnot || block instanceof BlockGrapeVineBush || block instanceof BlockHopsBush;
        return block instanceof IBlockRope;
    }
    
    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        return block instanceof BlockRopeFence || block instanceof BlockRopeKnot;
    }
    
	/************
	 * DROPS
	 ************/
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return GrowthcraftCoreItems.rope.getItem();
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}
	
	/************
	 * RENDERS
	 ************/
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return Blocks.LEAVES.isOpaqueCube(state);
	}
	
	/************
	 * STATES
	 ************/
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, SUBTYPE, NORTH, EAST, SOUTH, WEST, UP, DOWN);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState().withProperty(SUBTYPE, meta & 0x7);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta |= state.getValue(SUBTYPE) & 0x7;
	    return meta;
	}

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(NORTH, canConnectRopeTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, canConnectRopeTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, canConnectRopeTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canConnectRopeTo(worldIn, pos, EnumFacing.WEST))
                .withProperty(UP, canConnectRopeTo(worldIn, pos, EnumFacing.UP))
                .withProperty(DOWN, canConnectRopeTo(worldIn, pos, EnumFacing.DOWN));
    }
}
