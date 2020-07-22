package growthcraft.cellar.shared.processing.fermenting;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.item.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FermentingRegistry {
    private List<IFermentationRecipe> recipes = new ArrayList<IFermentationRecipe>();

    private void onRecipeAdded(@Nonnull IFermentationRecipe recipe) {
        GrowthcraftLogger.getLogger(Reference.MODID).debug("Added Fermentation recipe={%s}", recipe);
    }

    public void addRecipe(@Nonnull IFermentationRecipe recipe) {
        // TODO: Warn if multiple fallback recipes exist for same input fluid

        recipes.add(recipe);
        onRecipeAdded(recipe);
    }

    public void addRecipe(@Nonnull FluidStack result, @Nonnull Object booze, @Nonnull Object fermenter, int time) {
        addRecipe(new FermentationRecipe(MultiStacksUtil.toMultiFluidStacks(booze), MultiStacksUtil.toMultiItemStacks(fermenter), result, time));
    }

    public void addFallbackRecipe(@Nonnull FluidStack result, @Nonnull Object booze, int time) {
        addRecipe(new FermentationFallbackRecipe(MultiStacksUtil.toMultiFluidStacks(booze), result, time));
    }

    public IFermentationRecipe findRecipe(@Nullable FluidStack booze, @Nullable ItemStack fermenter) {
        return findRecipe(booze, fermenter, false);
    }

    public IFermentationRecipe findRecipe(@Nullable FluidStack booze, @Nullable ItemStack fermenter, boolean forceAllowFallback) {
        // TODO: fermenter == null can be a MC 1.7.10 convention for empty stacks. Check if sound for current MC version.

        if (booze == null || fermenter == null) return null;
        for (IFermentationRecipe recipe : recipes) {
            if (isFallbackRecipe(recipe))
                continue;
            if (recipe.matchesRecipe(booze, fermenter)) return recipe;
        }

        if (GrowthcraftCellarConfig.allowFallbackRecipes || forceAllowFallback) {
            for (IFermentationRecipe recipe : recipes) {
                if (!isFallbackRecipe(recipe))
                    continue;
                if (recipe.matchesIngredient(booze)) return recipe;
            }
        }

        return null;
    }

    public List<IFermentationRecipe> findRecipes(@Nullable FluidStack fluid) {
        final List<IFermentationRecipe> result = new ArrayList<IFermentationRecipe>();
        if (fluid != null) {
            for (IFermentationRecipe recipe : recipes) {
                if (recipe.matchesIngredient(fluid))
                    result.add(recipe);
            }
        }
        return result;
    }

    public List<IFermentationRecipe> findRecipes(@Nullable ItemStack fermenter) {
        return findRecipes(fermenter, false);
    }

    public List<IFermentationRecipe> findRecipes(@Nullable ItemStack fermenter, boolean forceAllowFallback) {
        // TODO: fermenter == null can be a MC 1.7.10 convention for empty stacks. Check if sound for current MC version.

        final boolean allowFallback = GrowthcraftCellarConfig.allowFallbackRecipes || forceAllowFallback;

        final List<IFermentationRecipe> result = new ArrayList<IFermentationRecipe>();
        if (fermenter != null) {
            for (IFermentationRecipe recipe : recipes) {
                if (allowFallback && isFallbackRecipe(recipe)) {
                    result.add(recipe);
                    continue;
                }

                if (recipe.matchesIngredient(fermenter)) {
                    result.add(recipe);
                    continue;
                }
            }
        }

        return result;
    }

    public boolean canFerment(@Nullable FluidStack fluid) {
        if (fluid != null) {
            for (IFermentationRecipe recipe : recipes) {
                if (recipe.matchesIngredient(fluid))
                    return true;
            }
        }
        return false;
    }

    public boolean isItemFermentationIngredient(@Nullable ItemStack itemstack) {
        // TODO: itemstack == null can be a MC 1.7.10 convention for empty stacks. Check if sound for current MC version.

        if (itemstack == null) return false;

        for (IFermentationRecipe recipe : recipes) {
            if (isFallbackRecipe(recipe))    // Ignore default recipes
                continue;

            if (recipe.isItemIngredient(itemstack))
                return true;
        }
        return false;
    }

    public boolean isFallbackRecipe(IFermentationRecipe recipe) {
        return (recipe instanceof FermentationFallbackRecipe) && !(recipe instanceof FermentationRecipe);
    }
}