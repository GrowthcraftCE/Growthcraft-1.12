package growthcraft.cellar.shared.processing.culturing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.GrowthcraftLogger;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CulturingRegistry
{
	private List<ICultureRecipe> recipes = new ArrayList<ICultureRecipe>();

	public void addRecipe(@Nonnull ICultureRecipe recipe)
	{
		recipes.add(recipe);
		GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding new Culturing Recipe, {%s}.", recipe);
	}

	public void addRecipe(@Nonnull FluidStack fluidStack, @Nonnull ItemStack itemStack, float requiredHeat, int time)
	{
		addRecipe(new CultureRecipe(fluidStack, itemStack, requiredHeat, time));
	}

	public ICultureRecipe findRecipe(@Nullable FluidStack fluid, float heat)
	{
		for (ICultureRecipe recipe : recipes)
		{
			if (recipe.matchesRecipe(fluid, heat)) return recipe;
		}
		return null;
	}
}
