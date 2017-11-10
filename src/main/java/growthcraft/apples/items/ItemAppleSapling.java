package growthcraft.apples.items;

import growthcraft.apples.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemAppleSapling extends Item {

    public ItemAppleSapling(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
