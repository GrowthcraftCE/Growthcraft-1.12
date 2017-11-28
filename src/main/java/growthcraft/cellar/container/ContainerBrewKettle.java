package growthcraft.cellar.container;


import growthcraft.cellar.tileentity.TileEntityBrewKettle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBrewKettle extends Container {

    //private TileEntityBrewKettle tileEntityBrewKettle;

    public ContainerBrewKettle(IInventory inventory, TileEntityBrewKettle tileEntityBrewKettle) {


        IItemHandler handler = tileEntityBrewKettle.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        IFluidHandler fluidInputHandler = tileEntityBrewKettle.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

        SlotItemHandler inputSlot = new SlotItemHandler(handler, 0, 80, 35);
        this.addSlotToContainer(inputSlot);

        SlotItemHandler outputSlot = new SlotItemHandler(handler, 1, 141, 17);
        this.addSlotToContainer(outputSlot);


        // Player Inventory Containers
        int posX = 8;
        int posY = 84;

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(inventory, x + y * 9 + 9, posX + x * 18, posY + y * 18));
            }
        }

        for (int x = 0; x < 9; ++x) {
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
        Slot slot = this.inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            itemStackCopy = itemStack.copy();
            if (slotIndex < 9) {
                if (!this.mergeItemStack(itemStack, 9, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemStack, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

        }

        return itemStackCopy;
    }

}
