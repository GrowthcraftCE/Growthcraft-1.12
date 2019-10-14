package growthcraft.core.shared.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public interface IFluidTanks {
    FluidTank[] getFluidTanks();

    FluidTank getFluidTank(int slot);

    FluidStack getFluidStack(int slot);

    int getFluidAmountScaled(int scalar, int slot);

    float getFluidAmountRate(int slot);

    boolean isFluidTankFilled(int slot);

    boolean isFluidTankFull(int slot);

    boolean isFluidTankEmpty(int slot);

    int getFluidAmount(int slot);

    FluidStack drainFluidTank(int slot, int amount, boolean doDrain);

    int fillFluidTank(int slot, FluidStack fluid, boolean doFill);

    void setFluidStack(int slot, FluidStack stack);

    Fluid getFluid(int slot);

    void clearTank(int slot);

    int getTankCount();
}
