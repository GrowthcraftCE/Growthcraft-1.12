package growthcraft.milk.items;

import growthcraft.milk.Reference;
import net.minecraft.item.Item;

public class ItemThistle extends Item {

    public ItemThistle(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

}
