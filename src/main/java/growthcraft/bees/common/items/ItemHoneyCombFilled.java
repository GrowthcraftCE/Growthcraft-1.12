package growthcraft.bees.common.items;

import growthcraft.bees.shared.Reference;
import growthcraft.bees.shared.init.GrowthcraftBeesItems;
import growthcraft.core.shared.item.GrowthcraftItemBase;

public class ItemHoneyCombFilled extends GrowthcraftItemBase {

    public ItemHoneyCombFilled(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName);
        setContainerItem(GrowthcraftBeesItems.honeyCombEmpty.getItem());
    }
}
