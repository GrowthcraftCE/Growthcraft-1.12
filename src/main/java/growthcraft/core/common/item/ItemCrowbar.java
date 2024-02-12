package growthcraft.core.common.item;

import growthcraft.core.shared.Reference;
import growthcraft.core.shared.init.GrowthcraftCoreItems.CrowbarTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemCrowbar extends Item {

    public ItemCrowbar(String unlocalizedName) {
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!this.isInCreativeTab(tab))
            return;
        for (int i = 0; i < CrowbarTypes.values().length; i++) {
            subItems.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        for (int i = 0; i < CrowbarTypes.values().length; i++) {
            if (stack.getItemDamage() == i) {
                return this.getTranslationKey() + "." + CrowbarTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return super.getTranslationKey() + "." + CrowbarTypes.SILVER.getName();
    }
}
