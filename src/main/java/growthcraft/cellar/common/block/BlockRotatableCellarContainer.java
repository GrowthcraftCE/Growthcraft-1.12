package growthcraft.cellar.common.block;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.BlockUtils;
import growthcraft.core.shared.block.GrowthcraftBlockContainer;
import growthcraft.core.shared.block.GrowthcraftRotatableBlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRotatableCellarContainer extends GrowthcraftRotatableBlockContainer {

	public BlockRotatableCellarContainer(Material material) {
		super(material);
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
	
	protected EnumFacing setOrientWhenPlacing(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, boolean allDirections) {
		EnumFacing facing;
		if( allDirections ) {
			facing = EnumFacing.getDirectionFromEntityLiving(pos, placer);	
		}
		else {
			facing = EnumFacing.fromAngle(placer.rotationYaw);
		}
		worldIn.setBlockState(pos, state.withProperty(TYPE_ROTATION, facing), BlockFlags.UPDATE_AND_SYNC);
		return facing;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(worldIn, pos, state);
		setDefaultDirection(worldIn, pos, state);
	}
	
	// GUI
	
	protected boolean openGui(EntityPlayer player, World world, BlockPos pos)
	{
		return GuiBindingBlockCellar.openGui(this, player, world, pos);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return GuiBindingBlockCellar.onBlockActivated(this, worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
}
