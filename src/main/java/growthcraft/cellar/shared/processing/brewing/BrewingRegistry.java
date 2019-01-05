package growthcraft.cellar.shared.processing.brewing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.item.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BrewingRegistry
{
	private List<IBrewingRecipe> recipes = new ArrayList<IBrewingRecipe>();

	public void addRecipe(@Nonnull IBrewingRecipe recipe)
	{
		// TODO: Warn if multiple fallback recipes exist for same input fluid
		
		recipes.add(recipe);
		GrowthcraftLogger.getLogger(Reference.MODID).debug("Added Brewing Recipe recipe={%s}", recipe);
	}

	public void addRecipe(@Nonnull FluidStack sourceFluid, @Nonnull Object raw, @Nonnull FluidStack resultFluid, boolean requiresLid, int time, @Nullable Residue residue)
	{
		addRecipe(new BrewingRecipe(sourceFluid, MultiStacksUtil.toMultiItemStacks(raw), resultFluid, requiresLid, time, residue));
	}
	
	public void addFallbackRecipe(@Nonnull FluidStack sourceFluid, @Nonnull FluidStack resultFluid, int time, @Nullable Residue residue)
	{
		addRecipe(new BrewingFallbackRecipe(sourceFluid, resultFluid, time, residue));
	}

	public IBrewingRecipe findRecipe(@Nullable FluidStack fluidstack, @Nullable ItemStack itemstack, boolean requiresLid)
	{
		if (itemstack == null || fluidstack == null) return null;

		for (IBrewingRecipe recipe : recipes)
		{
			if (isFallbackRecipe(recipe))
				continue;
			if (recipe.matchesRecipe(fluidstack, itemstack, requiresLid)) return recipe;
		}
		
		for (IBrewingRecipe recipe : recipes)
		{
			if (!isFallbackRecipe(recipe))
				continue;
			if (recipe.matchesIngredient(fluidstack)) return recipe;
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
				if (isFallbackRecipe(recipe) || recipe.matchesIngredient(fermenter))
					result.add(recipe);
			}
		}
		return result;
	}

	public boolean isBrewingRecipe(@Nullable FluidStack fluidstack, @Nullable ItemStack itemstack, boolean requiresLid)
	{
		return findRecipe(fluidstack, itemstack, requiresLid) != null;
	}

	public boolean isItemBrewingIngredient(@Nullable ItemStack itemstack)
	{
		if (itemstack == null) return false;

		for (IBrewingRecipe recipe : recipes)
		{
			if (isFallbackRecipe(recipe) )	// Ignore fallback recipes
				continue;

			if (recipe.isItemIngredient(itemstack)) return true;
		}
		return false;
	}
	
	public boolean isFallbackRecipe(IBrewingRecipe recipe) {
		return (recipe instanceof BrewingFallbackRecipe) && !(recipe instanceof BrewingRecipe);
	}
}
