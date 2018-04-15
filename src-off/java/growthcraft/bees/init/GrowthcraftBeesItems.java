package growthcraft.bees.init;

import growthcraft.bees.handlers.EnumHandler.BeesWaxTypes;
import growthcraft.bees.items.ItemBee;
import growthcraft.bees.items.ItemBeesWax;
import growthcraft.bees.items.ItemHoneyCombEmpty;
import growthcraft.bees.items.ItemHoneyCombFilled;
import growthcraft.bees.items.ItemHoneyJar;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.definition.ItemDefinition;
import net.minecraftforge.oredict.OreDictionary;

public class GrowthcraftBeesItems {

	public static ItemDefinition honeyCombEmpty;
	public static ItemDefinition honeyCombFilled;
	public static ItemDefinition honeyJar;
	public static ItemDefinition bee;
	public static ItemDefinition beesWax;

	private GrowthcraftBeesItems() {}

	public static void preInit() {
		honeyCombEmpty = new ItemDefinition(new ItemHoneyCombEmpty("honey_comb_empty"));
		honeyCombFilled = new ItemDefinition(new ItemHoneyCombFilled("honey_comb_filled"));
		honeyJar = new ItemDefinition(new ItemHoneyJar("honey_jar"));
		bee = new ItemDefinition( new ItemBee("bee") );
		beesWax = new ItemDefinition( new ItemBeesWax("bees_wax") );
	}
	
	private static void registerOres() {
		OreDictionary.registerOre("materialWax", beesWax.getItem());
		OreDictionary.registerOre("materialPressedwax", beesWax.getItem());
		OreDictionary.registerOre("materialBeeswax", beesWax.getItem());
		OreDictionary.registerOre("materialBeeswaxBlack", BeesWaxTypes.BLACK.asStack());
		OreDictionary.registerOre("materialBeeswaxRed", BeesWaxTypes.RED.asStack());
//		OreDictionary.registerOre("beeQueen", bee.getItem());
		OreDictionary.registerOre("materialWaxcomb", honeyCombEmpty.getItem());
		OreDictionary.registerOre("beecomb", honeyCombEmpty.getItem());
		OreDictionary.registerOre("materialHoneycomb", honeyCombFilled.getItem());
		OreDictionary.registerOre("beecomb", honeyCombFilled.getItem());
		OreDictionary.registerOre("honeyDrop", honeyJar.getItem());
		OreDictionary.registerOre("dropHoney", honeyJar.getItem());
		OreDictionary.registerOre("jarHoney", honeyJar.getItem());
//		OreDictionary.registerOre("bucketHoney", honeyJar.getItem());
	}

	public static void register() {
		honeyCombEmpty.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
		honeyCombEmpty.register();
		honeyCombFilled.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
		honeyCombFilled.register();
		honeyJar.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
		honeyJar.register();
		bee.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
		bee.register();
		beesWax.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
		beesWax.register();
		
		registerOres();
	}
	
	public static void registerRenders() {
		honeyCombEmpty.registerRender();
		honeyCombFilled.registerRender();
		honeyJar.registerRender();
		bee.registerRender();
		beesWax.registerRenders( BeesWaxTypes.class );
	}
	
	public static void registerItemVariants() {
		beesWax.registerModelBakeryVariants( BeesWaxTypes.class );
	}
}
