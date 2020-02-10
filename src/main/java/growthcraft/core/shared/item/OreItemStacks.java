package growthcraft.core.shared.item;

import growthcraft.core.shared.definition.IMultiItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class OreItemStacks implements IMultiItemStacks {
    public int stackSize;
    private String oreName;

    public OreItemStacks(@Nonnull String name, int amount) {
        this.oreName = name;
        this.stackSize = amount;
    }

    public OreItemStacks(@Nonnull String name) {
        this(name, 1);
    }

    @Override
    public int getStackSize() {
        return stackSize;
    }

    public String getName() {
        return oreName;
    }

    public List<ItemStack> getRawItemStacks() {
        return OreDictionary.getOres(oreName);
    }

    @Override
    public boolean isEmpty() {
        return getRawItemStacks().isEmpty();
    }

    @Override
    public List<ItemStack> getItemStacks() {
        final List<ItemStack> items = getRawItemStacks();
        final List<ItemStack> result = new ArrayList<ItemStack>();
        for (ItemStack stack : items) {
            final ItemStack newStack = stack.copy();
            if (newStack.isEmpty()) newStack.setCount(1);
            newStack.setCount(newStack.getCount() * stackSize);
            result.add(newStack);
        }
        return result;
    }

    @Override
    public boolean containsItemStack(@Nullable ItemStack stack) {
        if (ItemTest.isValid(stack)) {
            final int oreId = OreDictionary.getOreID(oreName);
            for (int i : OreDictionary.getOreIDs(stack)) {
                if (i == oreId) return true;
            }
        }
        return false;
    }
}