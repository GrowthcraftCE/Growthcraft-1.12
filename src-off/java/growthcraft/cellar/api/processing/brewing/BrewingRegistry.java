package growthcraft.cellar.api.processing.brewing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.api.processing.common.Residue;
import growthcraft.core.api.utils.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BrewingRegistry
{
	private List<IBrewingRecipe> recipes = new ArrayList<IBrewingRecipe>();

	public void addRecipe(@Nonnull IBrewingRecipe recipe)
	{
		recipes.add(recipe);
		GrowthcraftCellar.logger.debug("Added Brewing Recipe recipe={%s}", recipe);
	}

	public void addRecipe(@Nonnull FluidStack sourceFluid, @Nonnull Object raw, @Nonnull FluidStack resultFluid, int time, @Nullable Residue residue)
	{
		addRecipe(new BrewingRecipe(sourceFluid, MultiStacksUtil.toMultiItemStacks(raw), resultFluid, time, residue));
	}

	public IBrewingRecipe findRecipe(@Nullable FluidStack fluidstack, @Nullable ItemStack itemstack)
	{
		if (itemstack == null || fluidstack == null) return null;

		for (IBrewingRecipe recipe : recipes)
		{
			if (recipe.matchesRecipe(fluidstack, itemstack)) return recipe;
		}
		return null;
	}

	public List<IBrewingRecipe> findRecipes(@Nullable FluidStack fluid)
	{
		final List<IBrewingRecipe> result = new ArrayList<IBrewingRecipe>();
		if (fluid != null)
		{
			for (IBrewingRecipe recipe : recipes)
			{
				if (recipe.matchesIngredient(fluid))
					result.add(recipe);
			}
		}
		return result;
	}

	public List<IBrewingRecipe> findRecipes(@Nullable ItemStack fermenter)
	{
		final List<IBrewingRecipe> result = new ArrayList<IBrewingRecipe>();
		if (fermenter != null)
		{
			for (IBrewingRecipe recipe : recipes)
			{
				if (recipe.matchesIngredient(fermenter))
					result.add(recipe);
			}
		}
		return result;
	}

	public boolean isBrewingRecipe(@Nullable FluidStack fluidstack, @Nullable ItemStack itemstack)
	{
		return findRecipe(fluidstack, itemstack) != null;
	}

	public boolean isItemBrewingIngredient(@Nullable ItemStack itemstack)
	{
		if (itemstack == null) return false;

		for (IBrewingRecipe recipe : recipes)
		{
			if (recipe.isItemIngredient(itemstack)) return true;
		}
		return false;
	}
}
