package growthcraft.milk.shared.processing.cheesevat;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.item.ItemTest;
import growthcraft.milk.shared.Reference;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CheeseVatRegistry {
    private List<ICheeseVatRecipe> recipes = new ArrayList<ICheeseVatRecipe>();

    public void addRecipe(ICheeseVatRecipe recipe) {
        recipes.add(recipe);
        GrowthcraftLogger.getLogger(Reference.MODID).debug("Added Cheese Vat recipe {%s}", recipe);
    }

    public void addRecipe(@Nonnull List<FluidStack> outputFluids, @Nonnull List<ItemStack> outputItems, @Nonnull List<IMultiFluidStacks> inputFluids, @Nonnull List<IMultiItemStacks> inputItems) {
        final CheeseVatRecipe recipe = new CheeseVatRecipe(outputFluids, outputItems, inputFluids, inputItems);
        addRecipe(recipe);
    }

    public boolean isFluidIngredient(@Nullable Fluid fluid) {
        if (!FluidTest.isValid(fluid)) return false;
        for (ICheeseVatRecipe recipe : recipes) {
            if (recipe.isFluidIngredient(fluid)) return true;
        }
        return false;
    }

    public boolean isFluidIngredient(@Nullable FluidStack fluid) {
        if (!FluidTest.isValid(fluid)) return false;
        for (ICheeseVatRecipe recipe : recipes) {
            if (recipe.isFluidIngredient(fluid)) return true;
        }
        return false;
    }

    public boolean isItemIngredient(@Nullable ItemStack item) {
        if (!ItemTest.isValid(item)) return false;
        for (ICheeseVatRecipe recipe : recipes) {
            if (recipe.isItemIngredient(item)) return true;
        }
        return false;
    }

    @Nullable
    public ICheeseVatRecipe findRecipe(@Nonnull List<FluidStack> fluids, @Nonnull List<ItemStack> stacks) {
        for (ICheeseVatRecipe recipe : recipes) {
            if (recipe.isMatchingRecipe(fluids, stacks)) {
                return recipe;
            }
        }
        return null;
    }
}
