package growthcraft.cellar.api.processing.culturing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.GrowthcraftCellar;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CulturingRegistry
{
	private List<CultureRecipe> recipes = new ArrayList<CultureRecipe>();

	public void addRecipe(@Nonnull CultureRecipe recipe)
	{
		recipes.add(recipe);
		GrowthcraftCellar.logger.debug("Adding new Culturing Recipe, {%s}.", recipe);
	}

	public void addRecipe(@Nonnull FluidStack fluidStack, @Nonnull ItemStack itemStack, float requiredHeat, int time)
	{
		addRecipe(new CultureRecipe(fluidStack, itemStack, requiredHeat, time));
	}

	public CultureRecipe findRecipe(@Nullable FluidStack fluid, float heat)
	{
		for (CultureRecipe recipe : recipes)
		{
			if (recipe.matchesRecipe(fluid, heat)) return recipe;
		}
		return null;
	}
}
