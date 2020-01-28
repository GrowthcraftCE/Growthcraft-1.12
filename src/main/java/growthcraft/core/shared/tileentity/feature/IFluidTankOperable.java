package growthcraft.core.shared.tileentity.feature;

import growthcraft.core.shared.fluids.GrowthcraftFluidUtils;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public interface IFluidTankOperable {

    default FluidTankInfo[] getTankInfo(EnumFacing from) {
        return GrowthcraftFluidUtils.convertTankPropsToInfo(getTankProperties(from));
    }

    IFluidTankProperties[] getTankProperties(EnumFacing from);

    default boolean canFill(EnumFacing from, Fluid fluid) {
        for (IFluidTankProperties props : getTankProperties(from)) {
            if (!props.canFill())
                continue;
            if (props.canFillFluidType(new FluidStack(fluid, 1)))
                return true;
        }
        return false;
    }


    default boolean canDrain(EnumFacing from, Fluid fluid) {
        for (IFluidTankProperties props : getTankProperties(from)) {
            if (!props.canDrain())
                continue;
            if (props.canDrainFluidType(new FluidStack(fluid, 1)))
                return true;
        }
        return false;
    }

    int fill(EnumFacing dir, FluidStack stack, boolean shouldFill);

    FluidStack drain(EnumFacing dir, int amount, boolean shouldDrain);

    FluidStack drain(EnumFacing dir, FluidStack stack, boolean shouldDrain);
}
