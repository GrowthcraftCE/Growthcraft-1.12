package growthcraft.core.common.lib.item;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

public class CommonItemStackComparator implements IItemStackComparator
{
	/**
	 * @param expected - the expected item stack
	 * @param actual - the given or actual item stack being checked
	 * @return true, the stacks are equal, or has a wildcard, false otherwise
	 */
	public boolean equals(@Nonnull ItemStack expected, @Nonnull ItemStack actual)
	{
		if( ItemUtils.isEmpty(actual) ^ ItemUtils.isEmpty(expected) )
			return false;
		if( !ItemStack.areItemsEqualIgnoreDurability(actual, expected) )
			return false;
		if( !(actual.getItem() == expected.getItem() &&
			 (expected.getItemDamage() == ItemKey.WILDCARD_VALUE ||
				actual.getItemDamage() == expected.getItemDamage())) )
			 return false;
		if( !ItemStack.areItemStackTagsEqual(actual, expected) )
			return false;
		return true;
		
///		return actual.getItem() == expected.getItem() &&
//			(expected.getItemDamage() == ItemKey.WILDCARD_VALUE ||
//				actual.getItemDamage() == expected.getItemDamage());
	}
}
