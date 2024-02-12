package growthcraft.core.common.creativetabs;

import growthcraft.core.shared.init.GrowthcraftCoreItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabGrowthcraft extends CreativeTabs {

    public TabGrowthcraft() {
        super("Growthcraft");
    }

    @Override
    public ItemStack createIcon() {
        return GrowthcraftCoreItems.crowbar.asStack();
    }
}
