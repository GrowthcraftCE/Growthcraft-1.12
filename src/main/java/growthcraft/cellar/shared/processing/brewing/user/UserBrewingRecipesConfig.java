package growthcraft.cellar.shared.processing.brewing.user;

import java.io.BufferedReader;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.cellar.shared.config.schema.ResidueSchema;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.config.schema.FluidStackSchema;
import growthcraft.core.shared.config.schema.ItemKeySchema;
import growthcraft.core.shared.GrowthcraftLogger;
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
		GrowthcraftLogger.getLogger(Reference.MODID).debug("Added new default brewing recipe={%s}", recipe);
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
			GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid recipe");
			return;
		}

		if (recipe.item == null || recipe.item.isInvalid())
		{
			GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid item for recipe {%s}", recipe);
			return;
		}

		if (recipe.input_fluid == null || recipe.input_fluid.isInvalid())
		{
			GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid input_fluid {%s}", recipe);
			return;
		}

		if (recipe.output_fluid == null || recipe.output_fluid.isInvalid())
		{
			GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid output_fluid {%s}", recipe);
			return;
		}

		Residue residue = null;
		if (recipe.residue != null)
		{
			residue = recipe.residue.asResidue();
			if (residue == null)
			{
				GrowthcraftLogger.getLogger(Reference.MODID).error("Not a valid residue found for {%s}", recipe);
				return;
			}
		}


		final FluidStack inputFluidStack = recipe.input_fluid.asFluidStack();
		final FluidStack outputFluidStack = recipe.output_fluid.asFluidStack();

		GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding user brewing recipe {%s}", recipe);
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
				GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding %d user brewing recipes.", recipes.data.size());
				for (UserBrewingRecipe recipe : recipes.data) addBrewingRecipe(recipe);
			}
			else
			{
				GrowthcraftLogger.getLogger(Reference.MODID).error("Recipes data is invalid!");
			}
		}
	}
}
