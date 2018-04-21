package growthcraft.cellar.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.Reference;
import growthcraft.cellar.common.block.BlockFruitPresser.Orient;
import growthcraft.cellar.common.init.GrowthcraftCellarBlocks;
import growthcraft.cellar.common.tileentity.TileEntityFruitPresser;
import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.IRotatableBlock;
import growthcraft.core.shared.block.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFruitPresser extends BlockCellarContainer implements IWrenchable, IRotatableBlock {
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.9375F, 0.8125F);
	
	public final static PropertyEnum<Orient> TYPE_ORIENT = PropertyEnum.create("orient", Orient.class);
	public final static PropertyEnum<PressState> TYPE_PRESSED = PropertyEnum.create("pressed", PressState.class);
	
	public BlockFruitPresser(String unlocalizedName) {
		super(Material.PISTON);
		this.isBlockContainer = true;
		setTileEntityType(TileEntityFruitPresser.class);
		setHardness(2.0F);
		this.setResistance(5.0F);
		setSoundType(SoundType.STONE);	// Formerly soundTypePiston
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHarvestLevel("axe", 0);
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    	return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
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

	/* IRotatableBLock */
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

	/* IWrenchable */
	@Override
	public boolean wrenchBlock(World world, BlockPos pos, EntityPlayer player, ItemStack wrench)
	{
		final Block below = world.getBlockState(pos.down()).getBlock();
		if (below instanceof BlockFruitPress)
		{
			return ((BlockFruitPress)below).wrenchBlock(world, pos.down(), player, wrench);
		}
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;
		final Block below = worldIn.getBlockState(pos.down()).getBlock();
		if (below instanceof BlockFruitPress)
		{
			return ((BlockFruitPress)below).tryWrenchItem(playerIn, worldIn, pos.down());
		}
		return false;
	}

	private void updateOrientWithPress(World world, BlockPos pos, IBlockState state) {
		EnumFacing facing = world.getBlockState(pos.down()).getValue(TYPE_ROTATION);
		Orient orient = Orient.fromFacing(facing);
		world.setBlockState(pos, state.withProperty(TYPE_ORIENT, orient), BlockFlags.UPDATE_AND_SYNC);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);
		updateOrientWithPress(world, pos, state);

		if (!world.isRemote)
		{
			this.updatePressState(world, pos);
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		updateOrientWithPress(worldIn, pos, state);
		if (!worldIn.isRemote)
		{
			this.updatePressState(worldIn, pos);
		}
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canBlockStay(worldIn, pos))
		{
			worldIn.destroyBlock(pos, false);
		}

		if (!worldIn.isRemote)
		{
			this.updatePressState(worldIn, pos);
		}
	}
	
	private void updatePressState(World world, BlockPos pos)
	{
		final IBlockState state = world.getBlockState(pos);
		if( state.getBlock() != GrowthcraftCellarBlocks.fruitPresser.getBlock() )
			return;
		final boolean flag = world.isBlockPowered(pos);
		final PressState pressed = state.getValue(TYPE_PRESSED);

		if (flag && pressed == PressState.UNPRESSED)
		{
			world.setBlockState(pos, state.withProperty(TYPE_PRESSED, PressState.PRESSED), BlockFlags.UPDATE_AND_SYNC);
			world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.25F + 0.6F, false);
		}
		else if (!flag && pressed == PressState.PRESSED)
		{
			world.setBlockState(pos, state.withProperty(TYPE_PRESSED, PressState.UNPRESSED), BlockFlags.UPDATE_AND_SYNC);
			world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.15F + 0.6F, false);
		}

		markBlockForUpdate(world, pos);
	}
	
	/************
	 * CONDITIONS
	 ************/
	
	public boolean canBlockStay(World world, BlockPos pos)
	{
		return GrowthcraftCellarBlocks.fruitPress.getBlock() == world.getBlockState(pos.down()).getBlock();
	}
	
	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		Orient orient = state.getValue(TYPE_ORIENT);

		if (orient == Orient.TOEAST)
		{
			return side == EnumFacing.EAST || side == EnumFacing.WEST;
		}
		else if (orient == Orient.TOSOUTH)
		{
			return side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
		}

		return isNormalCube(state, world, pos);
	}
	
	/************
	 * STUFF
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return GrowthcraftCellarBlocks.fruitPress.getItemAsStack(1, 0);
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
	 * PROPERTIES
	 ************/	
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, TYPE_ORIENT, TYPE_PRESSED);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState()
	    		   .withProperty(TYPE_ORIENT, Orient.values()[meta & 0x1])
	    		   .withProperty(TYPE_PRESSED, PressState.values()[(meta >> 1) & 0x1]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    return state.getValue(TYPE_ORIENT).ordinal() |
	    	   (state.getValue(TYPE_PRESSED).ordinal() << 1);
	}
	
	public static enum Orient implements IStringSerializable {
		TOEAST,
		TOSOUTH;
		
		public static Orient fromFacing(EnumFacing facing) {
			if( facing == EnumFacing.EAST || facing == EnumFacing.WEST )
				return TOEAST;
			else
				return TOSOUTH;
		}
		
		public EnumFacing toFacing() {
			return this == TOEAST ? EnumFacing.EAST : EnumFacing.SOUTH;
		}

		@Override
		public String getName() {
			return toString().toLowerCase();
		}
	}
	
	public static enum PressState implements IStringSerializable {
		UNPRESSED,
		PRESSED;

		@Override
		public String getName() {
			return toString().toLowerCase();
		}
	}
}
