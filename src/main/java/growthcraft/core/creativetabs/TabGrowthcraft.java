package growthcraft.core.creativetabs;

import growthcraft.core.init.GrowthcraftCoreItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabGrowthcraft extends CreativeTabs {

    public TabGrowthcraft() {
        super("Growthcraft");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(GrowthcraftCoreItems.crowbar);
    }
}
