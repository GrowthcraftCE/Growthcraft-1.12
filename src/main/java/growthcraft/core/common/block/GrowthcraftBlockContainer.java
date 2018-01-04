package growthcraft.core.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.GrowthcraftCore;
import growthcraft.core.Utils;
import growthcraft.core.api.nbt.INBTItemSerializable;
import growthcraft.core.api.utils.BlockFlags;
import growthcraft.core.common.inventory.InventoryProcessor;
import growthcraft.core.common.item.IItemTileBlock;
import growthcraft.core.common.tileentity.feature.ICustomDisplayName;
import growthcraft.core.common.tileentity.feature.IAltItemHandler;
import growthcraft.core.lib.legacy.ILegacyFluidHandler;
import growthcraft.core.utils.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;

public abstract class GrowthcraftBlockContainer extends GrowthcraftBlockBase implements IDroppableBlock, IRotatableBlock, IWrenchable, ITileEntityProvider
{
	public final static PropertyEnum<EnumFacing> TYPE_ROTATION = PropertyEnum.create("rotation", EnumFacing.class);
	
	protected Random rand = new Random();
	protected Class<? extends TileEntity> tileEntityType;

	public GrowthcraftBlockContainer(@Nonnull Material material)
	{
		super(material);
		this.isBlockContainer = true;
	}
	

	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
	{
		super.eventReceived(state, worldIn, pos, id, param);
		final TileEntity te = getTileEntity(worldIn, pos);
		return te != null ? te.receiveClientEvent(id, param) : false;
	}

