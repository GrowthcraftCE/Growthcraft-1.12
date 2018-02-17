package growthcraft.hops.init;

import growthcraft.cellar.common.item.ItemBoozeBottle;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.client.utils.ItemRenderUtils;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.common.definition.ItemTypeDefinition;
import growthcraft.hops.Reference;
import growthcraft.hops.handlers.EnumHandler.HopAleTypes;
import growthcraft.hops.handlers.EnumHandler.LagerTypes;
import growthcraft.hops.items.ItemHops;
import growthcraft.hops.items.ItemSeedHops;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class GrowthcraftHopsItems {
	public static ItemDefinition hops;
    public static ItemDefinition hop_seeds;
	public static ItemTypeDefinition<ItemBoozeBottle> lagerBottle;
    public static ItemTypeDefinition<ItemBoozeBottle> hopAleBottle;

    public static void init() {
        hops = new ItemDefinition( new ItemHops("hops") );
        hop_seeds = new ItemDefinition( new ItemSeedHops("hop_seeds") );
        lagerBottle = new ItemTypeDefinition<ItemBoozeBottle>( new ItemBoozeBottle() );
        hopAleBottle = new ItemTypeDefinition<ItemBoozeBottle>( new ItemBoozeBottle() );
    }
    
    private static void registerOres() {
    	OreDictionary.registerOre("cropHops", hops.getItem());
    }

    public static void register() {
    	hops.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
    	hops.register();
    	hop_seeds.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
    	hop_seeds.register();
    	lagerBottle.register(new ResourceLocation(Reference.MODID, "lager"));
    	hopAleBottle.register(new ResourceLocation(Reference.MODID, "hopale"));
    	
    	registerOres();
    }

    public static void registerRenders() {
    	hops.registerRender();
    	hop_seeds.registerRender();
    	lagerBottle.registerRenders(LagerTypes.class);
    	hopAleBottle.registerRenders(HopAleTypes.class);
    }
    
	public static void registerItemColorHandlers() {
		ItemRenderUtils.registerItemColorHandler(lagerBottle.getItem());
		ItemRenderUtils.registerItemColorHandler(hopAleBottle.getItem());
	}
	
	public static void registerItemVariants() {
		lagerBottle.registerModelBakeryVariants(LagerTypes.class);
		hopAleBottle.registerModelBakeryVariants(HopAleTypes.class);
	}
}
