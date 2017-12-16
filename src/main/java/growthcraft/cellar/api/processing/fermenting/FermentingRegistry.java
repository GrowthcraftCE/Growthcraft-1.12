package growthcraft.cellar.api.processing.fermenting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.core.api.utils.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FermentingRegistry
{
	private List<FermentationRecipe> recipes = new ArrayList<FermentationRecipe>();

	private void onRecipeAdded(@Nonnull FermentationRecipe recipe)
	{
		GrowthcraftCellar.logger.debug("Added Fermentation recipe={%s}", recipe);
	}

	public void addRecipe(@Nonnull FermentationRecipe recipe)
	{
		recipes.add(recipe);
		onRecipeAdded(recipe);
	}

	public void addRecipe(@Nonnull FluidStack result, @Nonnull Object booze, @Nonnull Object fermenter, int time)
	{
		addRecipe(new FermentationRecipe(MultiStacksUtil.toMultiFluidStacks(booze), MultiStacksUtil.toMultiItemStacks(fermenter), result, time));
	}

	public FermentationRecipe findRecipe(@Nullable FluidStack booze, @Nullable ItemStack fermenter)
	{
		if (booze == null || fermenter == null) return null;
		for (FermentationRecipe recipe : recipes)
		{
			if (recipe.matchesRecipe(booze, fermenter)) return recipe;
		}
		return null;
	}

	public List<FermentationRecipe> findRecipes(@Nullable FluidStack fluid)
	{
		final List<FermentationRecipe> result = new ArrayList<FermentationRecipe>();
		if (fluid != null)
		{
			for (FermentationRecipe recipe : recipes)
			{
				if (recipe.matchesIngredient(fluid))
					result.add(recipe);
			}
		}
		return result;
	}

	public List<FermentationRecipe> findRecipes(@Nullable ItemStack fermenter)
	{
		final List<FermentationRecipe> result = new ArrayList<FermentationRecipe>();
		if (fermenter != null)
		{
			for (FermentationRecipe recipe : recipes)
			{
				if (recipe.matchesIngredient(fermenter))
					result.add(recipe);
			}
		}
		return result;
	}

	public boolean canFerment(@Nullable FluidStack fluid)
	{
		final List<FermentationRecipe> result = new ArrayList<FermentationRecipe>();
		if (fluid != null)
		{
			for (FermentationRecipe recipe : recipes)
			{
				if (recipe.matchesIngredient(fluid))
					return true;
			}
		}
		return false;
	}
}