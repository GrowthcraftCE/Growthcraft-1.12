package growthcraft.core.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityRopeKnot extends TileEntity implements ICapabilityProvider {

    private ItemStackHandler handlerInventory;

    public TileEntityRopeKnot() {
        this.handlerInventory = new ItemStackHandler(1);
    }

    public TileEntityRopeKnot(ItemStack stack) {
        this.handlerInventory = new ItemStackHandler(1);
        this.addStackToInventory(handlerInventory, stack, false);
    }

    public void addStackToInventory(ItemStack stack) {
        this.addStackToInventory(this.handlerInventory, stack, false);
    }

    private ItemStack addStackToInventory(IItemHandler handler, ItemStack stack, boolean simulate) {
        ItemStack remainder = stack;
        for(int slot = 0; slot < handler.getSlots(); slot++) {
            remainder = handler.insertItem(slot, stack, simulate);
            if ( remainder == ItemStack.EMPTY) break;
        }
        return remainder;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
         this.handlerInventory.deserializeNBT(compound.getCompoundTag("Inventory"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Inventory", this.handlerInventory.serializeNBT());
        return super.writeToNBT(compound);
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
        return (T)this.handlerInventory;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }
}
