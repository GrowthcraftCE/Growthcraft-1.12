package growthcraft.tweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.processing.brewing.BrewingRecipe;
import growthcraft.cellar.shared.processing.common.Residue;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.growthcraft.BrewKettle")
public class BrewKettle {


    @ZenMethod
    public static void addRecipe(ILiquidStack pInputFluid, IIngredient pInputItem,
                                 ILiquidStack pOutputFluid, boolean requiresLid, int craftingTime, IItemStack residue, float residuePerInput) {
        DummyAction.apply(() ->
                CellarRegistry.instance().brewing().addRecipe(
                        new BrewingRecipe(
                                Utils.toFluidStack(pInputFluid),
                                Utils.toMultiStack(pInputItem),
                                Utils.toFluidStack(pOutputFluid),
                                requiresLid,
                                craftingTime,
                                new Residue(Utils.toStack(residue), residuePerInput)
                        )
                )
        );
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack pInputFluid, IItemStack pInputItem, boolean requiresLid) {
        DummyAction.apply(() -> CellarRegistry.instance().brewing().removeRecipe(Utils.toFluidStack(pInputFluid), Utils.toStack(pInputItem), requiresLid));
    }
}
