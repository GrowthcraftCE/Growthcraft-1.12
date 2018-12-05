package growthcraft.milk.common.block;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.milk.common.tileentity.TileEntityCheesePress;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.config.GrowthcraftMilkConfig;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCheesePress extends BlockOrientable {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 16);
    
    public final static PropertyEnum<AnimationStage> STAGE_PRESS = PropertyEnum.create("animstage", AnimationStage.class);
    public final static PropertyBool SUBMODEL_CAP = PropertyBool.create("iscap");

	public BlockCheesePress(String unlocalizedName) {
		super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setResistance(5.0F);
		this.setHardness(2.0F);
		this.setSoundType(SoundType.WOOD);
		this.setHarvestLevel("axe", 1);
		setTileEntityType(TileEntityCheesePress.class);
	}

	@SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return BOUNDING_BOX;
    }

	@SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, getBoundingBox(state, worldIn, pos));
    }

	@SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

	@SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

	@SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = playerIn.getHeldItem(hand);
		if (super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ))
			return true;
		if (GrowthcraftMilkConfig.cheesePressHandOperated && held.isEmpty() )
		{
			if (!playerIn.isSneaking())
			{
				final TileEntityCheesePress cheesePress = getTileEntity(worldIn, pos);
				if (cheesePress != null)
				{
					if (cheesePress.toggle())
					{
//							world.playSoundEffect((double)x, (double)y, (double)z, "random.wood_click", 0.3f, 0.5f);
						worldIn.playSound((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3f, 0.5f, false);
					}
					return true;
				}
			}
		}
		return false;
	}

	private void updatePressState(World world, BlockPos pos)
	{
		final boolean isPowered = world.isBlockPowered(pos);
		final TileEntityCheesePress cheesePress = getTileEntity(world, pos);
		if (cheesePress != null)
		{
			if (cheesePress.toggle(isPowered))
			{
//				world.playSoundEffect((double)x, (double)y, (double)z, "random.wood_click", 0.3f, 0.5f);
				world.playSound((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3f, 0.5f, false);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		if( !worldIn.isRemote ) {
			if (GrowthcraftMilkConfig.cheesePressRedstoneOperated)
			{
				this.updatePressState(worldIn, pos);
			}
		}
	}

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
		final TileEntityCheesePress te = getTileEntity(world, pos);
		if (te != null)
		{
			return Container.calcRedstoneFromInventory(te);
		}
		return 0;
	}
	
	/************
	 * PROPERTIES
	 ************/
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, TYPE_ORIENT, /*STAGE_PRESS,*/ SUBMODEL_CAP);
	}

	@SuppressWarnings("deprecation")
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		final TileEntityCheesePress cheesePress = getTileEntity(worldIn, pos);
		state = state.withProperty(SUBMODEL_CAP, false);
/*		if (cheesePress != null) {
			if( cheesePress.isAnimating() )
				return state.withProperty(STAGE_PRESS, AnimationStage.PRESSING);
			if( cheesePress.isPressed() )
				return state.withProperty(STAGE_PRESS, AnimationStage.PRESSED);
			else
				return state.withProperty(STAGE_PRESS, AnimationStage.UNPRESSED);
		} */
		return state;
	}

	public static enum AnimationStage implements IStringSerializable {
		UNPRESSED,
		PRESSING,
		PRESSED;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
	
}
