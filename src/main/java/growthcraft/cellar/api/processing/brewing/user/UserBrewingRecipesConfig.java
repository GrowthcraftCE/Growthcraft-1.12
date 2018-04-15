package growthcraft.cellar.api.processing.brewing.user;

import java.io.BufferedReader;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.api.processing.common.Residue;
import growthcraft.cellar.api.schema.ResidueSchema;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.config.schema.FluidStackSchema;
import growthcraft.core.shared.config.schema.ItemKeySchema;
import growthcraft.core.shared.config.AbstractUserJSONConfig;
import net.minecraftforge.fluids.FluidStack;

/**
 * This allows users to define new brewing recipes.
 */
public class UserBrewingRecipesConfig extends AbstractUserJSONConfig
{
	private final UserBrewingRecipes defaultRecipes = new UserBrewingRecipes();
	private UserBrewingRecipes recipes;

	public void addDefault(UserBrewingRecipe recipe)
	{
		defaultRecipes.data.add(recipe);
		GrowthcraftCellar.logger.debug("Added new default brewing recipe={%s}", recipe);
	}

	public void addDefault(Object stack, FluidStack inp, FluidStack out, Residue residue, int time)
	{
		for (ItemKeySchema itemKey : ItemKeySchema.createMulti(stack))
		{
			addDefault(
				new UserBrewingRecipe(
					itemKey,
					new FluidStackSchema(inp),
					new FluidStackSchema(out),
					residue == null ? null : new ResidueSchema(residue),
					time
				)
			);
		}
	}

	@Override
	protected String getDefault()
	{
		return gson.toJson(defaultRecipes);
	}

	@Override
	protected void loadFromBuffer(BufferedReader reader) throws IllegalStateException
	{
		this.recipes = gson.fromJson(reader, UserBrewingRecipes.class);
	}

	private void addBrewingRecipe(UserBrewingRecipe recipe)
	{
		if (recipe == null)
		{
			GrowthcraftCellar.logger.error("Invalid recipe");
			return;
		}

		if (recipe.item == null || recipe.item.isInvalid())
		{
			GrowthcraftCellar.logger.error("Invalid item for recipe {%s}", recipe);
			return;
		}

		if (recipe.input_fluid == null || recipe.input_fluid.isInvalid())
		{
			GrowthcraftCellar.logger.error("Invalid input_fluid {%s}", recipe);
			return;
		}

		if (recipe.output_fluid == null || recipe.output_fluid.isInvalid())
		{
			GrowthcraftCellar.logger.error("Invalid output_fluid {%s}", recipe);
			return;
		}

		Residue residue = null;
		if (recipe.residue != null)
		{
			residue = recipe.residue.asResidue();
			if (residue == null)
			{
				GrowthcraftCellar.logger.error("Not a valid residue found for {%s}", recipe);
				return;
			}
		}


		final FluidStack inputFluidStack = recipe.input_fluid.asFluidStack();
		final FluidStack outputFluidStack = recipe.output_fluid.asFluidStack();

		GrowthcraftCellar.logger.debug("Adding user brewing recipe {%s}", recipe);
		for (IMultiItemStacks item : recipe.item.getMultiItemStacks())
		{
			CellarRegistry.instance().brewing().addRecipe(inputFluidStack, item, outputFluidStack, recipe.time, residue);
		}
	}

	@Override
	public void postInit()
	{
		if (recipes != null)
		{
			if (recipes.data != null)
			{
				GrowthcraftCellar.logger.debug("Adding %d user brewing recipes.", recipes.data.size());
				for (UserBrewingRecipe recipe : recipes.data) addBrewingRecipe(recipe);
			}
			else
			{
				GrowthcraftCellar.logger.error("Recipes data is invalid!");
			}
		}
	}
}
