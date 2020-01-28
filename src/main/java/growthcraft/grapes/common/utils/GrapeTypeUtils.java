package growthcraft.grapes.common.utils;

import growthcraft.grapes.shared.definition.IGrapeType;
import net.minecraft.item.ItemStack;

public class GrapeTypeUtils {

    public static IGrapeType getTypeBySubID(IGrapeType[] types, int subID) {
        for (IGrapeType type : types) {
            if (type.getPlantSubTypeID() == subID)
                return type;
        }

        return null;
    }

    public static IGrapeType getTypeByVariantID(IGrapeType[] types, int variantID) {
        for (IGrapeType type : types) {
            if (type.getVariantID() == variantID)
                return type;
        }

        return null;
    }


}
