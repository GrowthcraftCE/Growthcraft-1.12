package growthcraft.hops.items;

import growthcraft.hops.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemHops extends Item {

    public ItemHops (String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

}
