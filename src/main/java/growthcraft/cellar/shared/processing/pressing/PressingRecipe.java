package growthcraft.cellar.shared.processing.pressing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.shared.processing.common.ProcessingRecipe;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.item.ItemTest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PressingRecipe extends ProcessingRecipe implements IPressingRecipe {
    private IMultiItemStacks inputItemStack;

    public PressingRecipe(@Nonnull IMultiItemStacks src, @Nonnull FluidStack f, int t, @Nullable Residue r) {
        super(f, t, r);
        this.inputItemStack = src;
    }

    @Override
    public IMultiItemStacks getInput() {
        return inputItemStack;
    }

    @Override
    public boolean matchesRecipe(@Nullable ItemStack itemStack) {
        if (itemStack != null) {
            if (!ItemTest.hasEnough(inputItemStack, itemStack)) return false;
            return true;
        }
        return false;
    }
}
