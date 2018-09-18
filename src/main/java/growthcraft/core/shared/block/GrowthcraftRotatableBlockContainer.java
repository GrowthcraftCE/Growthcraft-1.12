package growthcraft.core.shared.block;

import javax.annotation.Nonnull;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GrowthcraftRotatableBlockContainer extends GrowthcraftBlockContainer implements IRotatableBlock {

	public final static PropertyEnum<EnumFacing> TYPE_ROTATION = PropertyEnum.create("rotation", EnumFacing.class);
	
	GrowthcraftRotatableBlockContainer(Material material, MapColor mapColor) {
		super(material, mapColor);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE_ROTATION, EnumFacing.NORTH));
	}

	public GrowthcraftRotatableBlockContainer(Material material) {
		super(material);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE_ROTATION, EnumFacing.NORTH));
	}
	
	/* IRotatableBlock */
	@Override
	public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return false;
	}
	
	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing side)
	{
		if (isRotatable(world, pos, side))
		{
			IBlockState state = world.getBlockState(pos);
			doRotateBlock(world, pos, state, side);
			markBlockForUpdate(world, pos);
			return true;
		}
		return false;
	}

	protected void placeBlockByEntityDirection(World world, BlockPos pos, EntityLivingBase entity, ItemStack stack)
	{
		if (isRotatable(world, pos, null))
		{
			IBlockState state = world.getBlockState(pos);
			IBlockState newState = state.withProperty(TYPE_ROTATION, EnumFacing.fromAngle(entity.rotationYaw) );
			world.setBlockState(pos, newState, BlockFlags.SYNC);
		}
	}
	
	public void doRotateBlock(World world, BlockPos pos, IBlockState state, EnumFacing side)
	{
		final EnumFacing current = state.getValue(TYPE_ROTATION);
		EnumFacing newDirection = current;
		if (current == side)
		{
			switch (current)
			{
				case UP:
					newDirection = EnumFacing.NORTH;
					break;
				case DOWN:
					newDirection = EnumFacing.SOUTH;
					break;
				case NORTH:
				case EAST:
					newDirection = EnumFacing.UP;
					break;
				case SOUTH:
				case WEST:
					newDirection = EnumFacing.DOWN;
					break;
				default:
					// some invalid state
					break;
			}
		}
		else
		{
			switch (current)
			{
				case UP:
					newDirection = EnumFacing.DOWN;
					break;
				case DOWN:
					newDirection = EnumFacing.UP;
					break;
				case WEST:
					newDirection = EnumFacing.SOUTH;
					break;
				case EAST:
					newDirection = EnumFacing.NORTH;
					break;
				case NORTH:
					newDirection = EnumFacing.WEST;
					break;
				case SOUTH:
					newDirection = EnumFacing.EAST;
					break;
				default:
					// yet another invalid state
					break;
			}
		}
		if (newDirection != current)
		{
			IBlockState newState = state.withProperty(TYPE_ROTATION, newDirection);
			world.setBlockState(pos, newState, BlockFlags.SYNC);
//			world.setBlockMetadataWithNotify(pos, newDirection.ordinal(), BlockFlags.UPDATE_AND_SYNC);
		}
	}
	
	///////////
	// STATES
	///////////
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, TYPE_ROTATION);
	}

	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState().withProperty(TYPE_ROTATION, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    return state.getValue(TYPE_ROTATION).getIndex();
	}
}
