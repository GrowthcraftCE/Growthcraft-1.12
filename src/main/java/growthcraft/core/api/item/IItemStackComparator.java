package growthcraft.core.api.item;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

public interface IItemStackComparator
{
	boolean equals(@Nonnull ItemStack expected, @Nonnull ItemStack actual);
}
