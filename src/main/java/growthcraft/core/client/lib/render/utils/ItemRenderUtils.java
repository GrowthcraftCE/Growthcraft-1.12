package growthcraft.core.client.lib.render.utils;

import growthcraft.core.common.lib.item.IItemColored;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRenderUtils {
	public static void registerItemColorHandler(Item colorableItem) {
		ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
		itemColors.registerItemColorHandler(new IItemColor() {

			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if( tintIndex != 0 )
					return -1;
				Item item = stack.getItem();
				if( !(item instanceof IItemColored) )
					return -1;
				IItemColored itemColored = (IItemColored)item;
				int value = itemColored.getColor(stack);
				
				return value;
			}
			
		}, colorableItem);
	}

}
