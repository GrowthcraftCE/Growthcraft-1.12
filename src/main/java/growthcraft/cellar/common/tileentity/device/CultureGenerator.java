package growthcraft.cellar.common.tileentity.device;

import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.api.processing.culturing.CultureRecipe;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.cellar.common.tileentity.component.TileHeatingComponent;
import growthcraft.core.common.tileentity.device.DeviceFluidSlot;
import growthcraft.core.common.tileentity.device.DeviceInventorySlot;
import growthcraft.core.common.tileentity.device.DeviceProgressive;

public class CultureGenerator extends DeviceProgressive
{
	protected DeviceFluidSlot fluidSlot;
	protected DeviceInventorySlot invSlot;
	protected TileHeatingComponent heatComponent;

	/**
	 * @param te - parent tile entity
	 * @param fluidSlotIndex - fluid slot id to use in parent
	 *             Fluid will be used from this slot
	 * @param invSlotIndex - inventory slot id to use in parent
	 *             Culture will be generated into this slot
	 */
	public CultureGenerator(TileEntityCellarDevice te, TileHeatingComponent heatComp, int fluidSlotIndex, int invSlotIndex)
	{
		super(te);
		this.heatComponent = heatComp;
		this.fluidSlot = new DeviceFluidSlot(te, fluidSlotIndex);
		this.invSlot = new DeviceInventorySlot(te, invSlotIndex);
		setTimeMax(1200);
	}

	public float getHeatMultiplier()
	{
		return heatComponent.getHeatMultiplier();
	}

	@Override
	public void increaseTime()
	{
		this.time += 1;
	}

	public boolean isHeated()
	{
		return heatComponent.isHeated();
	}

	private boolean isRecipeValid(CultureRecipe recipe)
	{
		if (recipe != null)
		{
			if (fluidSlot.hasEnough(recipe.getInputFluidStack()))
			{
				return invSlot.isEmpty() || invSlot.hasMatchingWithCapacity(recipe.getOutputItemStack());
			}
		}
		return false;
	}

	private void produceCulture(CultureRecipe recipe)
	{
		fluidSlot.consume(recipe.getInputFluidStack(), true);
		invSlot.increaseStack(recipe.getOutputItemStack());
	}

	@Override
	public void update()
	{
		final CultureRecipe activeRecipe = CellarRegistry.instance().culturing().findRecipe(fluidSlot.get(), heatComponent.getHeatMultiplier());

		if (isRecipeValid(activeRecipe))
		{
			setTimeMax(activeRecipe.getTime());
			increaseTime();
			if (time >= timeMax)
			{
				resetTime();
				produceCulture(activeRecipe);
				markDirty();
			}
		}
		else
		{
			if (resetTime()) markDirty();
		}
	}
}
