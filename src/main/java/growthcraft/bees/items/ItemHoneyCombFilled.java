package growthcraft.bees.items;

import growthcraft.bees.Reference;
import growthcraft.bees.init.GrowthcraftBeesItems;
import growthcraft.core.common.item.GrowthcraftItemBase;

public class ItemHoneyCombFilled extends GrowthcraftItemBase {

    public ItemHoneyCombFilled(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName);
        setContainerItem(GrowthcraftBeesItems.honeyCombEmpty.getItem());
    }
}
