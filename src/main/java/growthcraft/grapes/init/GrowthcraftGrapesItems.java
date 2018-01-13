package growthcraft.grapes.init;

import growthcraft.cellar.common.item.ItemBoozeBottle;
import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.common.definition.ItemTypeDefinition;
import growthcraft.grapes.Reference;
import growthcraft.grapes.handlers.EnumHandler;
import growthcraft.grapes.items.ItemGrape;
import growthcraft.grapes.items.ItemGrapeSeed;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrowthcraftGrapesItems {
    public static ItemDefinition grape;
    public static ItemDefinition grape_seed;
	public static ItemTypeDefinition<ItemBoozeBottle> grapeWine;

    public static void init() {
        grape = new ItemDefinition( new ItemGrape("grape", 1, 1, false) );
        grape_seed = new ItemDefinition( new ItemGrapeSeed("grape_seed") );
        grapeWine = null;	// Is registered in GrowthcraftGrapesFluids
    }

    public static void register() {
        // registerItem(grape);
    	grape.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
    	grape.register();
        // registerItem(grape_seed);
    	grape_seed.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
    	grape_seed.register();
    }

    public static void registerRenders() {
        // registerRender(grape_seed);
    	grape_seed.registerRender();

/*        for ( int i = 0; i < EnumHandler.GrapeTypes.values().length; i++ ) {
           EnumHandler.GrapeTypes type = EnumHandler.GrapeTypes.values()[i];
           registerRender(grape, type.getVariantID(), "grape_" + type.getName());
        } */
    	grape.registerRenders(EnumHandler.GrapeTypes.class);
    }

    public static void registerItem(Item item) {
        item.setCreativeTab(GrowthcraftCore.tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));

    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
}
