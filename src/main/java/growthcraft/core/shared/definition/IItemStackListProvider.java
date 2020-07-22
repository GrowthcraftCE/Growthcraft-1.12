package growthcraft.core.shared.definition;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface IItemStackListProvider {
    /**
     * Returns a list of item stacks.
     *
     * @return item stacks
     */
    @Nonnull
    List<ItemStack> getItemStacks();
}
