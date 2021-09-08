package growthcraft.tweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.fluids.MultiFluidStacks;
import growthcraft.core.shared.item.MultiItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.stream.Collectors;

public class Utils {

    public static FluidStack toFluidStack(ILiquidStack liquidStack) {
        return FluidRegistry.getFluidStack(liquidStack.getName(), liquidStack.getAmount());
    }

    public static IMultiItemStacks toMultiStack(IIngredient ingredient) {
        return new MultiItemStacks(ingredient.getItems().stream().map(Utils::toStack).collect(Collectors.toList()));
    }

    public static ItemStack toStack(IItemStack ctStack) {
        return toStackTrim(ctStack, false);
    }

    public static ItemStack toStackTrim(IItemStack ctStack, boolean trim) {
        if (ctStack == null)
            return ItemStack.EMPTY;
        else {
            Object internal = ctStack.getInternal();
            if (!(internal instanceof ItemStack))
                CraftTweakerAPI.getLogger().logError("Not a valid item stack: " + ctStack);
            else if (trim) {
                ItemStack t = ((ItemStack) internal);
                if (t.getCount() > 1) {
                    CraftTweakerAPI.getLogger().logWarning("Stack size not supported, reduced to one item.");
                    t.setCount(1);
                }
                return t;
            }
            return ((ItemStack) internal);
        }
    }

    public static IMultiFluidStacks toMultiFluid(ILiquidStack liquidStack) {
        return new MultiFluidStacks(liquidStack.getLiquids().stream().map(Utils::toFluidStack).collect(Collectors.toList()));
    }
}
