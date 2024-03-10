package growthcraft.bees.common.items;

import growthcraft.bees.shared.Reference;
import growthcraft.core.shared.item.GrowthcraftItemFoodBase;
import net.minecraft.init.Items;

public class ItemHoneyJar extends GrowthcraftItemFoodBase {

    public ItemHoneyJar(String unlocalizedName) {
        super(6, false);
        this.setContainerItem(Items.FLOWER_POT);
        this.setMaxStackSize(1);
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }
}