package growthcraft.milk.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCheeseBowl extends ItemSimpleCheeseSlice {

	public ItemCheeseBowl(String unlocalizedName, int amount, float saturation, boolean isWolfFood) {
		super(unlocalizedName, amount, saturation, isWolfFood);
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
