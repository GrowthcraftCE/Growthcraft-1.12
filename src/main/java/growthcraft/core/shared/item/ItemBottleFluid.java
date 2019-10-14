package growthcraft.core.shared.item;

import javax.annotation.Nonnull;

import growthcraft.core.shared.handlers.FluidHandlerContainerItemWrapper;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Generic fluid bottle for growthcraft fluids
 */
public class ItemBottleFluid extends GrowthcraftItemBase implements IFluidContainerItem, IItemColored {
    private Fluid fluid;
    // Used to override the fluid color
    private int color = -1;

    public ItemBottleFluid(Fluid flu) {
        super();
        setContainerItem(Items.GLASS_BOTTLE);
        this.fluid = flu;
    }

    @Override
    public FluidStack getFluidStack(ItemStack stack) {
        return new FluidStack(fluid, FluidContainerRegistry.BOTTLE_VOLUME);
    }

    public ItemBottleFluid setColor(int c) {
        this.color = c;
        return this;
    }

    @Override
    public int getColor(ItemStack stack) {
        if (color != -1) return color;
        return fluid.getColor();
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, NBTTagCompound nbt) {
        return new FluidHandlerContainerItemWrapper(stack);
    }
}