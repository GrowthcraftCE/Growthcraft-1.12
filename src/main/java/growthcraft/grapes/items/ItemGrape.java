package growthcraft.grapes.items;

import growthcraft.grapes.Reference;
import growthcraft.grapes.handlers.EnumHandler;
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

public class ItemGrape extends ItemFood {

    public ItemGrape (String unlocalizedName, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for ( int i = 0; i < EnumHandler.GrapeTypes.values().length; i++ ) {
        	EnumHandler.GrapeTypes type = EnumHandler.GrapeTypes.values()[i];
            subItems.add(new ItemStack(itemIn, 1, type.getVariantID()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for (int i = 0; i < EnumHandler.GrapeTypes.values().length; i++ ) {
        	EnumHandler.GrapeTypes type = EnumHandler.GrapeTypes.values()[i];
            if ( stack.getItemDamage() == type.getVariantID() ) {
                return  this.getUnlocalizedName() + "." + type.getName();
            } else {
                continue;
            }
        }
        return super.getUnlocalizedName() + "." + EnumHandler.GrapeTypes.PURPLE.getName();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("item.grape.tooltip"));
    }

}
