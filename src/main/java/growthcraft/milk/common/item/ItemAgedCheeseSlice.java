package growthcraft.milk.common.item;

import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.AgedCheeseTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ItemAgedCheeseSlice extends ItemFood {

    public ItemAgedCheeseSlice(String unlocalizedName, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHasSubtypes(true);
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if( !this.isInCreativeTab(tab) )
			return;

        for (int i = 0; i < AgedCheeseTypes.values().length; i++) {
            subItems.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for (int i = 0; i < AgedCheeseTypes.values().length; i++) {
            if (stack.getItemDamage() == i) {
                return this.getUnlocalizedName() + "." + AgedCheeseTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return super.getUnlocalizedName() + "." + AgedCheeseTypes.GORGONZOLA.getName();
    }
}
