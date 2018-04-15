package growthcraft.cellar;

import growthcraft.cellar.client.gui.GuiHandler;
import growthcraft.cellar.common.CommonProxy;
import growthcraft.cellar.common.booze.GrowthcraftModifierFunctions;
import growthcraft.cellar.common.init.*;
import growthcraft.cellar.common.lib.CellarRegistry;
import growthcraft.cellar.common.lib.network.PacketPipeline;
import growthcraft.cellar.common.stats.GrowthcraftCellarAchievements;
import growthcraft.cellar.common.util.CellarBoozeBuilderFactory;
import growthcraft.cellar.common.util.UserApis;
import growthcraft.core.shared.client.gui.GrowthcraftGuiProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftCellar {
    // REVISE_TEAM
	
	@Mod.Instance(Reference.MODID)
    public static GrowthcraftCellar instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;
    
    public static CellarBoozeBuilderFactory boozeBuilderFactory;
    public static UserApis userApis = new UserApis();
    
    public static GrowthcraftGuiProvider guiProvider = new GuiHandler();
    
    public static Logger logger = LogManager.getLogger(Reference.MODID);

	public static EventBus CELLAR_BUS = new EventBus();
	
	public static final PacketPipeline packetPipeline = new PacketPipeline();

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
    	
    	GrowthcraftCellarHeatsources.init();
    	GrowthcraftCellarHeatsources.register();

        GrowthcraftCellarBlocks.init();
        GrowthcraftCellarBlocks.register();

        GrowthcraftCellarItems.init();
        GrowthcraftCellarItems.register();

        proxy.preInit();
        proxy.registerTitleEntities();
        GrowthcraftCellarAchievements.instance().init();
        GrowthcraftCellarPotions.registerPotions();
        
        userApis.preInit();
        userApis.register();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
    	GrowthcraftCellarYeasts.init();
    	GrowthcraftCellarYeasts.register();
    	packetPipeline.initialise();
    	NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, guiProvider);
        GrowthcraftCellarRecipes.registerCraftingRecipes();
        proxy.init();
        
        userApis.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    	// ... Any custom event buses
    	
    	userApis.loadConfigs();
    	packetPipeline.postInitialise();
    	userApis.postInit();
    	GrowthcraftCellarEvents.registerEvents();
    	CellarRegistry.onPostInit();
    }
    
	static {
		FluidRegistry.enableUniversalBucket();
	}
}
