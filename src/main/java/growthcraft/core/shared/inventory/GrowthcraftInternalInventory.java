package growthcraft.core.shared.inventory;

import growthcraft.core.shared.io.nbt.INBTSerializableContext;
import growthcraft.core.shared.io.nbt.NBTHelper;
import growthcraft.core.shared.io.nbt.NBTType;
import growthcraft.core.shared.item.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class GrowthcraftInternalInventory implements IInventory, INBTSerializableContext {
    public static final int WILDCARD_SLOT = -1;

    protected String inventoryName;
    protected ItemStack[] items;
    protected int maxSize;
    protected int maxStackSize;
    protected Object parent;

    public GrowthcraftInternalInventory(Object par, int size, int maxStack) {
        this.inventoryName = "grc.inventory.internal.name";
        this.parent = par;
        this.maxSize = size;
        this.maxStackSize = maxStack;
        this.items = new ItemStack[maxSize];
    }

    public GrowthcraftInternalInventory(Object par, int size) {
        this(par, size, 64);
    }

    public int getMaxSize() {
        return maxSize;
    }

    protected void onSlotChanged(int index) {
        if (parent instanceof IInventoryWatcher) {
            ((IInventoryWatcher) parent).onInventoryChanged(this, index);
        } else if (parent instanceof IInventory) {
            ((IInventory) parent).markDirty();
        }
    }

    @Override
    public void markDirty() {
        onSlotChanged(WILDCARD_SLOT);
    }

    public void clear() {
        for (int i = 0; i < getMaxSize(); ++i) {
            items[i] = null;
        }
        onSlotChanged(WILDCARD_SLOT);
    }

    /**
     * @deprecated Use #clear instead
     */
    @Deprecated
    public void clearInventory() {
        clear();
    }

    protected void readFromNBT(NBTTagList data) {
        this.items = ItemUtils.clearInventorySlots(items, getSizeInventory());
        NBTHelper.readInventorySlotsFromNBT(items, data);
        onSlotChanged(WILDCARD_SLOT);
    }

    @Override
    public void readFromNBT(NBTTagCompound data, String name) {
        final NBTTagList list = data.getTagList(name, NBTType.COMPOUND.id);
        if (list != null) {
            readFromNBT(list);
        } else {
            // LOG error
        }
    }

    protected void writeToNBT(NBTTagList data) {
        NBTHelper.writeInventorySlotsToNBT(items, data);
    }

    @Override
    public void writeToNBT(NBTTagCompound data, String name) {
        final NBTTagList invData = new NBTTagList();
        writeToNBT(invData);
        data.setTag(name, invData);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer _player) {
    }

    @Override
    public void closeInventory(EntityPlayer _player) {
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer _player) {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return maxSize;
    }

    @Override
    public int getInventoryStackLimit() {
        return maxStackSize;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    public GrowthcraftInternalInventory setInventoryName(String name) {
        this.inventoryName = name;
        return this;
    }

    @Override
    public String getName() {
        return inventoryName;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        ItemStack stack = items[index];
        return stack != null ? stack : ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        final ItemStack oldStack = items[index];
        items[index] = stack;
        if (!ItemUtils.isEmpty(stack)) {
            if (stack.getCount() > getInventoryStackLimit()) {
                final int discarded = stack.getCount() - getInventoryStackLimit();
                items[index].setCount(getInventoryStackLimit());
                if (discarded > 0) {
                    if (parent instanceof IInventoryWatcher) {
                        ((IInventoryWatcher) parent).onItemDiscarded(this, stack, index, discarded);
                    }
                }
            }
        }
        if (oldStack != stack) {
            onSlotChanged(index);
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        final ItemStack stack = items[index];
        items[index] = null;
        if (!ItemUtils.isEmpty(stack)) onSlotChanged(index);
        return stack;
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (items[index] != null) {
            ItemStack itemstack;

            if (items[index].getCount() <= amount) {
                itemstack = items[index];
                items[index] = null;
            } else {
                itemstack = items[index].splitStack(amount);

                if (items[index].isEmpty()) {
                    items[index] = null;
                }
            }
            onSlotChanged(index);
            return itemstack;
        }
        return null;
    }

    @Override
    public ITextComponent getDisplayName() {
        return (ITextComponent) (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!ItemUtils.isEmpty(stack))
                return false;
        }
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }
}
