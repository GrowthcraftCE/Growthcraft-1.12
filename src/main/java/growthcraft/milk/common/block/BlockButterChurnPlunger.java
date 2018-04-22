package growthcraft.milk.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.GrowthcraftBlockContainer;
import growthcraft.core.shared.block.IRotatableBlock;
import growthcraft.milk.common.tileentity.TileEntityButterChurn;
import growthcraft.milk.common.tileentity.TileEntityButterChurnPlunger;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.init.GrowthcraftMilkBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockButterChurnPlunger extends GrowthcraftBlockContainer {
    private static final AxisAlignedBB BOUNDING_BOX_UP = new AxisAlignedBB(
            0.0625 * 7, -0.0625 * 2, 0.0625 * 7,
            0.0625 * 9, 0.0625 * 11, 0.0625 * 9);
    private static final AxisAlignedBB BOUNDING_BOX_DOWN = new AxisAlignedBB(
            0.0625 * 7, -0.0625 * 2, 0.0625 * 7,
            0.0625 * 9, 0.0625 * 7, 0.0625 * 9);

    public final static PropertyBool SUBMODEL_PLUNGER = PropertyBool.create("isplunger");
	
	public BlockButterChurnPlunger(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setResistance(5.0F);
		this.setHardness(2.0F);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SUBMODEL_PLUNGER, false));
		setTileEntityType(TileEntityButterChurnPlunger.class);
	}

	private TileEntityButterChurn getMasterTileEntity(IBlockAccess world, BlockPos pos) {
		BlockPos posBelow = pos.down();
		IBlockState state = world.getBlockState(posBelow);
		if( state.getBlock() != GrowthcraftMilkBlocks.churn.getBlock() )
			return null;
		TileEntityButterChurn te = GrowthcraftMilkBlocks.churn.getBlock().getTileEntity(world, pos.down());
		return te;
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    	TileEntityButterChurn te = getMasterTileEntity(source, pos);
    	if( te != null ) {
    		if( te.getShaftState() == 0 )
    			return BOUNDING_BOX_DOWN;
    		else
    			return BOUNDING_BOX_UP;
    	}
    	return BOUNDING_BOX_UP;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX_UP);
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
	 * TRIGGERS
	 ************/

	@Override
	public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		final Block below = world.getBlockState(pos.down()).getBlock();
		if (below instanceof IRotatableBlock)
		{
			return ((IRotatableBlock)below).isRotatable(world, pos.down(), side);
		}
		return false;
	}
    
	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing side)
	{
		if (isRotatable(world, pos, side))
		{
			final Block below = world.getBlockState(pos.down()).getBlock();
			return below.rotateBlock(world, pos.down(), side);
		}
		return false;
	}

	@Override
	public boolean wrenchBlock(World world, BlockPos pos, EntityPlayer player, ItemStack wrench)
	{
		final Block below = world.getBlockState(pos.down()).getBlock();
		if (below instanceof BlockButterChurn)
		{
			return ((BlockButterChurn)below).wrenchBlock(world, pos.down(), player, wrench);
		}
		return false;
	}
    
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;
		BlockPos posBelow = pos.down();
		final Block below = worldIn.getBlockState(posBelow).getBlock();
		if (below instanceof BlockButterChurn)
		{
			BlockButterChurn churn = (BlockButterChurn)below;
			if( !playerIn.isSneaking() && churn.tryChurning(worldIn, posBelow, playerIn) )
				return true;
			return churn.tryWrenchItem(playerIn, worldIn, pos.down());
		}
		return false;
	}

	private void updateOrient(World world, BlockPos pos, IBlockState state) {
		EnumFacing facing = world.getBlockState(pos.down()).getValue(TYPE_ROTATION);
		world.setBlockState(pos, state.withProperty(TYPE_ROTATION, facing), BlockFlags.UPDATE_AND_SYNC);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);
		updateOrient(world, pos, state);

/*		if (!world.isRemote)
		{
			this.updatePressState(world, pos);
		} */
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		updateOrient(worldIn, pos, state);
/*		if (!worldIn.isRemote)
		{
			this.updatePressState(worldIn, pos);
		} */
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canBlockStay(worldIn, pos))
		{
			worldIn.destroyBlock(pos, false);
		}

/*		if (!worldIn.isRemote)
		{
			this.updatePressState(worldIn, pos);
		} */
	}
	
	/************
	 * CONDITIONS
	 ************/
	
	public boolean canBlockStay(World world, BlockPos pos)
	{
		return GrowthcraftMilkBlocks.churn.getBlock() == world.getBlockState(pos.down()).getBlock();
	}
	
	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
/*		EnumFacing facing = state.getValue(TYPE_ROTATION);

		if (orient == Orient.TOEAST)
		{
			return side == EnumFacing.EAST || side == EnumFacing.WEST;
		}
		else if (orient == Orient.TOSOUTH)
		{
			return side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
		} */

		return isNormalCube(state, world, pos);
	}

	/************
	 * DROPS
	 ************/
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}
	
	/************
	 * RENDERS
	 ************/

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	/************
	 * STATES
	 ************/
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, TYPE_ROTATION, SUBMODEL_PLUNGER);
	}
	
/*	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(SUBMODEL_PLUNGER, false);
	} */
	
}
