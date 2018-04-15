package growthcraft.cellar.common.lib.processing.fermenting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.core.shared.item.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FermentingRegistry
{
	private List<IFermentationRecipe> recipes = new ArrayList<IFermentationRecipe>();

	private void onRecipeAdded(@Nonnull IFermentationRecipe recipe)
	{
		GrowthcraftCellar.logger.debug("Added Fermentation recipe={%s}", recipe);
	}

	public void addRecipe(@Nonnull IFermentationRecipe recipe)
	{
		recipes.add(recipe);
		onRecipeAdded(recipe);
	}

	public void addRecipe(@Nonnull FluidStack result, @Nonnull Object booze, @Nonnull Object fermenter, int time)
	{
		addRecipe(new FermentationRecipe(MultiStacksUtil.toMultiFluidStacks(booze), MultiStacksUtil.toMultiItemStacks(fermenter), result, time));
	}

	public IFermentationRecipe findRecipe(@Nullable FluidStack booze, @Nullable ItemStack fermenter)
	{
		if (booze == null || fermenter == null) return null;
		for (IFermentationRecipe recipe : recipes)
		{
			if (recipe.matchesRecipe(booze, fermenter)) return recipe;
		}
		return null;
	}

	public List<IFermentationRecipe> findRecipes(@Nullable FluidStack fluid)
	{
		final List<IFermentationRecipe> result = new ArrayList<IFermentationRecipe>();
		if (fluid != null)
		{
			for (IFermentationRecipe recipe : recipes)
			{
				if (recipe.matchesIngredient(fluid))
					result.add(recipe);
			}
		}
		return result;
	}

	public List<IFermentationRecipe> findRecipes(@Nullable ItemStack fermenter)
	{
		final List<IFermentationRecipe> result = new ArrayList<IFermentationRecipe>();
		if (fermenter != null)
		{
			for (IFermentationRecipe recipe : recipes)
			{
				if (recipe.matchesIngredient(fermenter))
					result.add(recipe);
			}
		}
		return result;
	}

	public boolean canFerment(@Nullable FluidStack fluid)
	{
		final List<IFermentationRecipe> result = new ArrayList<IFermentationRecipe>();
		if (fluid != null)
		{
			for (IFermentationRecipe recipe : recipes)
			{
				if (recipe.matchesIngredient(fluid))
					return true;
			}
		}
		return false;
	}
}