package growthcraft.cellar.api.processing.culturing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.api.fluids.FluidTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CultureRecipe
{
	private FluidStack inputFluidStack;
	private ItemStack outputItemStack;
	private float requiredHeat;
	private int time;

	public CultureRecipe(@Nonnull FluidStack pInputFluidStack, @Nonnull ItemStack pOutputItemStack, float pRequiredHeat, int pTime)
	{
		this.inputFluidStack = pInputFluidStack;
		this.outputItemStack = pOutputItemStack;
		this.requiredHeat = pRequiredHeat;
		this.time = pTime;
	}

	public ItemStack getOutputItemStack()
	{
		return outputItemStack;
	}

	public FluidStack getInputFluidStack()
	{
		return inputFluidStack;
	}

	public float getRequiredHeat()
	{
		return requiredHeat;
	}

	public int getTime()
	{
		return time;
	}

	public boolean matchesRecipe(@Nullable FluidStack fluid, float heat)
	{
		if (FluidTest.hasEnough(inputFluidStack, fluid))
		{
			return heat >= requiredHeat;
		}
		return false;
	}
}
