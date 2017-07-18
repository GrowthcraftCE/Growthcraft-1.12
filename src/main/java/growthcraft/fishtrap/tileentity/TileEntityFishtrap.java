package growthcraft.fishtrap.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TileEntityFishtrap extends TileEntity implements ITickable, ICapabilityProvider {

    private int cooldown;
    private int random;
    private int intMinCooldown = 256;
    private int intMaxCooldown = 768;

    private ItemStackHandler handler;

    public TileEntityFishtrap() {
        this.cooldown = 0;
        this.random = intMaxCooldown;
        this.handler = new ItemStackHandler(5);
    }

    private int getRandomCooldown() {
        return new Random().nextInt((intMaxCooldown - intMinCooldown) + 1) + intMinCooldown;
    }

    private void doFishing() {
        if ( !getWorld().isRemote ) {
            // Get a random item from the Fishing_Rod LootTable. Pull this from the EntityFishHook class.
            LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.world);
            List<ItemStack> result = this.world.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(new Random(), lootcontext$builder.build());

            for (ItemStack itemstack : result) {
                if ( !this.isInventoryFull(this.handler)) {
                    this.addStackToInventory(this.handler, itemstack, false);
                }
            }
        }
    }

    private ItemStack addStackToInventory(IItemHandler handler, ItemStack stack, boolean simulate) {
        ItemStack remainder = stack;
        for(int slot = 0; slot < handler.getSlots(); slot++) {
            remainder = handler.insertItem(slot, stack, simulate);
            if ( remainder == ItemStack.EMPTY) break;
        }
        return remainder;
    }

    private boolean isInventoryFull(IItemHandler handler) {
        int filledSlots = 0;
        for ( int slot = 0; slot < handler.getSlots(); slot++ ) {
            if(handler.getStackInSlot(slot).getCount() == handler.getSlotLimit(slot)) filledSlots++;
        }
        return filledSlots == handler.getSlots();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.cooldown = compound.getInteger("Cooldown");
        this.handler.deserializeNBT(compound.getCompoundTag("ItemStackHandler"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Cooldown", this.cooldown);
        compound.setTag("ItemStackHandler", this.handler.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        this.cooldown++;
        this.cooldown %= intMaxCooldown;
        if ( cooldown == 0 ) {
            this.random = getRandomCooldown();
            if ( !isInventoryFull(this.handler)) {
                this.doFishing();
            }
        }
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return compound;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if ( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T)this.handler;
            return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if ( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }
}
