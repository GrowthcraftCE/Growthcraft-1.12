package growthcraft.milk.shared.definition;

import growthcraft.core.shared.definition.IItemStackFactory;
import net.minecraft.item.ItemStack;

public interface ICheeseItemStackFactory extends IItemStackFactory {
	@Override
	default ItemStack asStack() {
		return asStack(1);
	}
}
