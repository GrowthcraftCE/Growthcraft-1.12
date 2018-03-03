package growthcraft.bees.items;

import growthcraft.bees.Reference;
import growthcraft.core.common.item.GrowthcraftItemBase;
import growthcraft.core.common.item.GrowthcraftItemFoodBase;
import net.minecraft.init.Items;

public class ItemHoneyJar extends GrowthcraftItemFoodBase {

    public ItemHoneyJar(String unlocalizedName) {
    	super(6, false);
		this.setContainerItem(Items.FLOWER_POT);
		this.setMaxStackSize(1);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }
}