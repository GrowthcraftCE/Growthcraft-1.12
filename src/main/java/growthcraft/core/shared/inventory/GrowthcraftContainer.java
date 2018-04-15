package growthcraft.core.shared.inventory;

import growthcraft.core.shared.inventory.slot.SlotInput;
import growthcraft.core.shared.inventory.slot.SlotPlayer;
import growthcraft.core.shared.inventory.slot.SlotPlayerBackpack;
import growthcraft.core.shared.inventory.slot.SlotPlayerHotbar;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.core.shared.tileentity.feature.IGuiNetworkSync;
import growthcraft.core.shared.utils.Platform;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrowthcraftContainer extends Container
{
	protected static final int SLOT_W = 18;
	protected static final int SLOT_H = 18;

	protected TileEntity tileEntity;

	public GrowthcraftContainer(TileEntity te)
	{
		super();
		this.tileEntity = te;
	}

	public boolean mergeWithSlot(Slot slot, ItemStack stack)
	{
		if (ItemUtils.isEmpty(stack)) return false;

		if (slot.isItemValid(stack))
		{
			if (mergeItemStack(stack, slot.slotNumber, slot.slotNumber + 1, false))
			{
				return true;
			}
		}
		return false;
	}

	public boolean mergeWithSlotsOfKind(ItemStack stack, Class<? extends Slot> slotClass)
	{
		if (ItemUtils.isEmpty(stack)) return false;

		int start = -1;
		int end = -1;

		for (Object sub : inventorySlots)
		{
			if (slotClass.isInstance(sub))
			{
				final Slot subSlot = (Slot)sub;
				if (start < 0)
				{
					start = subSlot.slotNumber;
				}
				end = subSlot.slotNumber;
			}
		}
		if (start <= -1 || end <= -1) return false;

		boolean merged = false;
		for (int i = start; i <= end; ++i)
		{
			// Stop iterating if the stack has been successfully merged
			if (stack.isEmpty()) break;
			// Get the object at the given index
			final Object obj = inventorySlots.get(i);
			// Determine if the slot is the expected (in case the slots are interleaved)
			if (slotClass.isInstance(obj))
			{
				final Slot subSlot = (Slot)obj;
				// try to merge it
				merged |= mergeWithSlot(subSlot, stack);
			}
		}
		return merged;
	}

	public boolean mergeWithPlayer(ItemStack stack)
	{
		return mergeWithSlotsOfKind(stack, SlotPlayer.class);
	}

	public boolean mergeWithPlayerHotbar(ItemStack stack)
	{
		return mergeWithSlotsOfKind(stack, SlotPlayerHotbar.class);
	}

	public boolean mergeWithPlayerBackpack(ItemStack stack)
	{
		return mergeWithSlotsOfKind(stack, SlotPlayerBackpack.class);
	}

	public boolean mergeWithInput(ItemStack stack)
	{
		return mergeWithSlotsOfKind(stack, SlotInput.class);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		if (Platform.isClient())
		{
			return ItemStack.EMPTY;
		}

		final Slot s = getSlot(index);
		ItemStack itemstack = ItemStack.EMPTY;

		if (s != null && s.getHasStack())
		{
			final ItemStack stack = s.getStack();
			itemstack = stack.copy();

			boolean wasMerged = false;

			if (s instanceof SlotPlayer)
			{
				wasMerged |= mergeWithInput(stack);
				if (!wasMerged)
				{
					if (s instanceof SlotPlayerHotbar)
					{
						wasMerged |= mergeWithPlayerBackpack(stack);
					}
					else if (s instanceof SlotPlayerBackpack)
					{
						wasMerged |= mergeWithPlayerHotbar(stack);
					}
				}
			}
			else
			{
				wasMerged |= mergeWithPlayer(stack);
			}

			if (wasMerged)
			{
				s.onSlotChange(stack, itemstack);
			}
			else
			{
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty())
			{
				s.putStack(ItemStack.EMPTY);
			}
			s.onSlotChanged();

			if (stack.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			s.onTake(player, stack);
		}
		return itemstack;
	}

	public void bindPlayerHotbar(IInventory playerInventory, int x, int y)
	{
		for (int i = 0; i < 9; ++i)
		{
			addSlotToContainer(new SlotPlayerHotbar(playerInventory, i, x + i * SLOT_W, y));
		}
	}

	public void bindPlayerBackpack(IInventory playerInventory, int x, int y)
	{
		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 9; ++col)
			{
				final int slotIndex = 9 + col + row * 9;
				addSlotToContainer(new SlotPlayerBackpack(playerInventory, slotIndex, x + col * SLOT_W, y + row * SLOT_H));
			}
		}
	}

	public void bindPlayerInventory(IInventory playerInventory, int x, int y)
	{
		bindPlayerBackpack(playerInventory, x, y);
		bindPlayerHotbar(playerInventory, x, y + 58);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		if (tileEntity instanceof IInventory)
		{
			return ((IInventory)tileEntity).isUsableByPlayer(player);
		}
		return false;
	}

	// crafters
	@Override
	public void addListener(IContainerListener iCrafting)
	{
		super.addListener(iCrafting);
		if (tileEntity instanceof IGuiNetworkSync)
		{
			((IGuiNetworkSync)tileEntity).sendGUINetworkData(this, iCrafting);
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		if (tileEntity instanceof IGuiNetworkSync)
		{
			final IGuiNetworkSync sync = (IGuiNetworkSync)tileEntity;
			for (Object crafter : listeners)
			{
				if (crafter instanceof IContainerListener)
				{
					sync.sendGUINetworkData(this, (IContainerListener)crafter);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int v)
	{
		super.updateProgressBar(id, v);
		if (tileEntity instanceof IGuiNetworkSync)
		{
			((IGuiNetworkSync)tileEntity).receiveGUINetworkData(id, v);
		}
	}
}
