package growthcraft.cellar.init;

import growthcraft.cellar.items.ItemChievDummy;
import growthcraft.core.common.definition.ItemDefinition;

public class GrowthcraftCellarItems {
	// REVISE_ME 0
	
	public static ItemDefinition chievItemDummy;

    public static void init() {
    	chievItemDummy = new ItemDefinition( new ItemChievDummy("achievement_dummy") );
    }

    public static void register() {
    	chievItemDummy.register();
//        registerItem(chievItemDummy, false);
    }

    public static void registerRenders() {
    	chievItemDummy.registerRender();
//        registerRender(chievItemDummy);
    }

/*    public static void registerItem(Item item, boolean bAddToCreativeTab) {
        if( bAddToCreativeTab )
        	item.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(item);
    }

    public static void  registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    } */
}
