package growthcraft.bees.init;

import growthcraft.bees.handlers.EnumHandler.BeesWaxTypes;
import growthcraft.bees.items.ItemBeesWax;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.definition.ItemDefinition;
import net.minecraftforge.oredict.OreDictionary;

public class GrowthcraftBeesItems {
	public static ItemDefinition beesWax;

	private GrowthcraftBeesItems() {}

	public static void preInit() {
		beesWax = new ItemDefinition( new ItemBeesWax("bees_wax") );
	}
	
	private static void registerOres() {
		OreDictionary.registerOre("materialBeeswax", beesWax.getItem());
		OreDictionary.registerOre("materialBeeswaxBlack", BeesWaxTypes.BLACK.asStack());
		OreDictionary.registerOre("materialBeeswaxRed", BeesWaxTypes.RED.asStack());
	}

	public static void register() {
		beesWax.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
		beesWax.register();
		
		registerOres();
	}
	
	public static void registerRenders() {
		beesWax.registerRenders( BeesWaxTypes.class );
	}
	
	public static void registerItemVariants() {
		beesWax.registerModelBakeryVariants( BeesWaxTypes.class );
	}
}
