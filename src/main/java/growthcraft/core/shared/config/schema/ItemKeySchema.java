package growthcraft.core.shared.config.schema;

import growthcraft.core.shared.definition.IItemStackListProvider;
import growthcraft.core.shared.definition.IMultiItemStacks;
import growthcraft.core.shared.item.OreItemStacks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemKeySchema extends ItemStackSchema implements IItemStackListProvider, IValidatable {
    public String ore;

    public ItemKeySchema(@Nonnull String mid, @Nonnull String name, int amt, int mt) {
        super(mid, name, amt, mt);
    }

    public ItemKeySchema(@Nonnull String o, int amt) {
        this.ore = o;
        this.amount = amt;
    }

    public ItemKeySchema(@Nonnull ItemStack stack) {
        super(stack);
    }

    public ItemKeySchema() {
        super();
    }

    /**
     * @return list of ores or null if the name was invalid
     */
    public List<ItemStack> getOres() {
        if (ore != null) return OreDictionary.getOres(ore);
        return null;
    }

    /**
     * @return list of itemstacks, the list may be empty if it is invalid
     */
    @Override
    public List<ItemStack> getItemStacks() {
        final List<ItemStack> result = super.getItemStacks();
        final List<ItemStack> ores = getOres();
        if (ores != null) {
            for (ItemStack item : ores) {
                final ItemStack stack = item.copy();
                stack.setCount(amount);
                result.add(stack);
            }
        }
        return result;
    }

    /**
     * @return list of multi itemstacks, the list may be empty if it is invalid
     */
    @Override
    public List<IMultiItemStacks> getMultiItemStacks() {
        final List<IMultiItemStacks> result = super.getMultiItemStacks();
        if (ore != null)
            result.add(new OreItemStacks(ore, amount));
        return result;
    }

    /**
     * @return string representing the ItemKeySchema
     */
    @Override
    public String toString() {
        String result = String.format("Schema<ItemKey>(comment: '%s', amount: %d)", comment, amount);
        if (mod_id != null && name != null) {
            result += String.format("~(mod_id: '%s', name: '%s', meta: %d)", mod_id, name, meta);
        }
        if (ore != null) {
            result += String.format("~(ore: '%s')", ore);
        }
        return result;
    }

    /**
     * Does this schema have ANY valid items?
     *
     * @return true if there are any items, false otherwise
     */
    @Override
    public boolean isValid() {
        return getItemStacks().size() > 0 || ore != null;
    }

    /**
     * @return true if there are no items, false otherwise
     */
    @Override
    public boolean isInvalid() {
        return !isValid();
    }

    public static List<ItemKeySchema> createMulti(Object obj) {
        final List<ItemKeySchema> result = new ArrayList<ItemKeySchema>();
        // special handling for OreItemStacks
        if (obj instanceof OreItemStacks) {
            final OreItemStacks oreItemStack = (OreItemStacks) obj;
            result.add(new ItemKeySchema(oreItemStack.getName(), oreItemStack.getStackSize()));
        }
        // generic handling
        else if (obj instanceof IMultiItemStacks) {
            final IMultiItemStacks multiStack = (IMultiItemStacks) obj;
            for (ItemStack stack : multiStack.getItemStacks()) {
                result.add(new ItemKeySchema(stack));
            }
        } else if (obj instanceof ItemStack) {
            result.add(new ItemKeySchema((ItemStack) obj));
        } else {
            throw new IllegalArgumentException("Wrong type, expected a ItemStack or OreItemStacks, or IMultiItemStacks");
        }
        return result;
    }
}
