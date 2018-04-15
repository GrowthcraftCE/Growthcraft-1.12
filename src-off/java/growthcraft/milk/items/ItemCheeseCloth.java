package growthcraft.milk.items;

import growthcraft.milk.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemCheeseCloth extends Item {

    public ItemCheeseCloth(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

}
