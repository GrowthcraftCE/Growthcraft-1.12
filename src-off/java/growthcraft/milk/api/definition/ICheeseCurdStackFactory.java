package growthcraft.milk.api.definition;

import javax.annotation.Nullable;

import growthcraft.core.api.definition.IItemStackFactory;
import net.minecraft.item.ItemStack;

public interface ICheeseCurdStackFactory extends IItemStackFactory {
	@Override
	default	@Nullable ItemStack asStack() {
		return asStack(1);
	}
}
