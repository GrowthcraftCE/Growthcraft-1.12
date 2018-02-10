package growthcraft.grapes.api.definition;

import growthcraft.core.api.definition.IItemStackFactory;
import growthcraft.core.api.definition.IObjectVariant;

public interface IGrapeType extends IItemStackFactory, IObjectVariant {
	int getPlantSubTypeID();
	
	@Override
	default int getVariantID() {
		return getPlantSubTypeID();
	}
}
