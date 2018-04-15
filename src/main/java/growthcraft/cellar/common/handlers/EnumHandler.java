package growthcraft.cellar.common.handlers;

import growthcraft.cellar.common.init.GrowthcraftCellarItems;
import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.core.shared.definition.IObjectVariant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class EnumHandler {
	public enum EnumYeast implements IStringSerializable, IItemStackFactory, IObjectVariant
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
		@Override
		public ItemStack asStack(int size)
		{
			return GrowthcraftCellarItems.yeast.asStack(size, ordinal());
		}

		/**
		 * @return yeast stack, size: 1
		 */
		@Override
		public ItemStack asStack()
		{
			return asStack(1);
		}
		
        @Override
        public int getVariantID() {
            return this.ordinal();
        }
		
		public String toOreName() {
			String yeastName = getName();
			return "yeast" + Character.toUpperCase(yeastName.charAt(0)) + yeastName.substring(1).toLowerCase();
		}

		public static EnumYeast getSafeByMeta(int meta) {
			if( meta < 0 || meta >= values().length )
				return BREWERS;
			return values()[meta];
		}

		@Override
		public String getName() {
			return super.toString().toLowerCase();
		}
		
		@Override
		public String toString() {
			return getName();
		}
	}
}
