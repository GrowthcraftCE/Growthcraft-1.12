package growthcraft.bees.common.items;

import growthcraft.bees.common.handlers.EnumHandler;
import growthcraft.bees.shared.Reference;
import growthcraft.core.shared.item.GrowthcraftItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBeesWax extends GrowthcraftItemBase {

    public ItemBeesWax(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for ( int i = 0; i < EnumHandler.BeesWaxTypes.values().length; i++ ) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for ( int i = 0; i < EnumHandler.BeesWaxTypes.values().length; i++ ) {
            if ( stack.getItemDamage() == i ) {
                return this.getUnlocalizedName() + "." + EnumHandler.BeesWaxTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return super.getUnlocalizedName() + "." + EnumHandler.BeesWaxTypes.NORMAL.getName();
    }
}