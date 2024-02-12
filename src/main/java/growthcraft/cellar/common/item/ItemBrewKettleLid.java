package growthcraft.cellar.common.item;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.item.GrowthcraftItemBase;
import net.minecraft.util.ResourceLocation;

public class ItemBrewKettleLid extends GrowthcraftItemBase {
    public ItemBrewKettleLid(String unlocalizedName) {
        super();
        setMaxDamage(0);
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
