package growthcraft.cellar.shared.processing.brewing;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.item.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BrewingRegistry {
    private List<IBrewingRecipe> recipes = new ArrayList<IBrewingRecipe>();

    public void addRecipe(@Nonnull IBrewingRecipe recipe) {
        // TODO: Warn if multiple fallback recipes exist for same input fluid

        recipes.add(recipe);
        GrowthcraftLogger.getLogger(Reference.MODID).debug("Added Brewing Recipe recipe={%s}", recipe);
    }

    public void addRecipe(@Nonnull FluidStack sourceFluid, @Nonnull Object raw, @Nonnull FluidStack resultFluid, boolean requiresLid, int time, @Nullable Residue residue) {
        addRecipe(new BrewingRecipe(sourceFluid, MultiStacksUtil.toMultiItemStacks(raw), resultFluid, requiresLid, time, residue));
    }

    public void addFallbackRecipe(@Nonnull FluidStack sourceFluid, @Nonnull FluidStack resultFluid, int time, @Nullable Residue residue) {
        addRecipe(new BrewingFallbackRecipe(sourceFluid, resultFluid, time, residue));
    }

    public IBrewingRecipe findRecipe(@Nullable FluidStack fluidstack, @Nullable ItemStack itemstack, boolean requiresLid) {
        return findRecipe(fluidstack, itemstack, requiresLid, false);
    }

    public IBrewingRecipe findRecipe(@Nullable FluidStack fluidstack, @Nullable ItemStack itemstack, boolean requiresLid, boolean forceAllowFallback) {
        if (itemstack == null || fluidstack == null) return null;

        for (IBrewingRecipe recipe : recipes) {
            if (isFallbackRecipe(recipe))
                continue;
            if (recipe.matchesRecipe(fluidstack, itemstack, requiresLid)) return recipe;
        }

        if (GrowthcraftCellarConfig.allowFallbackRecipes || forceAllowFallback) {
            for (IBrewingRecipe recipe : recipes) {
                if (!isFallbackRecipe(recipe))
                    continue;
                if (recipe.matchesIngredient(fluidstack)) return recipe;
            }
        }

        return null;
    }

    public List<IBrewingRecipe> findRecipes(@Nullable FluidStack fluid) {
        final List<IBrewingRecipe> result = new ArrayList<IBrewingRecipe>();
        if (fluid != null) {
            for (IBrewingRecipe recipe : recipes) {
                if (recipe.matchesIngredient(fluid))
                    result.add(recipe);
            }
        }
        return result;
    }

    public List<IBrewingRecipe> findRecipes(@Nullable ItemStack fermenter) {
        return findRecipes(fermenter, false);
    }

    public List<IBrewingRecipe> findRecipes(@Nullable ItemStack fermenter, boolean forceAllowFallback) {
        final boolean allowFallback = GrowthcraftCellarConfig.allowFallbackRecipes || forceAllowFallback;

        final List<IBrewingRecipe> result = new ArrayList<IBrewingRecipe>();
        if (fermenter != null) {
            for (IBrewingRecipe recipe : recipes) {
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

    public boolean isBrewingRecipe(@Nullable FluidStack fluidstack, @Nullable ItemStack itemstack, boolean requiresLid) {
        return findRecipe(fluidstack, itemstack, requiresLid) != null;
    }

    public boolean isItemBrewingIngredient(@Nullable ItemStack itemstack) {
        if (itemstack == null) return false;

        for (IBrewingRecipe recipe : recipes) {
            if (isFallbackRecipe(recipe))    // Ignore fallback recipes
                continue;

            if (recipe.isItemIngredient(itemstack)) return true;
        }
        return false;
    }

    public boolean isFallbackRecipe(IBrewingRecipe recipe) {
        return (recipe instanceof BrewingFallbackRecipe) && !(recipe instanceof BrewingRecipe);
    }

    public void removeRecipe(FluidStack toFluidStack, ItemStack toStack, boolean requiresLid) {
        recipes.removeIf(r -> r.matchesRecipe(toFluidStack, toStack, requiresLid));
    }
}
