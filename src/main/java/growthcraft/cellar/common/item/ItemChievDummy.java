package growthcraft.cellar.common.item;

import growthcraft.cellar.shared.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemChievDummy extends Item {
    public ItemChievDummy(String unlocalizedName) {
        this.setCreativeTab(null);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
//		this.setCreativeTab(tabGrowthcraft);

        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
