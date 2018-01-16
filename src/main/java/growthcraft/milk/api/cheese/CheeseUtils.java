package growthcraft.milk.api.cheese;

import growthcraft.core.api.definition.IObjectVariant;
import growthcraft.milk.api.definition.EnumCheeseStage;

public class CheeseUtils {
	public static <T extends IObjectVariant> int getItemMetaFor(T cheese, EnumCheeseStage atStage) {
		// first 3 bits reserved for stage. The rest is for the variant.
		if( atStage.ordinal() > 7 )
			throw new IllegalArgumentException("Maximal 8 stages are supported.");
		return (cheese.getVariantID() << 3) | atStage.ordinal();
	}
	
	public static int getVariantIDFromMeta(int meta) {
		return meta >> 3;
	}
	
	public static EnumCheeseStage getStageFromMeta(int meta) {
		return EnumCheeseStage.values()[meta & 0x7];
	}
}
