package growthcraft.cellar.shared.processing.brewing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.shared.processing.common.ProcessingRecipe;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.item.ItemTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BrewingRecipe extends BrewingFallbackRecipe {
    private IMultiItemStacks inputItemStack;
    private boolean requiresLid;

    public BrewingRecipe(@Nonnull FluidStack pInputFluid, @Nonnull IMultiItemStacks pInputItem,
                         @Nonnull FluidStack pOutputFluid, boolean requiresLid, int pTime, @Nullable Residue pResidue) {
        super(pInputFluid, pOutputFluid, pTime, pResidue);
        this.inputItemStack = pInputItem;
        this.requiresLid = requiresLid;
    }

    @Override
    public IMultiItemStacks getInputItemStack() {
        return inputItemStack;
    }

    @Override
    public boolean matchesRecipe(@Nullable FluidStack fluidStack, @Nullable ItemStack itemStack, boolean requiresLid) {
        if (fluidStack != null && itemStack != null) {
            if (this.requiresLid != requiresLid)
                return false;
            if (!FluidTest.hasEnough(getInputFluidStack(), fluidStack))
                return false;
            if (!ItemTest.hasEnough(inputItemStack, itemStack))
                return false;
            return true;
        }
        return false;
    }

    @Override
    public boolean matchesIngredient(@Nullable FluidStack fluidStack) {
        return FluidTest.fluidMatches(getInputFluidStack(), fluidStack);
    }

    @Override
    public boolean matchesIngredient(@Nullable ItemStack stack) {
        return ItemTest.itemMatches(inputItemStack, stack);
    }

    @Override
    public boolean isItemIngredient(@Nullable ItemStack stack) {
        if (stack != null) {
            if (inputItemStack.containsItemStack(stack))
                return true;
        }
        return false;
    }
}
