package growthcraft.apples.common.item;

import growthcraft.apples.shared.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemAppleSapling extends Item {

    public ItemAppleSapling(String unlocalizedName) {
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
