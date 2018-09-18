package growthcraft.cellar.common.block;

import java.util.Random;

import javax.annotation.Nonnull;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFermentBarrel extends BlockRotatableCellarContainer {
	
	private static final PropertyBool TYPE_TAP = PropertyBool.create("hastap");

	public BlockFermentBarrel(String unlocalizedName) {
		super(Material.WOOD);
		setTileEntityType(TileEntityFermentBarrel.class);
		setHardness(2.5F);
		setSoundType(SoundType.WOOD);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE_TAP, false).withProperty(TYPE_ROTATION, EnumFacing.NORTH));
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	@Override
	protected boolean shouldRestoreBlockState(World world, BlockPos pos, ItemStack stack)
	{
		return true;
	}

	@Override
	protected boolean shouldDropTileStack(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return true;
	}

//	@Override
//	protected boolean playerDrainTank(World world, BlockPos pos, ILegacyFluidHandler tank, ItemStack held, EntityPlayer player)
//	{
//		final FluidStack available = GrowthcraftFluidUtils.playerDrainTank(world, pos, tank, held, player);
//		if (available != null && available.amount > 0)
//		{
//			GrowthcraftCellar.CELLAR_BUS.post(new EventTankDrained(player, world, pos, available));
//			return true;
//		}
//		return false;
//	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		setOrientWhenPlacing(worldIn, pos, state, placer, true);
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
	    return 0;
	}
	
	/************
	 * STATES
	 ************/
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, TYPE_TAP, TYPE_ROTATION);
	}

	@SuppressWarnings("deprecation")
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		final TileEntityFermentBarrel te = getTileEntity(worldIn, pos);
		if (te != null)
		{
			if( te.hasTap() )
				return state.withProperty(TYPE_TAP, true);
			else
				return state.withProperty(TYPE_TAP, false);
		}
		
		return state;
    }
	
	/************
	 * RENDERS
	 ************/

	@SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

	/************
	 * COMPARATOR
	 ************/
	@SuppressWarnings("deprecation")
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		final TileEntityFermentBarrel te = getTileEntity(world, pos);
		if (te != null)
		{
			return te.getDeviceProgressScaled(15);
		}
		return 0;
	}
}
