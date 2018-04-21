package growthcraft.core.handlers;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class FluidHandler implements IFluidHandler {
	// REMOVEME
	
    private FluidTank fluidTank;
    private TileEntity tileEntity;

    public FluidHandler(TileEntity entity, FluidTank tank) {
        this.tileEntity = entity;
        this.fluidTank = tank;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    public void setFluidTank(FluidTank tank) {
        this.fluidTank = tank;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return fluidTank.getTankProperties();
    }

    @Override
    public int fill(FluidStack fluidStack, boolean doFill) {
        if (fluidStack == null) {
            return 0;
        }

        FluidStack fluidStackCopy = fluidStack.copy();
        FluidStack fluidStackTank = fluidTank.getFluid();

        int totalUsed = 0;

        if (fluidStackTank != null && fluidStackTank.amount > 0 && !fluidStackTank.isFluidEqual(fluidStackCopy)) {
            return 0;
        }

        while (fluidStackCopy.amount > 0) {
            int used = this.fluidTank.fill(fluidStackCopy, doFill);
            fluidStackCopy.amount -= used;
            if (used > 0) {
                this.tileEntity.markDirty();
            }
            totalUsed += used;
        }

        return totalUsed;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack fluidStack, boolean doDrain) {
        if (fluidStack != null && fluidTank.getFluidAmount() == 0) {
            return null;
        }

        FluidStack fluidStackCopy = fluidStack.copy();
        FluidStack fluidStackTank = fluidTank.getFluid();

        if (fluidStackTank != null && !fluidStackTank.isFluidEqual(fluidStackCopy)) {
            return null;
        }

        return this.drain(fluidStack.amount, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        FluidStack fluidStackDrained = fluidTank.drain(maxDrain, doDrain);
        return fluidStackDrained;
    }
}
