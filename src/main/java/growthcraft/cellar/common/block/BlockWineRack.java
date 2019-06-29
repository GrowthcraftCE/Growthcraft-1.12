package growthcraft.cellar.common.block;

import growthcraft.cellar.common.tileentity.TileEntityWineRack;
import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.BlockUtils;
import growthcraft.core.shared.block.GrowthcraftRotatableBlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWineRack extends GrowthcraftRotatableBlockContainer
{
	
	private int flammability;
	private int fireSpreadSpeed;
	
	public BlockWineRack(Material material)
	{
		super(Material.WOOD);
		setTileEntityType(TileEntityWineRack.class);
		setHardness(2.5F);
		setSoundType(SoundType.WOOD);
        this.setTickRandomly(true);
	}
	
	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}
	
	protected void setDefaultDirection(World world, BlockPos pos, IBlockState state)
	{
		if (!world.isRemote)
		{
			EnumFacing facing = BlockUtils.getDefaultDirection(world, pos, state);
			world.setBlockState(pos, state.withProperty(TYPE_ROTATION, facing), BlockFlags.UPDATE_AND_SYNC);
		}
	}
	
	protected EnumFacing setOrientWhenPlacing(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer) {
		EnumFacing facing = EnumFacing.fromAngle(placer.rotationYaw);
		worldIn.setBlockState(pos, state.withProperty(TYPE_ROTATION, facing), BlockFlags.UPDATE_AND_SYNC);
		return facing;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(worldIn, pos, state);
		setDefaultDirection(worldIn, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		setOrientWhenPlacing(worldIn, pos, state, placer);
	}
	
	public BlockWineRack setFlammability(int flam)
	{
		this.flammability = flam;
		return this;
	}
	
	public BlockWineRack setFireSpread(int speed)
	{
		this.fireSpreadSpeed = speed;
		return this;
	}
	
	@Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
    	return flammability;
    }

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return fireSpreadSpeed;
	}
}




















