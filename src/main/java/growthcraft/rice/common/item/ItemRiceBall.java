package growthcraft.rice.common.item;

import growthcraft.core.shared.item.GrowthcraftItemFoodBase;
import growthcraft.rice.shared.Reference;

public class ItemRiceBall extends GrowthcraftItemFoodBase {

    public ItemRiceBall(String unlocalizedName) {
        super(5, false);
        this.setMaxStackSize(64);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

}
