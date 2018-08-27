package growthcraft.rice.common.item;

import growthcraft.core.shared.item.GrowthcraftItemBase;
import growthcraft.rice.shared.Reference;

public class ItemRice extends GrowthcraftItemBase {

    public ItemRice(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName);
    }

    // TODO: onItemUse needs to check target block. If it is a filled RicePatty block, then we need to plant the rice.

}
