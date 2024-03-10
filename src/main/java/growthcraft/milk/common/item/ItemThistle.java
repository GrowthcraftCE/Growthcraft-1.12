package growthcraft.milk.common.item;

import growthcraft.milk.shared.Reference;
import net.minecraft.item.Item;

public class ItemThistle extends Item {

    public ItemThistle(String unlocalizedName) {
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

}
