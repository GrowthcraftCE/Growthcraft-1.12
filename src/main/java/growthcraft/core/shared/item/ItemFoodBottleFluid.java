package growthcraft.core.shared.item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.handlers.FluidHandlerContainerItemWrapper;
import growthcraft.core.shared.item.IFluidContainerItem;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Generic fluid bottle for growthcraft fluids that are edible
 */
public class ItemFoodBottleFluid extends GrowthcraftItemFoodBase implements IFluidContainerItem, IItemColored
{
	private Fluid defaultFluid;
	// Used to override the fluid color
	private int color = -1;

	public ItemFoodBottleFluid(@Nullable Fluid defaultFluid, int healAmount, float saturation, boolean isWolfFavouriteFood)
	{
		super(healAmount, saturation, isWolfFavouriteFood);
		setItemUseAction(EnumAction.DRINK);
		setContainerItem(Items.GLASS_BOTTLE);
		if( defaultFluid == null )
			defaultFluid = FluidRegistry.WATER;
		this.defaultFluid = defaultFluid;
	}

	public ItemFoodBottleFluid(@Nullable Fluid defaultFluid, int healAmount, float saturation)
	{
		this(defaultFluid, healAmount, saturation, false);
	}

	public ItemFoodBottleFluid(@Nullable Fluid defaultFluid, int healAmount)
	{
		this(defaultFluid, healAmount, 0.0f);
	}

	public ItemFoodBottleFluid(@Nullable Fluid defaultFluid)
	{
		this(defaultFluid, 0);
	}

	@Override
	public FluidStack getFluidStack(ItemStack stack)
	{
		return new FluidStack(defaultFluid, FluidContainerRegistry.BOTTLE_VOLUME);
	}

	public ItemFoodBottleFluid setColor(int c)
	{
		this.color = c;
		return this;
	}

	@Override
	public int getColor(ItemStack stack)
	{
		if (color != -1) return color;
		return defaultFluid.getColor();
	}
	
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, NBTTagCompound nbt)
    {
        return new FluidHandlerContainerItemWrapper(stack);
    }
}
