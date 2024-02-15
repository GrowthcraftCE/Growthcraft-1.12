package growthcraft.milk.common.item;

import growthcraft.milk.shared.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemCheeseCloth extends Item {

    public ItemCheeseCloth(String unlocalizedName) {
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

}
