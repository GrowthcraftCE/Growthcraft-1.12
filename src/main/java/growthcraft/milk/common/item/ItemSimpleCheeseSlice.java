package growthcraft.milk.common.item;

import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.SimpleCheeseTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ItemSimpleCheeseSlice extends ItemFood {

    public ItemSimpleCheeseSlice(String unlocalizedName, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHasSubtypes(true);
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!this.isInCreativeTab(tab))
            return;
        for (int i = 0; i < SimpleCheeseTypes.values().length; i++) {
            subItems.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        for (int i = 0; i < SimpleCheeseTypes.values().length; i++) {
            if (stack.getItemDamage() == i) {
                return this.getTranslationKey() + "." + SimpleCheeseTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return super.getTranslationKey() + "." + SimpleCheeseTypes.RICOTTA.getName();
    }
}
