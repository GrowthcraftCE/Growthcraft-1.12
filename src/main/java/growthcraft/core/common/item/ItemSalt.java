package growthcraft.core.common.item;

import growthcraft.core.shared.Reference;
import net.minecraft.item.Item;

public class ItemSalt extends Item {

    public ItemSalt ( String unlocalizedName ) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

}
