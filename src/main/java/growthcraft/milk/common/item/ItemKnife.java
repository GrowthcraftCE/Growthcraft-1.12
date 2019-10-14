package growthcraft.milk.common.item;

import growthcraft.milk.shared.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemKnife extends Item {

    public ItemKnife(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setContainerItem(this);
    }
}
