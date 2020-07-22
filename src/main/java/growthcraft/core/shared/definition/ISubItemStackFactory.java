package growthcraft.core.shared.definition;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface ISubItemStackFactory extends IItemStackFactory {
    /**
     * Returns an ItemStack of `size` with damage set to `meta`
     *
     * @param size - item stack size
     * @param meta - item stack damage
     * @return item stack
     */
    @Nullable
    ItemStack asStack(int size, int meta);
}
