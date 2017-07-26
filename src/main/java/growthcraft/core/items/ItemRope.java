package growthcraft.core.items;

import growthcraft.core.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemRope extends Item {

    public ItemRope(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

}
