package growthcraft.cellar.shared.processing.culturing.user;

import java.io.BufferedReader;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.config.schema.FluidStackSchema;
import growthcraft.core.shared.config.schema.ItemKeySchema;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.config.AbstractUserJSONConfig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * This allows users to define new culturing recipes.
 */
public class UserCultureRecipesConfig extends AbstractUserJSONConfig {
    private final UserCultureRecipes defaultRecipes = new UserCultureRecipes();
    private UserCultureRecipes recipes;

    public void addDefault(UserCultureRecipe recipe) {
        defaultRecipes.data.add(recipe);
    }

    public void addDefault(FluidStack inp, ItemStack stack, float heat, int time) {
        addDefault(
                new UserCultureRecipe(
                        new FluidStackSchema(inp),
                        new ItemKeySchema(stack),
                        heat,
                        time
                )
        );
    }

    @Override
    protected String getDefault() {
        return gson.toJson(defaultRecipes);
    }

    @Override
    protected void loadFromBuffer(BufferedReader reader) throws IllegalStateException {
        this.recipes = gson.fromJson(reader, UserCultureRecipes.class);
    }

    private void addRecipe(UserCultureRecipe recipe) {
        if (recipe == null) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid recipe");
            return;
        }

        if (recipe.output_item == null || recipe.output_item.isInvalid()) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid output_item for recipe {%s}", recipe);
            return;
        }

        if (recipe.input_fluid == null || recipe.input_fluid.isInvalid()) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid input_fluid {%s}", recipe);
            return;
        }

        final FluidStack inputFluidStack = recipe.input_fluid.asFluidStack();

        GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding user culturing recipe {%s}", recipe);
        CellarRegistry.instance().culturing().addRecipe(inputFluidStack, recipe.output_item.asStack(), recipe.required_heat, recipe.time);
    }

    @Override
    public void postInit() {
        if (recipes != null) {
            if (recipes.data != null) {
                GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding %d user culturing recipes.", recipes.data.size());
                for (UserCultureRecipe recipe : recipes.data) addRecipe(recipe);
            } else {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Recipes data is invalid!");
            }
        }
    }
}
