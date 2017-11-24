package growthcraft.milk.items;

import growthcraft.milk.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemStarterCulture extends Item {
    public ItemStarterCulture(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
