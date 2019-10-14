package growthcraft.core.shared.handlers;

import growthcraft.core.shared.tileentity.feature.IFluidTankOperable;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluidHandlerBlockWrapper implements IFluidHandler {

    private final EnumFacing direction;
    private final IFluidTankOperable fh;

    public FluidHandlerBlockWrapper(IFluidTankOperable fh, EnumFacing direction) {
        this.direction = direction;
        this.fh = fh;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return fh.getTankProperties(direction);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return fh.fill(direction, resource, doFill);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return fh.drain(direction, resource, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return fh.drain(direction, maxDrain, doDrain);
    }
}
