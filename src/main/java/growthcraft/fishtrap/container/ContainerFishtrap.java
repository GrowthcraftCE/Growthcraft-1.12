package growthcraft.fishtrap.container;

import growthcraft.fishtrap.tileentity.TileEntityFishtrap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFishtrap extends Container {

    private TileEntityFishtrap tileEntityFishtrap;

    public ContainerFishtrap(IInventory inventory, TileEntityFishtrap tileEntityFishtrap) {

        IItemHandler handler = tileEntityFishtrap.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        // Fishtrap Output Inventory Slots
        SlotItemHandler[] fishTrapInventorySlots = {
                new SlotItemHandler(handler, 0, 44, 20),
                new SlotItemHandler(handler, 1, 62, 20),
                new SlotItemHandler(handler, 2, 80, 20),
                new SlotItemHandler(handler, 3, 98, 20),
                new SlotItemHandler(handler, 4, 116, 20),
                new SlotItemHandler(handler, 5, 134, 20)
        };

        for(SlotItemHandler fishTrapInventorySlot : fishTrapInventorySlots) {
            this.addSlotToContainer(fishTrapInventorySlot);
        }

        // Fishtrap Bait Inventory
        SlotItemHandler baitSlot = new SlotItemHandler(handler, 6, 17, 20);
        this.addSlotToContainer(baitSlot);

        // Player Inventory Containers
        int posX = 8;
        int posY = 51;

        for(int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(inventory, x + y * 9 + 9, posX + x * 18, posY + y * 18));
            }
        }

        for(int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(inventory, x, posX + x * 18, posY + 58));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex) {
        ItemStack itemStackCopy = ItemStack.EMPTY;
        Slot slot = (Slot) this.inventorySlots.get(slotIndex);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            itemStackCopy = itemStack.copy();
            if(slotIndex < 9) {
                if(!this.mergeItemStack(itemStack, 9 , this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemStack, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if(itemStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

        }

        return itemStackCopy;
    }
}
