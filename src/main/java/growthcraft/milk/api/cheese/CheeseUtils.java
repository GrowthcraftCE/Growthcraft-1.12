package growthcraft.milk.api.cheese;

import growthcraft.core.api.definition.IObjectVariant;
import growthcraft.milk.api.definition.EnumCheeseStage;

public class CheeseUtils {
	public static <T extends IObjectVariant> int getItemMetaFor(T cheese, int slices, EnumCheeseStage atStage) {
		// first 3 bits reserved for stage.
		// second 2 bits reserved for cutting.
		// The rest is for the variant.
		if( slices > 4 )
			throw new IllegalArgumentException("Maximal 4 slices are supported.");
		if( atStage.ordinal() > 7 )
			throw new IllegalArgumentException("Maximal 8 stages are supported.");
		return (cheese.getVariantID() << 5) | ((slices-1) << 3) | atStage.ordinal();
	}
	
	public static int getVariantIDFromMeta(int meta) {
		return meta >> 5;
	}

	public static int getSlicesFromMeta(int meta) {
		return ((meta >> 3) & 0x3) + 1;
	}
	
	public static EnumCheeseStage getStageFromMeta(int meta) {
		return EnumCheeseStage.values()[meta & 0x7];
	}
}
