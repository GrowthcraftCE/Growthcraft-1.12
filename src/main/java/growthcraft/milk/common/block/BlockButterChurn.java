package growthcraft.milk.common.block;

import java.util.List;

import javax.annotation.Nullable;

import growthcraft.core.common.block.GrowthcraftBlockContainer;
import growthcraft.milk.Reference;
import growthcraft.milk.common.tileentity.TileEntityButterChurn;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockButterChurn extends GrowthcraftBlockContainer {
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 4, 0.0625 * 0, 0.0625 * 4,
            0.0625 * 12, 0.0625 * 16, 0.0625 * 12);
	
	public BlockButterChurn(String unlocalizedName) {
        super(Material.CLAY);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setResistance(5.0F);
		this.setHardness(2.0F);
//		setStepSound(soundTypeWood);
		setTileEntityType(TileEntityButterChurn.class);
	}
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
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
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
	private boolean tryChurning(World world, BlockPos pos, EntityPlayer player)
	{
		final TileEntityButterChurn butterChurn = getTileEntity(world, pos);
		if (butterChurn != null)
		{
			switch (butterChurn.doWork())
			{
				case CHURN:
				case PRODUCE:
					return true;
				case NONE:
				default:
					break;
			}
		}
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if( super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ) )
			return true;
		if (!playerIn.isSneaking())
		{
			if (tryChurning(worldIn, pos, playerIn))
				return true;
		}
		return false;
	}

}
