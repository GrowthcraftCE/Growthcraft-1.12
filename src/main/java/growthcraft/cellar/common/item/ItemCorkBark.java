package growthcraft.cellar.common.item;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.item.GrowthcraftItemBase;
import net.minecraft.util.ResourceLocation;

public class ItemCorkBark extends GrowthcraftItemBase {
    public ItemCorkBark(String unlocalizedName) {
        super();
        setMaxDamage(0);
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
