package growthcraft.core.shared.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import growthcraft.core.shared.events.EventTankDrained;
import growthcraft.core.shared.fluids.GrowthcraftFluidUtils;
import growthcraft.core.shared.inventory.InventoryProcessor;
import growthcraft.core.shared.io.nbt.INBTItemSerializable;
import growthcraft.core.shared.item.IItemTileBlock;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.core.shared.tileentity.feature.ICustomDisplayName;
import growthcraft.core.shared.tileentity.feature.IFluidTankOperable;
import growthcraft.core.shared.tileentity.feature.IItemOperable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public abstract class GrowthcraftBlockContainer extends GrowthcraftBlockBase implements IDroppableBlock, IWrenchable, ITileEntityProvider
{
	protected Random rand = new Random();
	protected Class<? extends TileEntity> tileEntityType;

	public GrowthcraftBlockContainer(@Nonnull Material material)
	{
		super(material);
		this.hasTileEntity = true;
	}
	

	public GrowthcraftBlockContainer(@Nonnull Material material, @Nonnull MapColor mapColor) {
		super(material, mapColor);
		this.hasTileEntity = true;
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
	{
		super.eventReceived(state, worldIn, pos, id, param);
		final TileEntity te = getTileEntity(worldIn, pos);
		return te != null ? te.receiveClientEvent(id, param) : false;
	}

	@Override
	public int damageDropped(IBlockState state) {
		// TODO: Change it! Depends on how item handles the integer number
	    return getMetaFromState(state);
	}
	
	protected void setTileEntityType(Class<? extends TileEntity> klass)
	{
		this.tileEntityType = klass;
	}

	protected void fellBlockFromWrench(World world, BlockPos pos)
	{
//		final int metadata = world.getBlockMetadata(pos);
		final IBlockState state = world.getBlockState(pos);
		final List<ItemStack> drops = new ArrayList<ItemStack>();
		if (shouldDropTileStack(world, pos, state, 0))
		{
			GrowthcraftLogger.getLogger(Reference.MODID).info("Dropping Tile As ItemStack");
			getTileItemStackDrops(drops, world, pos, state, 0);
			for (ItemStack stack : drops)
			{
				ItemUtils.spawnItemStack(world, pos, stack, world.rand);
			}
			final TileEntity te = getTileEntity(world, pos);
			if (te instanceof IInventory)
			{
				GrowthcraftLogger.getLogger(Reference.MODID).info("Clearing Inventory");
				InventoryProcessor.instance().clearSlots((IInventory)te);
			}
			GrowthcraftLogger.getLogger(Reference.MODID).info("Setting Block To Air");
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
			GrowthcraftLogger.getLogger(Reference.MODID).error("Cannot get tile tag compound for a non IItemTileBlock: stack=%s block=%s", stack, this);
		}
		return null;
	}

	protected void setTileTagCompound(IBlockAccess world, BlockPos pos, ItemStack stack, NBTTagCompound tag)
	{
		final Item item = stack.getItem();
		if (item instanceof IItemTileBlock)
		{
			final IItemTileBlock itb = (IItemTileBlock)item;
			itb.setTileTagCompound(stack, tag);
		}
		else
		{
			GrowthcraftLogger.getLogger(Reference.MODID).error("Cannot set tile tag compound for a non IItemTileBlock: stack=%s block=%s", stack, this);
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
				GrowthcraftLogger.getLogger(Reference.MODID).error("Cannot restore tile from stack, the TileEntity does not support INBTItemSerializable: stack=%s block=%s tile=%s", stack, this, te);
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
					if (!ItemUtils.isEmpty(stack))
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

			if (!ItemUtils.isEmpty(itemstack))
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

	protected boolean shouldDropTileStack(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return false;
	}

	private void getDefaultDrops(List<ItemStack> ret, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		final int count = quantityDropped(state, fortune, /*world.*/rand);
		for (int i = 0; i < count; ++i)
		{
			final Item item = getItemDropped(state, /*world.*/rand, fortune);
			if (item != null)
			{
				ret.add(new ItemStack(item, 1, damageDropped(state)));
			}
		}
	}

	protected void getTileItemStackDrops(List<ItemStack> ret, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
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

	@SuppressWarnings("deprecation")
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
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

	protected boolean playerFillTank(World world, BlockPos pos, IFluidHandler tank, ItemStack is, EntityPlayer player)
	{
		return GrowthcraftFluidUtils.playerFillTank(world, pos, tank, is, player);
	}

	protected boolean playerDrainTank(World world, BlockPos pos, IFluidHandler tank, ItemStack is, EntityPlayer player)
	{
		final FluidStack fs = GrowthcraftFluidUtils.playerDrainTank(world, pos, tank, is, player);
		if (fs != null && fs.amount > 0)
		{
			MinecraftForge.EVENT_BUS.post(new EventTankDrained(player, world, pos, fs));
			return true;
		}
		return false;
	}

	private boolean handleIFluidHandler(World world, BlockPos pos, EntityPlayer player, IBlockState state)
	{
		final TileEntity te = world.getTileEntity(pos);
		if (te instanceof IFluidTankOperable)
		{
			if (world.isRemote)
			{
				return true;
			}
			else
			{
				final IFluidHandler fh = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				if( fh != null ) {
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
		}
		return false;
	}

	protected boolean handleOnUseItem(IItemOperable.Action action, World world, BlockPos pos, EntityPlayer player)
	{
		final TileEntity te = world.getTileEntity(pos);
		if (te instanceof IItemOperable)
		{
			if (world.isRemote)
			{
				return true;
			}
			else
			{
				final IItemOperable ih = (IItemOperable)te;
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
			if (te instanceof IItemOperable)
			{
				if (handleOnUseItem(IItemOperable.Action.LEFT, world, pos, player))
				{
					return;
				}
			}
		}
		super.onBlockClicked(world, pos, player);
	}

	public final boolean grcOnBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (tryWrenchItem(playerIn, worldIn, pos)) return true;
		if (handleIFluidHandler(worldIn, pos, playerIn, state)) return true;
		if (handleOnUseItem(IItemOperable.Action.RIGHT, worldIn, pos, playerIn)) return true;
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return grcOnBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
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
