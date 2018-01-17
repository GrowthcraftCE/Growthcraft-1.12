package growthcraft.milk.api.definition;

import growthcraft.core.api.definition.IItemStackFactory;
import net.minecraft.item.ItemStack;

public interface ICheeseItemStackFactory extends IItemStackFactory {
	@Override
	default ItemStack asStack() {
		return asStack(1);
	}
}
