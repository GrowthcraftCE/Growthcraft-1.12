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
    }

    public static void registerRenders() {
    	chievItemDummy.registerRender();
//    	for( EnumYeast type : EnumYeast.values() )
//    		yeast.registerRender(type.ordinal(), "yeast_" + type.toString().toLowerCase() );
    	yeast.registerRenders(EnumYeast.values());
    }
}
