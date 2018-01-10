package growthcraft.cellar.handlers;

import growthcraft.cellar.init.GrowthcraftCellarItems;
import growthcraft.core.api.definition.IItemStackFactory;
import net.minecraft.item.ItemStack;

public class EnumHandler {
	public enum EnumYeast implements IItemStackFactory
	{
		BREWERS,
		LAGER,
		BAYANUS,
		ETHEREAL,
		ORIGIN;

		public static final int length = values().length;

		/**
		 * Convience method for creating the corresponding yeast stack
		 *   example: EnumYeast.BREWERS.asStack(size)
		 *
		 * @param size - size of the stack to create
		 * @return yeast stack
		 */
		public ItemStack asStack(int size)
		{
			return GrowthcraftCellarItems.yeast.asStack(size, ordinal());
		}

		/**
		 * @return yeast stack, size: 1
		 */
		public ItemStack asStack()
		{
			return asStack(1);
		}
		
		public String toOreName() {
			String yeastName = toString();
			return "yeast" + Character.toUpperCase(yeastName.charAt(0)) + yeastName.substring(1).toLowerCase();
		}

		public static EnumYeast getTypeFromMeta(int meta) {
			if( meta < 0 || meta >= values().length )
				return BREWERS;
			return values()[meta];
		}
	}
}
