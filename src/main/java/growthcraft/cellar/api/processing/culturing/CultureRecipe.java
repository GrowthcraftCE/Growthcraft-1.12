package growthcraft.cellar.api.processing.culturing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.fluids.FluidTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CultureRecipe implements ICultureRecipe
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

	@Override
	public ItemStack getOutputItemStack()
	{
		return outputItemStack;
	}

	@Override
	public FluidStack getInputFluidStack()
	{
		return inputFluidStack;
	}

	@Override
	public float getRequiredHeat()
	{
		return requiredHeat;
	}

	@Override
	public int getTime()
	{
		return time;
	}

	@Override
	public boolean matchesRecipe(@Nullable FluidStack fluid, float heat)
	{
		if (FluidTest.hasEnough(inputFluidStack, fluid))
		{
			return heat >= requiredHeat;
		}
		return false;
	}
}
