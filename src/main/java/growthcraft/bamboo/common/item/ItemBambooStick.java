package growthcraft.bamboo.common.item;

import growthcraft.bamboo.shared.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemBambooStick extends Item {

    public ItemBambooStick(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

}
