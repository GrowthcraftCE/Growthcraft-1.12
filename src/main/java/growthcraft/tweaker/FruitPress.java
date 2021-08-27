package growthcraft.tweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.cellar.shared.processing.pressing.PressingRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.growthcraft.FruitPress")
public class FruitPress {

    @ZenMethod
    public static void addRecipe(IIngredient input, int craftingTime, ILiquidStack product, IItemStack residue, float residuePerInput) {
        DummyAction.apply(() ->
                CellarRegistry.instance().pressing().addRecipe(
                        new PressingRecipe(
                                Utils.toMultiStack(input),
                                Utils.toFluidStack(product),
                                craftingTime,
                                new Residue(Utils.toStack(residue), residuePerInput)
                        )
                )
        );
    }

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        DummyAction.apply(() -> CellarRegistry.instance().pressing().removeRecipe(Utils.toStack(input)));
    }
}
