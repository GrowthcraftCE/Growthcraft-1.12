package growthcraft.cellar.shared.processing.fermenting.user;

import java.io.BufferedReader;

import javax.annotation.Nonnull;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.config.schema.FluidStackSchema;
import growthcraft.core.shared.config.schema.ItemKeySchema;
import growthcraft.core.shared.config.schema.MultiFluidStackSchema;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.config.AbstractUserJSONConfig;
import growthcraft.core.shared.item.MultiStacksUtil;
import net.minecraftforge.fluids.FluidStack;

public class UserFermentingRecipesConfig extends AbstractUserJSONConfig {
    protected UserFermentingRecipes defaultRecipes = new UserFermentingRecipes();
    protected UserFermentingRecipes recipes;

    public void addDefaultSchemas(ItemKeySchema item, @Nonnull MultiFluidStackSchema inputFluid, @Nonnull FluidStackSchema outputFluid, int time) {
        addDefault(new UserFermentingRecipe(item, inputFluid, outputFluid, time));
    }

    public void addDefault(@Nonnull UserFermentingRecipe recipe) {
        if (recipe != null) {
            defaultRecipes.data.add(recipe);
            GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding default user fermenting recipe {%s}", recipe);
        } else {
            GrowthcraftLogger.getLogger(Reference.MODID).error("We have a problem here, someone tossed a null user recipe at us!");
            throw new IllegalArgumentException("Oh no you didn't, FIX DAT.");
        }
    }

    /**
     * @param stack      - any ItemStack or IMultiItemStack
     * @param inputFluid - any FluidStack or IMultiFluidStack
     * @return
     */
    public void addDefault(@Nonnull Object stack, @Nonnull Object inputFluid, @Nonnull FluidStack outputFluid, int time) {
        for (ItemKeySchema itemKey : ItemKeySchema.createMulti(stack)) {
            addDefaultSchemas(
                    itemKey,
                    new MultiFluidStackSchema(MultiStacksUtil.toMultiFluidStacks(inputFluid)),
                    new FluidStackSchema(outputFluid),
                    time
            );
        }
    }

    public void addFallbackDefault(@Nonnull Object inputFluid, @Nonnull FluidStack outputFluid, int time) {
        addDefaultSchemas(
                null,
                new MultiFluidStackSchema(MultiStacksUtil.toMultiFluidStacks(inputFluid)),
                new FluidStackSchema(outputFluid),
                time
        );
    }


    @Override
    protected String getDefault() {
        return gson.toJson(defaultRecipes);
    }

    @Override
    protected void loadFromBuffer(BufferedReader reader) throws IllegalStateException {
        this.recipes = gson.fromJson(reader, UserFermentingRecipes.class);
    }

    private void addRecipe(UserFermentingRecipe recipe) {
        if (recipe == null) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Recipe is invalid!");
            return;
        }

        if (recipe.item != null && !recipe.item.isValid()) {
            // NOTE: item == null means it is a fallback case
            GrowthcraftLogger.getLogger(Reference.MODID).error("Recipe item is invalid! {%s}", recipe);
            return;
        }

        if (recipe.input_fluid == null || !recipe.input_fluid.isValid()) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Recipe input_fluid is invalid! {%s}", recipe);
            return;
        }

        if (recipe.output_fluid == null || !recipe.output_fluid.isValid()) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Recipe output_fluid is invalid! {%s}", recipe);
            return;
        }

        GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding Fermenting Recipe {%s}", recipe);
        if (recipe.item != null) {
            for (IMultiItemStacks item : recipe.item.getMultiItemStacks()) {
                for (IMultiFluidStacks inputFluid : recipe.input_fluid.getMultiFluidStacks()) {
                    CellarRegistry.instance().fermenting().addRecipe(
                            recipe.output_fluid.asFluidStack(),
                            inputFluid,
                            item,
                            recipe.time
                    );
                }
            }
        } else {
            for (IMultiFluidStacks inputFluid : recipe.input_fluid.getMultiFluidStacks()) {
                CellarRegistry.instance().fermenting().addFallbackRecipe(
                        recipe.output_fluid.asFluidStack(),
                        inputFluid,
                        recipe.time
                );
            }
        }
    }

    @Override
    public void postInit() {
        if (recipes != null) {
            if (recipes.data != null) {
                GrowthcraftLogger.getLogger(Reference.MODID).debug("Registering %d fermenting recipes.", recipes.data.size());
                for (UserFermentingRecipe recipe : recipes.data) addRecipe(recipe);
            } else {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Fermenting Recipes data is invalid!");
            }
        }
    }
}
