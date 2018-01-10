package growthcraft.core.lib.legacy;

import growthcraft.core.api.fluids.FluidUtils;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public interface ILegacyFluidHandler extends IFluidHandler {
	
	default FluidTankInfo[] getTankInfo() {
		return getTankInfo(EnumFacing.NORTH);
	}

	default FluidTankInfo[] getTankInfo(EnumFacing from) {
		return FluidUtils.convertTankPropsToInfo(getTankProperties());
	}
	
	IFluidTankProperties[] getTankProperties(EnumFacing from);
	
	@Override
	default IFluidTankProperties[] getTankProperties() {
		return getTankProperties(EnumFacing.NORTH);
	}
	
	default boolean canFill(Fluid fluid) {
		return canFill(EnumFacing.NORTH, fluid);
	}
	
	default boolean canFill(EnumFacing from, Fluid fluid) {
		for( IFluidTankProperties props: getTankProperties() ) {
			if( !props.canFill() )
				continue;
			if( props.canFillFluidType(new FluidStack(fluid, 1)) )
				return true;
		}
		return false;
	}

	default boolean canDrain(Fluid fluid) {
		return canDrain(EnumFacing.NORTH, fluid);
	}
	
	default boolean canDrain(EnumFacing from, Fluid fluid) {
		for( IFluidTankProperties props: getTankProperties() ) {
			if( !props.canDrain() )
				continue;
			if( props.canDrainFluidType(new FluidStack(fluid, 1)) )
				return true;
		}
		return false;
	}

	int fill(EnumFacing dir, FluidStack stack, boolean shouldFill);
	
	@Override
	default int fill(FluidStack stack, boolean shouldFill) {
		return fill(EnumFacing.NORTH, stack, shouldFill);
	}

	FluidStack drain(EnumFacing dir, int amount, boolean shouldDrain);
	
	default FluidStack drain(int amount, boolean shouldDrain) {
		return drain(EnumFacing.NORTH, amount, shouldDrain);
	}

	FluidStack drain(EnumFacing dir, FluidStack stack, boolean shouldDrain);
	
	default FluidStack drain(FluidStack stack, boolean shouldDrain) {
		return drain(EnumFacing.NORTH, stack, shouldDrain);
	}

}
