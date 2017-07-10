package growthcraft.core.items;

import growthcraft.core.Reference;
import net.minecraft.item.Item;

public class ItemSalt extends Item {

    public ItemSalt ( String unlocalizedName ) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }




}
