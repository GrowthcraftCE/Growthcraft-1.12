package growthcraft.cellar.api.processing.fermenting.user;

import java.io.BufferedReader;

import javax.annotation.Nonnull;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.api.CellarRegistry;
import growthcraft.core.api.definition.IMultiFluidStacks;
import growthcraft.core.api.definition.IMultiItemStacks;
import growthcraft.core.api.schema.FluidStackSchema;
import growthcraft.core.api.schema.ItemKeySchema;
import growthcraft.core.api.schema.MultiFluidStackSchema;
import growthcraft.core.api.user.AbstractUserJSONConfig;
import growthcraft.core.api.utils.MultiStacksUtil;
import net.minecraftforge.fluids.FluidStack;

public class UserFermentingRecipesConfig extends AbstractUserJSONConfig
{
	protected UserFermentingRecipes defaultRecipes = new UserFermentingRecipes();
	protected UserFermentingRecipes recipes;

	public void addDefaultSchemas(@Nonnull ItemKeySchema item, @Nonnull MultiFluidStackSchema inputFluid, @Nonnull FluidStackSchema outputFluid, int time)
	{
		addDefault(new UserFermentingRecipe(item, inputFluid, outputFluid, time));
	}

	public void addDefault(@Nonnull UserFermentingRecipe recipe)
	{
		if (recipe != null)
		{
			defaultRecipes.data.add(recipe);
			GrowthcraftCellar.logger.debug("Adding default user fermenting recipe {%s}", recipe);
		}
		else
		{
			GrowthcraftCellar.logger.error("We have a problem here, someone tossed a null user recipe at us!");
			throw new IllegalArgumentException("Oh no you didn't, FIX DAT.");
		}
	}

	/**
	 * @param stack - any ItemStack or IMultiItemStack
	 * @param inputFluid - any FluidStack or IMultiFluidStack
	 * @return
	 */
	public void addDefault(@Nonnull Object stack, @Nonnull Object inputFluid, @Nonnull FluidStack outputFluid, int time)
	{
		for (ItemKeySchema itemKey : ItemKeySchema.createMulti(stack))
		{
			addDefaultSchemas(
				itemKey,
				new MultiFluidStackSchema(MultiStacksUtil.toMultiFluidStacks(inputFluid)),
				new FluidStackSchema(outputFluid),
				time
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
		this.recipes = gson.fromJson(reader, UserFermentingRecipes.class);
	}

	private void addRecipe(UserFermentingRecipe recipe)
	{
		if (recipe == null)
		{
			GrowthcraftCellar.logger.error("Recipe is invalid!");
			return;
		}

		if (recipe.item == null || !recipe.item.isValid())
		{
			GrowthcraftCellar.logger.error("Recipe item is invalid! {%s}", recipe);
			return;
		}

		if (recipe.input_fluid == null || !recipe.input_fluid.isValid())
		{
			GrowthcraftCellar.logger.error("Recipe input_fluid is invalid! {%s}", recipe);
			return;
		}

		if (recipe.output_fluid == null || !recipe.output_fluid.isValid())
		{
			GrowthcraftCellar.logger.error("Recipe output_fluid is invalid! {%s}", recipe);
			return;
		}

		GrowthcraftCellar.logger.debug("Adding Fermenting Recipe {%s}", recipe);
		for (IMultiItemStacks item : recipe.item.getMultiItemStacks())
		{
			for (IMultiFluidStacks inputFluid : recipe.input_fluid.getMultiFluidStacks())
			{
				CellarRegistry.instance().fermenting().addRecipe(
					recipe.output_fluid.asFluidStack(),
					inputFluid,
					item,
					recipe.time
				);
			}
		}
	}

	@Override
	public void postInit()
	{
		if (recipes != null)
		{
			if (recipes.data != null)
			{
				GrowthcraftCellar.logger.debug("Registering %d fermenting recipes.", recipes.data.size());
				for (UserFermentingRecipe recipe : recipes.data) addRecipe(recipe);
			}
			else
			{
				GrowthcraftCellar.logger.error("Fermenting Recipes data is invalid!");
			}
		}
	}
}
