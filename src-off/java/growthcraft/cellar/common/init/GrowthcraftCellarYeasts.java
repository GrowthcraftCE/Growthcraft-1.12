package growthcraft.cellar.common.init;

import growthcraft.cellar.common.handlers.EnumHandler.EnumYeast;
import growthcraft.cellar.common.lib.CellarRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class GrowthcraftCellarYeasts {
    
	private GrowthcraftCellarYeasts() {}
	
	public static void init() {
    	
    }
	
	public static void register() {
		OreDictionary.registerOre("materialYeast", GrowthcraftCellarItems.yeast.getItem());
		for( EnumYeast type : EnumYeast.values() ) {
			ItemStack yeastItemStack = type.asStack();
			OreDictionary.registerOre(type.toOreName(), yeastItemStack);
			CellarRegistry.instance().yeast().addYeast(yeastItemStack);
		}
    }

	
}
