package growthcraft.cellar.api.processing.pressing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.api.processing.common.Residue;
import growthcraft.core.api.utils.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PressingRegistry
{
	private List<PressingRecipe> recipes = new ArrayList<PressingRecipe>();

	public void addRecipe(@Nonnull PressingRecipe recipe)
	{
		recipes.add(recipe);
		GrowthcraftCellar.logger.debug("Added new Pressing Recipe recipe={%s}", recipe);
	}

	public void addRecipe(@Nonnull Object inputStack, @Nonnull FluidStack resultFluid, int time, @Nullable Residue residue)
	{
		addRecipe(new PressingRecipe(MultiStacksUtil.toMultiItemStacks(inputStack), resultFluid, time, residue));
	}

	public PressingRecipe getPressingRecipe(ItemStack itemstack)
	{
		if (itemstack == null) return null;

		for (PressingRecipe recipe : recipes)
		{
			if (recipe.matchesRecipe(itemstack)) return recipe;
		}
		return null;
	}

	public boolean hasPressingRecipe(ItemStack itemstack)
	{
		return getPressingRecipe(itemstack) != null;
	}
}
