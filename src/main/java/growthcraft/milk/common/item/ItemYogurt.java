package growthcraft.milk.common.item;

import growthcraft.milk.common.handlers.EnumHandler;
import growthcraft.milk.shared.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

public class ItemYogurt extends ItemFood {

    public ItemYogurt(String unlocalizedName, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHasSubtypes(true);
    }


    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (int i = 0; i < EnumHandler.YogurtTypes.values().length; i++) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for (int i = 0; i < EnumHandler.YogurtTypes.values().length; i++) {
            if (stack.getItemDamage() == i) {
                return this.getUnlocalizedName() + "." + EnumHandler.YogurtTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return super.getUnlocalizedName() + "." + EnumHandler.YogurtTypes.PLAIN.getName();
    }
}
