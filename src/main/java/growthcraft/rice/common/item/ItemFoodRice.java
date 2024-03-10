package growthcraft.rice.common.item;

import growthcraft.core.shared.item.GrowthcraftItemFoodBase;
import growthcraft.rice.shared.Reference;

public class ItemFoodRice extends GrowthcraftItemFoodBase {

    public ItemFoodRice(String unlocalizedName) {
        super(2, false);
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

}
