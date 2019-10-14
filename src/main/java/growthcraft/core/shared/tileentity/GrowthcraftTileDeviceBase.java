package growthcraft.core.shared.tileentity;

import java.io.IOException;

import javax.annotation.Nullable;

import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.handlers.FluidHandlerBlockWrapper;
import growthcraft.core.shared.fluids.FluidTanks;
import growthcraft.core.shared.fluids.IFluidTanks;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.feature.IFluidTankOperable;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 * Extend this base class if you want a base class with an `Inventory` and `Fluid Tanks`
 */
public abstract class GrowthcraftTileDeviceBase extends GrowthcraftTileInventoryBase implements IFluidTankOperable, IFluidTanks {
    private FluidTanks tanks;

    public GrowthcraftTileDeviceBase() {
        super();
        this.tanks = new FluidTanks(createTanks());
    }

    protected void markFluidDirty() {
        markDirty();
    }

    protected FluidTank[] createTanks() {
        return new FluidTank[]{};
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return true;
    }

    protected FluidStack doDrain(EnumFacing dir, int amount, boolean shouldDrain) {
        return null;
    }

    protected FluidStack doDrain(EnumFacing dir, FluidStack stack, boolean shouldDrain) {
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing dir, int amount, boolean shouldDrain) {
        final FluidStack result = doDrain(dir, amount, shouldDrain);
        if (shouldDrain && FluidTest.isValid(result)) markFluidDirty();
        return result;
    }

    @Override
    public FluidStack drain(EnumFacing dir, FluidStack stack, boolean shouldDrain) {
        if (!FluidTest.isValid(stack)) return null;
        final FluidStack result = doDrain(dir, stack, shouldDrain);
        if (shouldDrain && FluidTest.isValid(result)) markFluidDirty();
        return result;
    }

    protected int doFill(EnumFacing dir, FluidStack stack, boolean shouldFill) {
        return 0;
    }

    @Override
    public int fill(EnumFacing dir, FluidStack stack, boolean shouldFill) {
        final int result = doFill(dir, stack, shouldFill);
        if (shouldFill && result != 0) markFluidDirty();
        return result;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return tanks.getTankInfo(from);
    }

    @Override
    public IFluidTankProperties[] getTankProperties(EnumFacing from) {
        return tanks.getTankProperties(from);
    }

    public IFluidTanks getTanks() {
        return tanks;
    }

    @Override
    public int getTankCount() {
        return tanks.getTankCount();
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return tanks.getFluidTanks();
    }

    @Override
    public int getFluidAmountScaled(int scalar, int slot) {
        return tanks.getFluidAmountScaled(scalar, slot);
    }

    @Override
    public float getFluidAmountRate(int slot) {
        return tanks.getFluidAmountRate(slot);
    }

    @Override
    public boolean isFluidTankFilled(int slot) {
        return tanks.isFluidTankFilled(slot);
    }

    @Override
    public boolean isFluidTankFull(int slot) {
        return tanks.isFluidTankFull(slot);
    }

    @Override
    public boolean isFluidTankEmpty(int slot) {
        return tanks.isFluidTankEmpty(slot);
    }

    @Override
    public int getFluidAmount(int slot) {
        return tanks.getFluidAmount(slot);
    }

    @Override
    public FluidTank getFluidTank(int slot) {
        return tanks.getFluidTank(slot);
    }

    @Override
    public FluidStack getFluidStack(int slot) {
        return tanks.getFluidStack(slot);
    }

    @Override
    public FluidStack drainFluidTank(int slot, int amount, boolean shouldDrain) {
        final FluidStack result = tanks.drainFluidTank(slot, amount, shouldDrain);
        if (shouldDrain && FluidTest.isValid(result)) markFluidDirty();
        return result;
    }

    @Override
    public int fillFluidTank(int slot, FluidStack fluid, boolean shouldFill) {
        final int result = tanks.fillFluidTank(slot, fluid, shouldFill);
        if (shouldFill && result != 0) markFluidDirty();
        return result;
    }

    @Override
    public void setFluidStack(int slot, FluidStack stack) {
        tanks.setFluidStack(slot, stack);
        markFluidDirty();
    }

    @Override
    public Fluid getFluid(int slot) {
        return tanks.getFluid(slot);
    }

    @Override
    public void clearTank(int slot) {
        tanks.clearTank(slot);
        markFluidDirty();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {

            if (facing == null) {
                facing = EnumFacing.UP;
            }

            return (T) new FluidHandlerBlockWrapper(this, facing);
        }

        return super.getCapability(capability, facing);
    }

    protected void readTanksFromNBT(NBTTagCompound nbt) {
        if (tanks != null)
            tanks.readFromNBT(nbt);
    }

    @Override
    public void readFromNBTForItem(NBTTagCompound nbt) {
        super.readFromNBTForItem(nbt);
        readTanksFromNBT(nbt);
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_READ)
    public void readFromNBT_DeviceBase(NBTTagCompound nbt) {
        readTanksFromNBT(nbt);
    }

    private void writeTanksToNBT(NBTTagCompound nbt) {
        if (tanks != null)
            tanks.writeToNBT(nbt);
    }

    @Override
    public void writeToNBTForItem(NBTTagCompound nbt) {
        super.writeToNBTForItem(nbt);
        writeTanksToNBT(nbt);
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_WRITE)
    public void writeToNBT_DeviceBase(NBTTagCompound nbt) {
        writeTanksToNBT(nbt);
    }

    @TileEventHandler(event = TileEventHandler.EventType.NETWORK_READ)
    public boolean readFromStream_FluidTanks(ByteBuf stream) throws IOException {
        if (tanks != null)
            tanks.readFromStream(stream);
        return true;
    }

    @TileEventHandler(event = TileEventHandler.EventType.NETWORK_WRITE)
    public boolean writeToStream_FluidTanks(ByteBuf stream) throws IOException {
        if (tanks != null)
            tanks.writeToStream(stream);
        return false;
    }
}
