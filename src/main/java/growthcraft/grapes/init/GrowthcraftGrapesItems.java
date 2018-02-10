package growthcraft.grapes.init;

import growthcraft.cellar.common.item.ItemBoozeBottle;
import growthcraft.cellar.util.BoozeRegistryHelper;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.client.utils.ItemRenderUtils;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.common.definition.ItemTypeDefinition;
import growthcraft.grapes.Reference;
import growthcraft.grapes.handlers.EnumHandler;
import growthcraft.grapes.handlers.EnumHandler.GrapeTypes;
import growthcraft.grapes.handlers.EnumHandler.WineTypes;
import growthcraft.grapes.items.ItemGrape;
import growthcraft.grapes.items.ItemGrapeSeed;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class GrowthcraftGrapesItems {
    public static ItemDefinition grape;
    public static ItemDefinition grape_seed;
	public static ItemTypeDefinition<ItemBoozeBottle> grapeWine;

    public static void init() {
        grape = new ItemDefinition( new ItemGrape("grape", 1, 1, false) );
        grape_seed = new ItemDefinition( new ItemGrapeSeed("grape_seed") );
        grapeWine = new ItemTypeDefinition<ItemBoozeBottle>( new ItemBoozeBottle() );
    }

	private static void registerOres()
	{
		OreDictionary.registerOre("foodFruit", GrapeTypes.GREEN.asStack());
		OreDictionary.registerOre("foodFruit", GrapeTypes.PURPLE.asStack());
		OreDictionary.registerOre("foodFruit", GrapeTypes.RED.asStack());
	}
    
    public static void register() {
    	grape.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
    	grape.register();
    	grape_seed.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
    	grape_seed.register();
		grapeWine.register(new ResourceLocation(Reference.MODID, "grapewine"));
		OreDictionary.registerOre("foodGrapejuice", grapeWine.asStack(1, 0));
		
		registerOres();
    }

    public static void registerRenders() {
    	grape_seed.registerRenders(GrapeTypes.class);
    	grape.registerRenders(GrapeTypes.class);
		grapeWine.registerRenders(WineTypes.class);
    }
    
	public static void registerItemColorHandlers() {
		ItemRenderUtils.registerItemColorHandler(GrowthcraftGrapesItems.grapeWine.getItem());
	}

	public static void registerItemVariants() {
		grape_seed.registerModelBakeryVariants(GrapeTypes.class);
    	grape.registerModelBakeryVariants(GrapeTypes.class);
		grapeWine.registerModelBakeryVariants(WineTypes.class);
	}
}
