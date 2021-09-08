package growthcraft.tweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.processing.fermenting.FermentationRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.growthcraft.FermentBarrel")
public class FermentBarrel {
    @ZenMethod
    public static void addRecipe(ILiquidStack pInputFluidStack, IIngredient pFermentingItem, ILiquidStack pOutputFluidStack, int craftingTime) {
        DummyAction.apply(() ->
                CellarRegistry.instance().fermenting().addRecipe(
                        new FermentationRecipe(
                                Utils.toMultiFluid(pInputFluidStack),
                                Utils.toMultiStack(pFermentingItem),
                                Utils.toFluidStack(pOutputFluidStack),
                                craftingTime
                        )
                )
        );
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack pInputFluidStack, IItemStack pFermentingItem) {
        DummyAction.apply(() -> CellarRegistry.instance().fermenting().removeRecipe(Utils.toFluidStack(pInputFluidStack), Utils.toStack(pFermentingItem)));
    }
}
