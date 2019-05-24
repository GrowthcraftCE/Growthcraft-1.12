package growthcraft.bees.common.items;

import growthcraft.bees.shared.Reference;
import growthcraft.bees.shared.init.GrowthcraftBeesItems.BeesWaxTypes;
import growthcraft.core.shared.item.GrowthcraftItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBeesWax extends GrowthcraftItemBase {

    public ItemBeesWax(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if( !this.isInCreativeTab(tab) )
			return;
        for ( int i = 0; i < BeesWaxTypes.values().length; i++ ) {
            subItems.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for ( int i = 0; i < BeesWaxTypes.values().length; i++ ) {
            if ( stack.getItemDamage() == i ) {
                return this.getUnlocalizedName() + "." + BeesWaxTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return super.getUnlocalizedName() + "." + BeesWaxTypes.NORMAL.getName();
    }
}