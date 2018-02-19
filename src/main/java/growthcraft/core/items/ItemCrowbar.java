package growthcraft.core.items;

import growthcraft.core.Reference;
import growthcraft.core.handlers.EnumHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

public class ItemCrowbar extends Item {

    public ItemCrowbar(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for ( int i = 0; i < EnumHandler.CrowbarTypes.values().length; i++ ) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for ( int i = 0; i < EnumHandler.CrowbarTypes.values().length; i++ ) {
            if ( stack.getItemDamage() == i ) {
                return  this.getUnlocalizedName() + "." + EnumHandler.CrowbarTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return super.getUnlocalizedName() + "." + EnumHandler.CrowbarTypes.SILVER.getName();
    }
}
