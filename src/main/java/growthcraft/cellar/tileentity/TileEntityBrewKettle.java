package growthcraft.cellar.tileentity;

import net.minecraft.block.BlockFire;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;

public class TileEntityBrewKettle extends TileEntity implements ICapabilityProvider {

    // Is this TE attached to a block that is heated
    private boolean heated;
    // How long have we been brewing
    private int brewTime;
    // How long it takes to brew a recipe.
    private int maxBrewTime;

    private ItemStackHandler itemStackHandler;

    public TileEntityBrewKettle() {
        itemStackHandler = new ItemStackHandler(2);
        heated = false;
        brewTime = 0;
        maxBrewTime = 2000;
    }

    private boolean canBrew() {
        /**
         * Determines if we have a valid recipe for brewing. 
         * - Must have a valid fluid in tank0
         * - Must have a brewable item in itemStackHandler.slot0
         * - Must have a heat source in the block below.
         */
        return true;
    }

    private void doBrewing() {
        // We will need a brewing timer.
        
    }

    public int getBrewTime() {
        return brewTime;
    }

    public int getMaxBrewTime() {
        return maxBrewTime;
    }

    public float getBrewProgress() {
        return (brewTime * 100 / maxBrewTime);
    }

    public boolean isHeated() {
        return world.getBlockState(pos.down()).getBlock() instanceof BlockFire;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        brewTime = compound.getInteger("BrewTime");
        maxBrewTime = compound.getInteger("MaxBrewTime");
        itemStackHandler.deserializeNBT(compound.getCompoundTag("ItemInventory"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("BrewTime", brewTime);
        compound.setInteger("MaxBrewTime", maxBrewTime);
        compound.setTag("ItemInventory", itemStackHandler.serializeNBT());
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

    @SideOnly(Side.CLIENT)
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

    /* -- ICapabilityProvider -- */

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

            if (facing == null) {
                return (T) new CombinedInvWrapper(itemStackHandler);
            }

            switch (facing) {
                case UP:
                    return (T) this.itemStackHandler;
                case EAST:
                    return null;
                case WEST:
                    return null;
                case NORTH:
                    return null;
                case SOUTH:
                    return null;
                default:
                    return (T) this.itemStackHandler;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }


}
