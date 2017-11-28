package growthcraft.cellar.tileentity;

import growthcraft.cellar.blocks.BlockBrewKettle;
import growthcraft.cellar.init.GrowthcraftCellarBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;

public class TileEntityBrewKettle extends TileEntity implements ITickable, ICapabilityProvider {

    private int cooldown;
    private boolean isHeated;

    //private IFluidHandler fluidHandlerInput;
    //private IFluidHandler fluidHandlerOutput;
    private ItemStackHandler itemStackHandlerInput;
    private ItemStackHandler itemStackHandlerOutput;

    private FluidHandlerItemStack fluidHandlerItemStack;

    public TileEntityBrewKettle() {
        //fluidHandlerInput = new FluidTank(5000);
        //fluidHandlerOutput = new FluidTank(5000);
        itemStackHandlerInput = new ItemStackHandler(1);
        itemStackHandlerOutput = new ItemStackHandler(1);

        //fluidHandlerItemStack = new FluidHandlerItemStack(5000);
        this.cooldown = 0;
        this.isHeated = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.cooldown = compound.getInteger("cooldown");
        this.itemStackHandlerOutput.deserializeNBT(compound.getCompoundTag("inventoryOutput"));
        this.itemStackHandlerInput.deserializeNBT(compound.getCompoundTag("inventoryInput"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("cooldown", this.cooldown);
        compound.setTag("inventoryOutput", this.itemStackHandlerOutput.serializeNBT());
        compound.setTag("inventoryInput", this.itemStackHandlerInput.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        this.cooldown++;
        this.cooldown %= 100;

        if (this.world.isRemote) {
            // Check to determine if we have heat or not.
            // TODO: Need to implement a better heat detection system.
            this.isHeated = this.world.getBlockState(this.pos.down()).getMaterial() == Material.FIRE;
            world.setBlockState(
                    pos, GrowthcraftCellarBlocks.blockBrewKettle.getDefaultState().withProperty(BlockBrewKettle.HEATED, Boolean.valueOf(this.isHeated)), 3
            );
        }
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            //if (facing != null) {
            return (T) new CombinedInvWrapper(itemStackHandlerInput, itemStackHandlerOutput);
            //}
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            //if (facing != null) {
            return (T) new CombinedInvWrapper(itemStackHandlerInput, itemStackHandlerOutput);
            //}
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
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
}
