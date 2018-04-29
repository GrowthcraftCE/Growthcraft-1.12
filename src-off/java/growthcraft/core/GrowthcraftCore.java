package growthcraft.core;

import growthcraft.core.shared.GrowthcraftCoreApis;
import growthcraft.core.shared.Reference;
import growthcraft.core.common.CommonProxy;
import growthcraft.core.common.Init;
import growthcraft.core.common.creativetabs.TabGrowthcraft;
import growthcraft.core.shared.item.recipes.ShapelessItemComparableRecipe;
import growthcraft.core.shared.item.recipes.ShapelessMultiRecipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftCore {

	public static final String CLIENT_PROXY_CLASS = "growthcraft.core.client.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "growthcraft.core.common.CommonProxy";
	
    @Mod.Instance(Reference.MODID)
    public static GrowthcraftCore instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	GrowthcraftCoreApis.tabGrowthcraft = new TabGrowthcraft();
    	
        Init.preInitBlocks();
        Init.registerBlocks();
        
        Init.preInitItems();
        Init.registerItems();
        
        proxy.registerRenders();
        proxy.registerTitleEntities();
        
        RecipeSorter.register("minecraft:shapeless_comparator", ShapelessItemComparableRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("minecraft:shapeless_multi", ShapelessMultiRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        proxy.registerSpecialRenders();
        proxy.registerModelBakeryVariants();
        Init.registerRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

}
