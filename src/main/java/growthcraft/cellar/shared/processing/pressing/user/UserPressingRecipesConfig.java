package growthcraft.cellar.shared.processing.pressing.user;

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
 * This allows users to define their own pressing recipes using existing items
 * and fluids. Growthcraft WILL NOT create new fluids or items for you, THEY
 * MUST EXIST, or we will not register your recipe.
 */
public class UserPressingRecipesConfig extends AbstractUserJSONConfig {
    private final UserPressingRecipes defaultRecipes = new UserPressingRecipes();
    private UserPressingRecipes recipes;

    public void addDefault(UserPressingRecipe recipe) {
        defaultRecipes.data.add(recipe);
    }

    public void addDefault(ItemKeySchema itm, FluidStackSchema fl, int tm, ResidueSchema res) {
        addDefault(new UserPressingRecipe(itm, fl, tm, res));
    }

    public void addDefault(Object stack, FluidStack fluid, int time, Residue res) {
        for (ItemKeySchema itemKey : ItemKeySchema.createMulti(stack)) {
            addDefault(
                    itemKey,
                    new FluidStackSchema(fluid),
                    time,
                    res == null ? null : new ResidueSchema(res)
            );
        }
    }

    @Override
    protected String getDefault() {
        return gson.toJson(defaultRecipes);
    }

    @Override
    protected void loadFromBuffer(BufferedReader reader) throws IllegalStateException {
        this.recipes = gson.fromJson(reader, UserPressingRecipes.class);
    }

    protected void addPressingRecipe(UserPressingRecipe recipe) {
        if (recipe == null) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("NULL RECIPE");
            return;
        }

        if (recipe.item == null || recipe.item.isInvalid()) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Item is invalid for recipe {%s}", recipe);
            return;
        }

        if (recipe.fluid == null) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("No result fluid for recipe {%s}", recipe);
            return;
        }

        final FluidStack fluidStack = recipe.fluid.asFluidStack();
        if (fluidStack == null) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("Invalid fluid for recipe {%s}", recipe);
            return;
        }

        Residue residue = null;
        if (recipe.residue != null) {
            residue = recipe.residue.asResidue();
            if (residue == null) {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Not a valid residue found for {%s}", recipe);
                return;
            }
        }

        GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding pressing recipe {%s}", recipe);
        for (IMultiItemStacks item : recipe.item.getMultiItemStacks()) {
            CellarRegistry.instance().pressing().addRecipe(item, fluidStack, recipe.time, residue);
        }
    }

    @Override
    public void postInit() {
        if (recipes != null) {
            if (recipes.data != null) {
                GrowthcraftLogger.getLogger(Reference.MODID).debug("Adding %d user pressing recipes.", recipes.data.size());
                for (UserPressingRecipe recipe : recipes.data) addPressingRecipe(recipe);
            } else {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Recipe data is invalid!");
            }
        }
    }
}