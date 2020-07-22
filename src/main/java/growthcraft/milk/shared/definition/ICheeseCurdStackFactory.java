package growthcraft.milk.shared.definition;

import growthcraft.core.shared.definition.IItemStackFactory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface ICheeseCurdStackFactory extends IItemStackFactory {
    @Override
    default @Nullable
    ItemStack asStack() {
        return asStack(1);
    }
}
