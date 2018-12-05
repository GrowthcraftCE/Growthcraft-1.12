package growthcraft.grapes.shared.definition;

import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.core.shared.definition.IObjectVariant;
import net.minecraft.item.ItemStack;

public interface IGrapeType extends IItemStackFactory, IObjectVariant {
	int getPlantSubTypeID();
	
	@Override
	default int getVariantID() {
		return getPlantSubTypeID();
	}
	
	default ItemStack asSeedsStack() {
		return asSeedsStack(1);
	}
	
	ItemStack asSeedsStack(int amount);
}
