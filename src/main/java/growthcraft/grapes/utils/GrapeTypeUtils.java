package growthcraft.grapes.utils;

import growthcraft.grapes.api.definition.IGrapeType;
import net.minecraft.item.ItemStack;

public class GrapeTypeUtils {
	
	public static IGrapeType getTypeBySubID(IGrapeType[] types, int subID) {
		for( IGrapeType type : types ) {
			if( type.getGrapeSubTypeID() == subID )
				return type;
		}
		
		return null;
	}
	
	public static IGrapeType getTypeByVariantID(IGrapeType[] types, int variantID) {
		for( IGrapeType type : types ) {
			if( type.getVariantID() == variantID )
				return type;
		}
		
		return null;
	}


}
