package growthcraft.milk.api.processing.cheesepress;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.api.item.ItemTest;
import growthcraft.milk.GrowthcraftMilk;
import net.minecraft.item.ItemStack;

public class CheesePressRegistry
{
	private List<CheesePressRecipe> recipes = new ArrayList<CheesePressRecipe>();

	public void addRecipe(@Nonnull CheesePressRecipe recipe)
	{
		GrowthcraftMilk.logger.debug("Adding new cheese press recipe {%s}", recipe);
		recipes.add(recipe);
	}

	public void addRecipe(@Nonnull ItemStack stack, @Nonnull ItemStack output, int time)
	{
		addRecipe(new CheesePressRecipe(stack, output, time));
	}

	public CheesePressRecipe findRecipe(@Nullable ItemStack stack)
	{
		if (ItemTest.isValid(stack))
		{
			for (CheesePressRecipe recipe : recipes)
			{
				if (recipe.isMatchingRecipe(stack)) return recipe;
			}
		}
		return null;
	}
}
