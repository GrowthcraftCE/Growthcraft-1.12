package growthcraft.cellar.shared.processing.culturing;

import growthcraft.cellar.shared.processing.common.IProcessingRecipeBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ICultureRecipe extends IProcessingRecipeBase {

    ItemStack getOutputItemStack();

    FluidStack getInputFluidStack();

    float getRequiredHeat();

    boolean matchesRecipe(FluidStack fluid, float heat);

}
