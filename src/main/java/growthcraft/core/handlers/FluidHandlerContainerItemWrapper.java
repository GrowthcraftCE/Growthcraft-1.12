package growthcraft.core.handlers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.common.item.IFluidItem;
import growthcraft.core.lib.legacy.FluidContainerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluidHandlerContainerItemWrapper implements IFluidHandlerItem, ICapabilityProvider {

    @Nonnull
    protected ItemStack container;
    
    public FluidHandlerContainerItemWrapper(ItemStack container) {
    	this.container = container;
    }
    
    @Nullable
    public FluidStack getFluidStack() {
    	FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(container);
    	if( fluidStack != null )
    		return fluidStack;

    	Item item = container.getItem();
    	if( item instanceof IFluidItem ) {
    		return ((IFluidItem)item).getFluidStack(container);
    	}
    	else
    		return null;
    }
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		FluidStack fluidStack = getFluidStack();
		if( fluidStack == null )
			return new FluidTankProperties[] {};
		return new FluidTankProperties[] { new FluidTankProperties( fluidStack, fluidStack.amount ) };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (container.getCount() != 1 || resource == null )
			return 0;
		ItemStack newContainer = FluidContainerRegistry.fillFluidContainer(resource, container);
		if( newContainer == null )
			return 0;
		
		if( doFill )
			container = newContainer;
		return FluidContainerRegistry.getContainerCapacity(newContainer);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		FluidStack fluidStack = getFluidStack();
		if( fluidStack == null )
			return null;

		if (container.isEmpty() || resource == null || resource.amount < fluidStack.amount )
        {
            return null;
        }
		
		if( fluidStack.isFluidEqual(resource) ) {
			if( doDrain )
			{
				container = FluidContainerRegistry.drainFluidContainer(container);
			}
			return fluidStack;
		}
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		FluidStack fluidStack = getFluidStack();
		if( fluidStack == null )
			return null;
		
		fluidStack = fluidStack.copy();
		fluidStack.amount = Math.min(maxDrain, fluidStack.amount);
		return drain(fluidStack, doDrain);
	}

	@Override
	public ItemStack getContainer() {
		return container;
	}
	
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(this);
        }
        return null;
    }
}
