package growthcraft.core.common.tileentity;

import growthcraft.core.common.inventory.GrowthcraftInternalInventory;
import growthcraft.core.common.inventory.IInventoryWatcher;
import growthcraft.core.common.inventory.InventoryProcessor;
import growthcraft.core.common.tileentity.event.TileEventHandler;
import growthcraft.core.common.tileentity.feature.ICustomDisplayName;
import growthcraft.core.utils.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * Extend this base class if you want a Tile with an `Inventory`
 */
public abstract class GrowthcraftTileInventoryBase extends GrowthcraftTileBase implements ISidedInventory, ICustomDisplayName, IInventoryWatcher
{
	protected static final int[] NO_SLOTS = new int[]{};

	protected String inventoryName;
	protected GrowthcraftInternalInventory inventory;

	public GrowthcraftTileInventoryBase()
	{
		super();
		this.inventory = createInventory();
	}

	public GrowthcraftInternalInventory createInventory()
	{
		return new GrowthcraftInternalInventory(this, 0);
	}

	public GrowthcraftInternalInventory getInternalInventory()
	{
		return inventory;
	}

	public String getDefaultInventoryName()
	{
		return "grc.inventory.name";
	}

	@Override
	public void onInventoryChanged(IInventory inv, int index)
	{
		markDirty();
	}

	@Override
	public void onItemDiscarded(IInventory inv, ItemStack stack, int index, int discardedAmount)
	{
		final ItemStack discarded = stack.copy();
		discarded.setCount( discardedAmount );
		ItemUtils.spawnItemStack(world, pos, discarded, world.rand);
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? inventoryName : getDefaultInventoryName();
	}

	@Override
	public boolean hasCustomName()
	{
		return inventoryName != null && inventoryName.length() > 0;
	}

	@Override
	public void setGuiDisplayName(String string)
	{
		this.inventoryName = string;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory.getStackInSlot(index);
	}

	public ItemStack tryMergeItemIntoSlot(ItemStack itemstack, int index)
	{
		final ItemStack result = ItemUtils.mergeStacksBang(getStackInSlot(index), itemstack);
		if (result != null)
		{
			inventory.setInventorySlotContents(index, result);
		}
		return result;
	}

	// Attempts to merge the given itemstack into the main slot
	public ItemStack tryMergeItemIntoMainSlot(ItemStack itemstack)
	{
		return tryMergeItemIntoSlot(itemstack, 0);
	}

	@Override
	public ItemStack decrStackSize(int index, int par2)
	{
		return inventory.decrStackSize(index, par2);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return inventory.removeStackFromSlot(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemstack)
	{
		inventory.setInventorySlotContents(index, itemstack);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		if (world.getTileEntity(pos) != this)
		{
			return false;
		}
		return player.getDistanceSq((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer _player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer _player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return inventory.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side)
	{
		return InventoryProcessor.instance().canInsertItem(this, stack, slot);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side)
	{
		return InventoryProcessor.instance().canExtractItem(this, stack, slot);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return NO_SLOTS;
	}
	
	@Override
	public boolean isEmpty() {
		return inventory.isEmpty();
	}
	
	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public int getField(int id) {
		return inventory.getField(id);
	}

	@Override
	public void setField(int id, int value) {
		inventory.setField(id, value);
	}

	@Override
	public int getFieldCount() {
		return inventory.getFieldCount();
	}

	protected void readInventoryFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("items"))
		{
			inventory.readFromNBT(nbt, "items");
		}
		else if (nbt.hasKey("inventory"))
		{
			inventory.readFromNBT(nbt, "inventory");
		}
	}

	private void readInventoryNameFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("name"))
		{
			this.inventoryName = nbt.getString("name");
		}
		else if (nbt.hasKey("inventory_name"))
		{
			this.inventoryName = nbt.getString("inventory_name");
		}
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readInventoryFromNBT(nbt);
		// Do not reload the inventory name from NBT, allow the ItemStack to do that
		//readInventoryNameFromNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_Inventory(NBTTagCompound nbt)
	{
		readInventoryFromNBT(nbt);
		readInventoryNameFromNBT(nbt);
	}

	private void writeInventoryToNBT(NBTTagCompound nbt)
	{
		inventory.writeToNBT(nbt, "inventory");
		// NAME
		if (hasCustomName())
		{
			nbt.setString("inventory_name", inventoryName);
		}
		nbt.setInteger("inventory_tile_version", 3);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeInventoryToNBT(nbt);
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_Inventory(NBTTagCompound nbt)
	{
		writeInventoryToNBT(nbt);
	}
}
