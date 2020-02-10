package growthcraft.milk.common.lib.processing;

import growthcraft.milk.common.item.ItemBlockHangingCurds;
import growthcraft.milk.shared.processing.cheesepress.ICheesePressRecipe;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class DriedCurdsCheesePressRecipe implements ICheesePressRecipe {
    private ItemStack inputStack;
    private ItemStack outputStack;
    private int time;

    public DriedCurdsCheesePressRecipe(@Nonnull ItemStack pInputStack, @Nonnull ItemStack pOutputStack, int pTime) {
        this.inputStack = pInputStack;
        this.outputStack = pOutputStack;
        this.time = pTime;
    }

    @Override
    public ItemStack getInputItemStack() {
        return inputStack;
    }

    @Override
    public ItemStack getOutputItemStack() {
        return outputStack;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public boolean isMatchingRecipe(@Nonnull ItemStack stack) {
        if (inputStack.isItemEqual(stack)) {
            if (stack.getCount() >= inputStack.getCount()) {
                if (stack.getItem() instanceof ItemBlockHangingCurds) {
                    final ItemBlockHangingCurds<?> item = (ItemBlockHangingCurds<?>) stack.getItem();
                    return item.isDried(stack);
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("DriedCurdsCheesePressRecipe({%s} / %d = {%s})", getOutputItemStack(), time, getInputItemStack());
    }


}
