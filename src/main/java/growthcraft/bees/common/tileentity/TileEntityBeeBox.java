package growthcraft.bees.common.tileentity;

import growthcraft.bees.common.inventory.ContainerBeeBox;
import growthcraft.bees.common.lib.config.BeesRegistry;
import growthcraft.bees.common.tileentity.device.DeviceBeeBox;
import growthcraft.bees.shared.init.GrowthcraftBeesFluids;
import growthcraft.bees.shared.init.GrowthcraftBeesItems;
import growthcraft.core.shared.item.EnumDye;
import growthcraft.core.shared.utils.AuxFX;
import growthcraft.core.shared.inventory.GrowthcraftInternalInventory;
import growthcraft.core.shared.tileentity.GrowthcraftTileInventoryBase;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.feature.IItemOperable;
import growthcraft.core.shared.tileentity.feature.IInteractionObject;
import growthcraft.core.shared.item.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityBeeBox extends GrowthcraftTileInventoryBase implements ITickable, IItemOperable, IInteractionObject {
    @Override
    public String getDefaultInventoryName() {
        return "container.growthcraft_bees.beeBox";
    }

    private static final int beeBoxVersion = 3;
    private static final int[] beeSlotIds = new int[]{0};
    private static final int[] honeyCombSlotIds = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
    private DeviceBeeBox beeBox = new DeviceBeeBox(this);

    @Override
    public String getGuiID() {
        return "growthcraft_bees:bee_box";
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerBeeBox(playerInventory, this);
    }

    @Override
    public void onInventoryChanged(IInventory inv, int index) {
        super.onInventoryChanged(inv, index);
        if (index == 0) {
            markDirty();
        } else if (index > 0) {
            markDirtyAndUpdate();
        }
    }

    public enum HoneyCombExpect {
        ANY,
        EMPTY,
        FILLED
    }

    @Override
    public GrowthcraftInternalInventory createInventory() {
        return new GrowthcraftInternalInventory(this, 28);
    }

    @Override
    public void update() {
        if (!world.isRemote)
            beeBox.update();
    }

    public void updateBlockTick() {
        if (world.isRemote) {
            beeBox.updateClientTick();
        } else {
            beeBox.updateTick();
        }
    }

    public boolean hasBonus() {
        return beeBox.hasBonus();
    }

    public float getGrowthRate() {
        return beeBox.getGrowthRate();
    }

    public void setTime(int v) {
        beeBox.setBonusTime(v);
    }

    public boolean slotHasHoneyComb(int index, HoneyCombExpect expects) {
        final ItemStack slotItem = getStackInSlot(index);
        if (ItemUtils.isEmpty(slotItem))
            return false;
        switch (expects) {
            case EMPTY:
                return BeesRegistry.instance().isItemEmptyHoneyComb(slotItem);
            case FILLED:
                return BeesRegistry.instance().isItemFilledHoneyComb(slotItem);
            default:
                return BeesRegistry.instance().isItemHoneyComb(slotItem);
        }
    }

    public boolean slotHasEmptyComb(int index) {
        return slotHasHoneyComb(index, HoneyCombExpect.EMPTY);
    }

    public int countCombsOfType(HoneyCombExpect type) {
        int count = 0;
        for (int i = 1; i < getSizeInventory(); ++i) {
            if (!ItemUtils.isEmpty(getStackInSlot(i))) {
                if (slotHasHoneyComb(i, type)) {
                    count++;
                }
            }
        }
        return count;
    }

    //counts filled honeycombs only
    public int countHoney() {
        return countCombsOfType(HoneyCombExpect.FILLED);
    }

    public int countEmptyCombs() {
        return countCombsOfType(HoneyCombExpect.EMPTY);
    }

    //counts both empty and filled honeycombs
    public int countCombs() {
        return countCombsOfType(HoneyCombExpect.ANY);
    }

    public int getHoneyCombMax() {
        return honeyCombSlotIds.length;
    }

    public int countBees() {
        final ItemStack stack = getStackInSlot(ContainerBeeBox.SlotId.BEE);
        if (ItemUtils.isEmpty(stack))
            return 0;
        return stack.getCount();
    }

    public boolean hasBees() {
        return countBees() != 0;
    }

    public boolean hasMaxBees() {
        // in the case they use a mod that overrides the maximum item stack
        return countBees() >= 64;
    }

    public boolean isHoneyEnough(int size) {
        return countHoney() >= size;
    }

    public ItemStack getBeeStack() {
        return getStackInSlot(ContainerBeeBox.SlotId.BEE);
    }

    private void setBeeStack(ItemStack itemstack) {
        setInventorySlotContents(ContainerBeeBox.SlotId.BEE, itemstack);
        markDirtyAndUpdate();
    }

    public void spawnBee() {
        final ItemStack beestack = getBeeStack();
        if (beestack == null) {
            // Put a bee in the slot if we have none currently
            setBeeStack(GrowthcraftBeesItems.bee.asStack());
        } else {
            // Ensure that the item in the slot IS a bee, and prevent duplication
            if (BeesRegistry.instance().isItemBee(beestack)) {
                setBeeStack(ItemUtils.increaseStack(beestack));
            }
        }
    }

    public void spawnHoneyCombs(int n) {
        for (int i = 1; i < getSizeInventory(); ++i) {
            if (n <= 0) break;
            final ItemStack stack = getStackInSlot(i);
            if (ItemUtils.isEmpty(stack)) {
                setInventorySlotContents(i, GrowthcraftBeesItems.honeyCombEmpty.asStack());
                markDirtyAndUpdate();
                n--;
            }
        }
    }

    public void spawnHoneyComb() {
        spawnHoneyCombs(1);
    }

    public boolean decreaseHoney(int count) {
        boolean shouldMark = false;
        for (int i = 1; i < getSizeInventory(); ++i) {
            if (count <= 0) break;
            if (slotHasHoneyComb(i, HoneyCombExpect.FILLED)) {
                final ItemStack stack = getStackInSlot(i);
                final ItemStack result = BeesRegistry.instance().getEmptyHoneyComb(stack);
                setInventorySlotContents(i, result != null ? result.copy() : null);
                count--;
                shouldMark = true;
            }
        }
        if (shouldMark) {
            markDirtyAndUpdate();
            return true;
        }
        return false;
    }

    public boolean fillHoneyCombs(int count) {
        boolean shouldMark = false;
        for (int i = 1; i < getSizeInventory(); ++i) {
            if (count <= 0) break;
            final ItemStack stack = getStackInSlot(i);
            if (!ItemUtils.isEmpty(stack) && slotHasEmptyComb(i)) {
                final ItemStack resultStack = BeesRegistry.instance().getFilledHoneyComb(stack);
                if (resultStack != null) {
                    setInventorySlotContents(i, resultStack.copy());
                }
                count--;
                shouldMark = true;
            }
        }
        if (shouldMark) {
            markDirtyAndUpdate();
            return true;
        }
        return false;
    }

    public void fillHoneyComb() {
        fillHoneyCombs(1);
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_READ)
    public void readFromNBT_BeeBox(NBTTagCompound nbt) {
        beeBox.readFromNBT(nbt, "bee_box");
        if (nbt.hasKey("time")) {
            beeBox.setBonusTime(nbt.getShort("time"));
        }
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_WRITE)
    public void writeToNBT_BeeBox(NBTTagCompound nbt) {
        nbt.setInteger("BeeBox.version", beeBoxVersion);
        beeBox.writeToNBT(nbt, "bee_box");
    }

    /************
     * HOPPER
     ************/
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == ContainerBeeBox.SlotId.BEE) {
            return BeesRegistry.instance().isItemBee(stack);
        } else {
            return BeesRegistry.instance().isItemHoneyComb(stack);
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.UP ? beeSlotIds : honeyCombSlotIds;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side) {
        return true;
    }

    @Override
    public boolean tryPlaceItem(IItemOperable.Action action, EntityPlayer player, ItemStack stack) {
        if (IItemOperable.Action.RIGHT != action) return false;
        if (!ItemUtils.isEmpty(stack)) {
            final Item item = stack.getItem();
            if (item == Items.FLOWER_POT) {
                if (isHoneyEnough(6)) {
                    ItemUtils.addStackToPlayer(GrowthcraftBeesItems.honeyJar.asStack(), player, world, getPos(), false);
                    ItemUtils.consumeStackOnPlayer(stack, player);
                    decreaseHoney(6);
                    return true;
                }
            } else if (item == Items.DYE) {
                int time = 0;
                if (stack.getItemDamage() == EnumDye.PINK.meta) {
                    time = 9600;
                } else if (stack.getItemDamage() == EnumDye.MAGENTA.meta) {
                    time = 4800;
                }
                if (time > 0) {
                    setTime(time);
                    world.playEvent(AuxFX.BONEMEAL, getPos(), 0);
                    ItemUtils.consumeStackOnPlayer(stack, player);
                    markDirtyAndUpdate();
                }
                return true;
            } else if (item == Items.GLASS_BOTTLE) {
                if (GrowthcraftBeesFluids.honey != null && isHoneyEnough(2)) {
                    final ItemStack result = GrowthcraftBeesFluids.honey.asBottleItemStack();
                    if (!ItemUtils.isEmpty(result)) {
                        ItemUtils.addStackToPlayer(result, player, world, getPos(), false);
                        ItemUtils.decrPlayerCurrentInventorySlot(player, 1);
                        decreaseHoney(2);
                        return true;
                    }
                }
            } else if (item == Items.BUCKET) {
                if (GrowthcraftBeesFluids.honey != null && isHoneyEnough(6)) {
                    final ItemStack result = GrowthcraftBeesFluids.honey.asBucketItemStack();
                    if (!ItemUtils.isEmpty(result)) {
                        ItemUtils.addStackToPlayer(result, player, world, getPos(), false);
                        ItemUtils.decrPlayerCurrentInventorySlot(player, 1);
                        decreaseHoney(6);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean tryTakeItem(IItemOperable.Action action, EntityPlayer player, ItemStack onHand) {
        return false;
    }
}
