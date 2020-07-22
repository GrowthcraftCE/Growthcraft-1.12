package growthcraft.milk.shared.utils;

import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.item.MultiStacksUtil;
import growthcraft.milk.shared.MilkRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheeseVatRecipeBuilder {
    private String label;
    private List<FluidStack> outputFluids = new ArrayList<FluidStack>();
    private List<ItemStack> outputStacks = new ArrayList<ItemStack>();
    private List<IMultiFluidStacks> inputFluids = new ArrayList<IMultiFluidStacks>();
    private List<IMultiItemStacks> inputStacks = new ArrayList<IMultiItemStacks>();

    public CheeseVatRecipeBuilder(String l) {
        this.label = l;
    }

    private void addFluidStacksToList(@Nonnull List<IMultiFluidStacks> list, @Nonnull Object... objs) {
        for (Object obj : objs) {
            list.add(MultiStacksUtil.toMultiFluidStacks(obj));
        }
    }

    private void addItemStacksToList(@Nonnull List<IMultiItemStacks> list, @Nonnull Object... objs) {
        for (Object obj : objs) {
            list.add(MultiStacksUtil.toMultiItemStacks(obj));
        }
    }

    public CheeseVatRecipeBuilder outputFluids(@Nonnull FluidStack... objs) {
        outputFluids.addAll(Arrays.asList(objs));
        return this;
    }

    public CheeseVatRecipeBuilder outputItems(@Nonnull ItemStack... objs) {
        outputStacks.addAll(Arrays.asList(objs));
        return this;
    }

    public CheeseVatRecipeBuilder inputFluids(@Nonnull Object... objs) {
        addFluidStacksToList(inputFluids, objs);
        return this;
    }

    public CheeseVatRecipeBuilder inputItems(@Nonnull Object... objs) {
        addItemStacksToList(inputStacks, objs);
        return this;
    }

    public CheeseVatRecipeBuilder register() {
        MilkRegistry.instance().cheeseVat().addRecipe(outputFluids, outputStacks, inputFluids, inputStacks);
        return this;
    }

    public static CheeseVatRecipeBuilder buildRecipe(String label) {
        return new CheeseVatRecipeBuilder(label);
    }
}
