package growthcraft.cellar.api.processing.culturing;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ICultureRecipe {

	ItemStack getOutputItemStack();
	FluidStack getInputFluidStack();
	float getRequiredHeat();
	int getTime();
	boolean matchesRecipe(FluidStack fluid, float heat);

}
