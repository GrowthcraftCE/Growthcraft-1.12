package growthcraft.cellar;

import growthcraft.cellar.client.gui.GuiHandler;
import growthcraft.cellar.common.CommonProxy;
import growthcraft.cellar.common.Init;
import growthcraft.cellar.common.handlers.EventHandlerLivingUpdateEventCellar;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.booze.modifier.GrowthcraftModifierFunctions;
import growthcraft.cellar.common.lib.network.PacketPipeline;
import growthcraft.cellar.shared.booze.CellarBoozeBuilderFactory;
import growthcraft.cellar.shared.config.UserApis;
import growthcraft.cellar.common.stats.GrowthcraftCellarAchievements;
import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.client.gui.GrowthcraftGuiProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftCellar {
    static final String CLIENT_PROXY_CLASS = "growthcraft.cellar.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.cellar.common.CommonProxy";
    
	@Mod.Instance(Reference.MODID)
    public static GrowthcraftCellar instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;
    
    @SideOnly(Side.CLIENT)
    public static GrowthcraftGuiProvider guiProvider = new GuiHandler();
    
	public static final PacketPipeline packetPipeline = new PacketPipeline();

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
    	Init.preInitEffects();
    	
    	GrowthcraftCellarApis.userApis = new UserApis();
		GrowthcraftCellarApis.userApis.getUserBrewingRecipes()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/brewing.json");
		GrowthcraftCellarApis.userApis.getUserCultureRecipes()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/culturing.json");
		GrowthcraftCellarApis.userApis.getUserFermentingRecipes()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/fermenting.json");
		GrowthcraftCellarApis.userApis.getUserHeatSources()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/heatsources.json");
		GrowthcraftCellarApis.userApis.getUserPressingRecipes()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/pressing.json");
		GrowthcraftCellarApis.userApis.getUserYeastEntries()
			.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/cellar/yeast.json");
    	
    	GrowthcraftCellarApis.boozeBuilderFactory = new CellarBoozeBuilderFactory(GrowthcraftCellarApis.userApis);

    	GrowthcraftModifierFunctions.registerBoozeModifierFunctions();
    	
    	Init.preInitHeatSources();
    	Init.registerHeatSources();

        Init.preInitBlocks();
        Init.registerBlocks();

        Init.preInitItems();
        Init.registerItems();

        proxy.preInit();
        proxy.registerTitleEntities();
        GrowthcraftCellarAchievements.instance().preInit();
        Init.registerPotions();
        
        GrowthcraftCellarApis.userApis.preInit();
        GrowthcraftCellarApis.userApis.register();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
    	Init.initYeasts();
    	Init.registerYeasts();
    	packetPipeline.initialise();
    	NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, guiProvider);
        Init.registerRecipes();
        proxy.init();
        
        GrowthcraftCellarApis.userApis.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    	// ... Any custom event buses
    	
    	GrowthcraftCellarApis.userApis.loadConfigs();
    	packetPipeline.postInitialise();
    	GrowthcraftCellarApis.userApis.postInit();
		MinecraftForge.EVENT_BUS.register(new EventHandlerLivingUpdateEventCellar());
    	CellarRegistry.onPostInit();
    }
    
	static {
		FluidRegistry.enableUniversalBucket();
	}
}
