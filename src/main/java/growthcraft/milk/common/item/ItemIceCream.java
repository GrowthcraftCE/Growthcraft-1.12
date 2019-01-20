package growthcraft.milk.common.item;

import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.IceCreamTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemIceCream extends ItemFood {

    public ItemIceCream(String unlocalizedName, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHasSubtypes(true);
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if( !this.isInCreativeTab(tab) )
			return;
        for (int i = 0; i < IceCreamTypes.values().length; i++) {
            subItems.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for (int i = 0; i < IceCreamTypes.values().length; i++) {
            if (stack.getItemDamage() == i) {
                return this.getUnlocalizedName() + "." + IceCreamTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return super.getUnlocalizedName() + "." + IceCreamTypes.PLAIN.getName();
    }
    
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL);
    }
}