	  @Nonnull
	  @Override
	  protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, TYPE_ROTATION);
	  }

	  @Nonnull
	  @Override
	  public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState().withProperty(TYPE_ROTATION, EnumFacing.getFront(meta));
	  }

	  @Override
	  public int getMetaFromState(IBlockState state) {
	    return state.getValue(TYPE_ROTATION).getIndex();
	  }

	  @Override
	  public int damageDropped(IBlockState state) {
	    return getMetaFromState(state);
	  }

	
	protected void setTileEntityType(Class<? extends TileEntity> klass)
	{
		this.tileEntityType = klass;
	}

	/* IRotatableBlock */
	@Override
	public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return false;
	}

	public void doRotateBlock(World world, BlockPos pos, EnumFacing side)
	{
		IBlockState state = world.getBlockState(pos);
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

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing side)
	{
		if (isRotatable(world, pos, side))
		{
			doRotateBlock(world, pos, side);
			markBlockForUpdate(world, pos);
			return true;
		}
		return false;
	}

	protected void fellBlockFromWrench(World world, BlockPos pos)
	{
//		final int metadata = world.getBlockMetadata(pos);
		final IBlockState state = world.getBlockState(pos);
		final List<ItemStack> drops = new ArrayList<ItemStack>();
		if (shouldDropTileStack(world, pos, state, 0))
		{
			GrowthcraftCore.logger.info("Dropping Tile As ItemStack");
			getTileItemStackDrops(drops, world, pos, state, 0);
			for (ItemStack stack : drops)
			{
				ItemUtils.spawnItemStack(world, pos, stack, world.rand);
			}
			final TileEntity te = getTileEntity(world, pos);
			if (te instanceof IInventory)
			{
				GrowthcraftCore.logger.info("Clearing Inventory");
				InventoryProcessor.instance().clearSlots((IInventory)te);
			}
			GrowthcraftCore.logger.info("Setting Block To Air");
			world.setBlockToAir(pos);
		}
		else
		{
			fellBlockAsItem(world, pos);
		}
	}

	@Override
	public boolean wrenchBlock(World world, BlockPos pos, EntityPlayer player, ItemStack wrench)
	{
		if (player == null) return false;
		if (!ItemUtils.canWrench(wrench, player, pos)) return false;
		if (!player.isSneaking()) return false;
		if (!world.isRemote)
		{
			fellBlockFromWrench(world, pos);
			ItemUtils.wrenchUsed(wrench, player, pos);
		}
		return true;
	}

	public boolean tryWrenchItem(EntityPlayer player, World world, BlockPos pos)
	{
		if (player == null) return false;
		final ItemStack is = player.inventory.getCurrentItem();
		return wrenchBlock(world, pos, player, is);
	}

	protected void placeBlockByEntityDirection(World world, BlockPos pos, EntityLivingBase entity, ItemStack stack)
	{
		if (isRotatable(world, pos, null))
		{
/*			final int l = MathHelper.floor((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

			int meta = 2;

			if (l == 0) meta = 1;
			else if (l == 1) meta = 2;
			else if (l == 2) meta = 0;
			else if (l == 3) meta = 3;
			world.setBlockMetadataWithNotify(pos, meta, BlockFlags.SYNC);*/
			IBlockState state = world.getBlockState(pos);
			IBlockState newState = state.withProperty(TYPE_ROTATION, EnumFacing.fromAngle(entity.rotationYaw) );
			world.setBlockState(pos, newState, BlockFlags.SYNC);
		}
	}

	protected void setupCustomDisplayName(World world, BlockPos pos, ItemStack stack)
	{
		if (stack.hasDisplayName())
		{
			final TileEntity te = getTileEntity(world, pos);
			if (te instanceof ICustomDisplayName)
			{
				((ICustomDisplayName)te).setGuiDisplayName(stack.getDisplayName());
			}
		}
	}

	protected NBTTagCompound getTileTagCompound(World world, BlockPos pos, ItemStack stack)
	{
		final Item item = stack.getItem();
		if (item instanceof IItemTileBlock)
		{
			final IItemTileBlock itb = (IItemTileBlock)item;
			return itb.getTileTagCompound(stack);
		}
		else
		{
			GrowthcraftCore.logger.error("Cannot get tile tag compound for a non IItemTileBlock: stack=%s block=%s", stack, this);
		}
		return null;
	}

	protected void setTileTagCompound(World world, BlockPos pos, ItemStack stack, NBTTagCompound tag)
	{
		final Item item = stack.getItem();
		if (item instanceof IItemTileBlock)
		{
			final IItemTileBlock itb = (IItemTileBlock)item;
			itb.setTileTagCompound(stack, tag);
		}
		else
		{
			GrowthcraftCore.logger.error("Cannot set tile tag compound for a non IItemTileBlock: stack=%s block=%s", stack, this);
		}
	}

	protected boolean shouldRestoreBlockState(World world, BlockPos pos, ItemStack stack)
	{
		return false;
	}

	protected void restoreBlockStateFromStack(World world, BlockPos pos, ItemStack stack)
	{
		if (shouldRestoreBlockState(world, pos, stack))
		{
			final TileEntity te = getTileEntity(world, pos);
			if (te instanceof INBTItemSerializable)
			{
				final NBTTagCompound tag = getTileTagCompound(world, pos, stack);
				if (tag != null)
				{
					((INBTItemSerializable)te).readFromNBTForItem(tag);
				}
			}
			else
			{
				GrowthcraftCore.logger.error("Cannot restore tile from stack, the TileEntity does not support INBTItemSerializable: stack=%s block=%s tile=%s", stack, this, te);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		restoreBlockStateFromStack(worldIn, pos, stack);
		setupCustomDisplayName(worldIn, pos, stack);
	}

	protected void scatterInventory(World world, BlockPos pos, Block block)
	{
		final TileEntity te = getTileEntity(world, pos);
		if (te instanceof IInventory)
		{
			final IInventory inventory = (IInventory)te;
			if (inventory != null)
			{
				for (int index = 0; index < inventory.getSizeInventory(); ++index)
				{
					final ItemStack stack = inventory.getStackInSlot(index);
					if (stack != null)
					{
						ItemUtils.spawnItemStack(world, pos, stack, rand);
					}
					inventory.setInventorySlotContents(index, (ItemStack)null);
				}
				world.updateComparatorOutputLevel(pos, block);
			}
		}
	}

	protected boolean shouldScatterInventoryOnBreak(World world, BlockPos pos)
	{
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (shouldScatterInventoryOnBreak(worldIn, pos))
			scatterInventory(worldIn, pos, state.getBlock());
		worldIn.removeTileEntity(pos);
	}

	protected ItemStack createHarvestedBlockItemStack(World world, EntityPlayer player, BlockPos pos, IBlockState state)
	{
		return getSilkTouchDrop(state);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		// TODO: Method is similar to the overridden one. Maybe remove?
		
		player.addStat(StatList.getBlockStats(this), 1);
		player.addExhaustion(0.025F);

		if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
		{
			final ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			final ItemStack itemstack = createHarvestedBlockItemStack(worldIn, player, pos, state);

			if (itemstack != null)
			{
				items.add(itemstack);
			}

			ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
			for (ItemStack is : items)
			{
				spawnAsEntity(worldIn, pos, is);
			}
		}
		else
		{
			harvesters.set(player);
			final int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			dropBlockAsItem(worldIn, pos, state, fortune);
			harvesters.set(null);
		}
	}

	protected boolean shouldDropTileStack(World world, BlockPos pos, IBlockState state, int fortune)
	{
		return false;
	}

	private void getDefaultDrops(List<ItemStack> ret, World world, BlockPos pos, IBlockState state, int fortune)
	{
		final int count = quantityDropped(state, fortune, world.rand);
		for (int i = 0; i < count; ++i)
		{
			final Item item = getItemDropped(state, world.rand, fortune);
			if (item != null)
			{
				ret.add(new ItemStack(item, 1, damageDropped(state)));
			}
		}
	}

	protected void getTileItemStackDrops(List<ItemStack> ret, World world, BlockPos pos, IBlockState state, int fortune)
	{
		final TileEntity te = getTileEntity(world, pos);
		if (te instanceof INBTItemSerializable)
		{
			final NBTTagCompound tag = new NBTTagCompound();
			((INBTItemSerializable)te).writeToNBTForItem(tag);
			final ItemStack stack = new ItemStack(this, 1, getMetaFromState(state));
			setTileTagCompound(world, pos, stack, tag);
			ret.add(stack);
		}
		else
		{
			getDefaultDrops(ret, world, pos, state, fortune);
		}
	}

	public ArrayList<ItemStack> getDrops(World world, BlockPos pos, IBlockState state, int fortune)
	{
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		if (shouldDropTileStack(world, pos, state, fortune))
		{
			getTileItemStackDrops(ret, world, pos, state, fortune);
		}
		else
		{
			getDefaultDrops(ret, world, pos, state, fortune);
		}
		return ret;
	}

	protected boolean playerFillTank(World world, BlockPos pos, ILegacyFluidHandler fh, ItemStack is, EntityPlayer player)
	{
		return Utils.playerFillTank(world, pos, fh, is, player);
	}

	protected boolean playerDrainTank(World world, BlockPos pos, ILegacyFluidHandler fh, ItemStack is, EntityPlayer player)
	{
		final FluidStack fs = Utils.playerDrainTank(world, pos, fh, is, player);
		return fs != null && fs.amount > 0;
	}

	private boolean handleIFluidHandler(World world, BlockPos pos, EntityPlayer player, IBlockState state)
	{
		final TileEntity te = world.getTileEntity(pos);
		if (te instanceof ILegacyFluidHandler)
		{
			if (world.isRemote)
			{
				return true;
			}
			else
			{
				final ILegacyFluidHandler fh = (ILegacyFluidHandler)te;
				final ItemStack is = player.inventory.getCurrentItem();

				boolean needUpdate = false;

				if (!player.isSneaking())
				{
					// While not sneaking, draining is given priority
					if (playerDrainTank(world, pos, fh, is, player) ||
						playerFillTank(world, pos, fh, is, player)) needUpdate = true;
				}
				else
				{
					// Otherwise filling is given priority
					if (playerFillTank(world, pos, fh, is, player) ||
						playerDrainTank(world, pos, fh, is, player)) needUpdate = true;
				}
				if (needUpdate)
				{
					markBlockForUpdate(world, pos);
					return true;
				}
			}
		}
		return false;
	}

	protected boolean handleOnUseItem(IAltItemHandler.Action action, World world, BlockPos pos, EntityPlayer player)
	{
		final TileEntity te = world.getTileEntity(pos);
		if (te instanceof IAltItemHandler)
		{
			if (world.isRemote)
			{
				return true;
			}
			else
			{
				final IAltItemHandler ih = (IAltItemHandler)te;
				final ItemStack is = player.inventory.getCurrentItem();

				boolean needUpdate = false;
				if (ih.tryPlaceItem(action, player, is))
				{
					needUpdate = true;
				}
				else if (ih.tryTakeItem(action, player, is))
				{
					needUpdate = true;
				}

				if (needUpdate)
				{
					markBlockForUpdate(world, pos);
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			final TileEntity te = world.getTileEntity(pos);
			if (te instanceof IAltItemHandler)
			{
				if (handleOnUseItem(IAltItemHandler.Action.LEFT, world, pos, player))
				{
					return;
				}
			}
		}
		super.onBlockClicked(world, pos, player);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (tryWrenchItem(playerIn, worldIn, pos)) return true;
		if (handleIFluidHandler(worldIn, pos, playerIn, state)) return true;
		if (handleOnUseItem(IAltItemHandler.Action.RIGHT, worldIn, pos, playerIn)) return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T extends TileEntity> T getTileEntity(IBlockAccess world, BlockPos pos)
	{
		final TileEntity te = world.getTileEntity(pos);
		if (te != null)
		{
			if (tileEntityType.isInstance(te))
			{
				return (T)te;
			}
			else
			{
				// warn
			}
		}
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int unused)
	{
		if (tileEntityType != null)
		{
			try
			{
				return tileEntityType.newInstance();
			}
			catch (InstantiationException e)
			{
				throw new IllegalStateException("Failed to create a new instance of an illegal class " + this.tileEntityType, e);
			}
			catch (IllegalAccessException e)
			{
				throw new IllegalStateException("Failed to create a new instance of " + this.tileEntityType + ", because lack of permissions", e);
			}
		}
		return null;
	}
}
