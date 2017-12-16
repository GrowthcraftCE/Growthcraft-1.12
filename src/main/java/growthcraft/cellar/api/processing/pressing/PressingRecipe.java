package growthcraft.cellar.api.processing.pressing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.api.processing.common.ProcessingRecipe;
import growthcraft.cellar.api.processing.common.Residue;
import growthcraft.core.api.definition.IMultiItemStacks;
import growthcraft.core.api.item.ItemTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PressingRecipe extends ProcessingRecipe
{
	private IMultiItemStacks inputItemStack;

	public PressingRecipe(@Nonnull IMultiItemStacks src, @Nonnull FluidStack f, int t, @Nullable Residue r)
	{
		super(f, t, r);
		this.inputItemStack = src;
	}

	public IMultiItemStacks getInput()
	{
		return inputItemStack;
	}

	public boolean matchesRecipe(@Nullable ItemStack itemStack)
	{
		if (itemStack != null)
		{
			if (!ItemTest.hasEnough(inputItemStack, itemStack)) return false;
			return true;
		}
		return false;
	}
}
