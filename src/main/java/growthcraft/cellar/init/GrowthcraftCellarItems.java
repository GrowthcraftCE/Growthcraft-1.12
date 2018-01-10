package growthcraft.cellar.init;

import growthcraft.cellar.handlers.EnumHandler.EnumYeast;
import growthcraft.cellar.items.ItemChievDummy;
import growthcraft.cellar.items.ItemYeast;
import growthcraft.core.common.definition.ItemDefinition;
import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftCellarItems {
	// REVISE_ME 0
	
	public static ItemDefinition chievItemDummy;
	public static ItemDefinition yeast;

    public static void init() {
    	chievItemDummy = new ItemDefinition( new ItemChievDummy("achievement_dummy") );
    	yeast = new ItemDefinition( new ItemYeast("yeast") );
    }

    public static void register() {
    	chievItemDummy.register();
    	yeast.getItem().setCreativeTab(tabGrowthcraft);
    	yeast.register();
//        registerItem(chievItemDummy, false);
    }

    public static void registerRenders() {
    	chievItemDummy.registerRender();
    	for( EnumYeast type : EnumYeast.values() )
    		yeast.registerRender(type.ordinal(), "yeast_" + type.toString().toLowerCase() /*yeast.getItem().getUnlocalizedName(yeast.asStack()).substring(5)*/ );  // Skip prefix "items."
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
