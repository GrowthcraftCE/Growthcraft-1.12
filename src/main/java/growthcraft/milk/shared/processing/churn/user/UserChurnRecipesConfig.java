package growthcraft.milk.shared.processing.churn.user;

import java.io.BufferedReader;

import growthcraft.core.shared.config.AbstractUserJSONConfig;
import growthcraft.core.shared.config.schema.FluidStackSchema;
import growthcraft.core.shared.config.schema.ItemKeySchema;
import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.processing.churn.IChurnRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * This allows users to define new brewing recipes.
 */
public class UserChurnRecipesConfig extends AbstractUserJSONConfig
{
	private final UserChurnRecipes defaultRecipes = new UserChurnRecipes();
	private UserChurnRecipes recipes;

	public void addDefault(UserChurnRecipe recipe)
	{
		defaultRecipes.data.add(recipe);
	}

	public void addDefault(FluidStack inp, FluidStack out, ItemStack stack, int churns)
	{
		addDefault(
			new UserChurnRecipe(
				new FluidStackSchema(inp),
				new FluidStackSchema(out),
				new ItemKeySchema(stack),
				churns
			)
		);
	}

	@Override
	protected String getDefault()
	{
		return gson.toJson(defaultRecipes);
	}

	@Override
	protected void loadFromBuffer(BufferedReader reader) throws IllegalStateException
	{
		this.recipes = gson.fromJson(reader, UserChurnRecipes.class);
	}

	private void addChurnRecipe(UserChurnRecipe recipe)
	{
		if (recipe == null)
		{
			GrowthcraftMilk.logger.error("Invalid recipe");
			return;
		}

		if (recipe.input_fluid == null || recipe.input_fluid.isInvalid())
		{
			GrowthcraftMilk.logger.error("Invalid input_fluid {%s}", recipe);
			return;
		}

		for (IChurnRecipe churnRecipe : recipe.toChurnRecipes())
		{
			GrowthcraftMilk.logger.debug("Adding user churn recipe {%s}", churnRecipe);
			MilkRegistry.instance().churn().addRecipe(churnRecipe);
		}
	}

	@Override
	public void postInit()
	{
		if (recipes != null)
		{
			if (recipes.data != null)
			{
				GrowthcraftMilk.logger.debug("Adding %d user brewing recipes.", recipes.data.size());
				for (UserChurnRecipe recipe : recipes.data) addChurnRecipe(recipe);
			}
			else
			{
				GrowthcraftMilk.logger.error("Recipes data is invalid!");
			}
		}
	}
}
