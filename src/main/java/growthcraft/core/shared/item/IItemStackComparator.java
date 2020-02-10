package growthcraft.core.shared.item;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IItemStackComparator {
    boolean equals(@Nonnull ItemStack expected, @Nonnull ItemStack actual);
}
