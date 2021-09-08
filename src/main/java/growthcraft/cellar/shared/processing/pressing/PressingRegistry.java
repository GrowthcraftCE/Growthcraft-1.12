package growthcraft.cellar.shared.processing.pressing;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.item.MultiStacksUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PressingRegistry {
    private List<IPressingRecipe> recipes = new ArrayList<IPressingRecipe>();

    public void addRecipe(@Nonnull IPressingRecipe recipe) {
        recipes.add(recipe);
        GrowthcraftLogger.getLogger(Reference.MODID).debug("Added new Pressing Recipe recipe={%s}", recipe);
    }

    public void addRecipe(@Nonnull Object inputStack, @Nonnull FluidStack resultFluid, int time, @Nullable Residue residue) {
        addRecipe(new PressingRecipe(MultiStacksUtil.toMultiItemStacks(inputStack), resultFluid, time, residue));
    }

    public void removeRecipe(ItemStack input) {
        recipes.removeIf(r -> r.matchesRecipe(input));
    }

    public IPressingRecipe getPressingRecipe(ItemStack itemstack) {
        if (itemstack == null) return null;

        for (IPressingRecipe recipe : recipes) {
            if (recipe.matchesRecipe(itemstack)) return recipe;
        }
        return null;
    }

    public boolean hasPressingRecipe(ItemStack itemstack) {
        return getPressingRecipe(itemstack) != null;
    }
}
