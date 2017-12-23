package growthcraft.cellar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import growthcraft.cellar.api.CellarRegistry;
import growthcraft.cellar.common.booze.GrowthcraftModifierFunctions;
import growthcraft.cellar.events.CellarEvents;
import growthcraft.cellar.init.GrowthcraftCellarPotions;
import growthcraft.cellar.init.GrowthcraftCellarItems;
import growthcraft.cellar.proxy.CommonProxy;
import growthcraft.cellar.stats.GrowthcraftCellarAchievements;
import growthcraft.cellar.util.CellarBoozeBuilderFactory;
import growthcraft.cellar.util.UserApis;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftCellar {
    @Mod.Instance(Reference.MODID)
    public static GrowthcraftCellar instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;
    
    public static CellarBoozeBuilderFactory boozeBuilderFactory;
    private static UserApis userApis = new UserApis();
    
    // REVISE_TEAM
    public static Logger logger = LogManager.getLogger(Reference.MODID);

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
		userApis.getUserBrewingRecipes()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/brewing.json");
		userApis.getUserCultureRecipes()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/culturing.json");
		userApis.getUserFermentingRecipes()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/fermenting.json");
		userApis.getUserHeatSources()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/heatsources.json");
		userApis.getUserPressingRecipes()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/pressing.json");
		userApis.getUserYeastEntries()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/yeast.json");
    	
    	GrowthcraftModifierFunctions.registerBoozeModifierFunctions();
    	boozeBuilderFactory = new CellarBoozeBuilderFactory(userApis);
    	
        GrowthcraftCellarItems.init();
        GrowthcraftCellarItems.register();

        proxy.registerRenders();
        GrowthcraftCellarAchievements.instance().init();
        GrowthcraftCellarPotions.registerPotions();
        
        userApis.preInit();
        userApis.register();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.registerModelBakeryVariants();
        
        userApis.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    	userApis.loadConfigs();
    	userApis.postInit();
    	CellarEvents.registerEvents();
    	CellarRegistry.onPostInit();
    }
    
	static {
		FluidRegistry.enableUniversalBucket();
	}
}
