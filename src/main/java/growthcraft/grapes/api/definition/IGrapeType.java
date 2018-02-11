package growthcraft.grapes.api.definition;

import growthcraft.core.api.definition.IItemStackFactory;
import growthcraft.core.api.definition.IObjectVariant;
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
