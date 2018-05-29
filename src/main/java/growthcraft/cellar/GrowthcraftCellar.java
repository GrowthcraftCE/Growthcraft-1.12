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
import growthcraft.core.shared.inventory.GrowthcraftGuiProvider;
import growthcraft.cellar.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
     dependencies = "required-after:"+growthcraft.core.shared.Reference.MODID)
public class GrowthcraftCellar {
    static final String CLIENT_PROXY_CLASS = "growthcraft.cellar.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.cellar.common.CommonProxy";
    
	@Mod.Instance(Reference.MODID)
    public static GrowthcraftCellar instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;
    
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
        Init.preInitItems();

        proxy.preInit();
        proxy.registerTitleEntities();
//OFF        GrowthcraftCellarAchievements.instance().preInit();
        
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
    
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();

        Init.registerBlocks(registry);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();
		
        Init.registerBlockItems(registry);
        Init.registerItems(registry);
        
        proxy.postRegisterItems();
	}

	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event)
	{
		IForgeRegistry<Potion> registry = event.getRegistry();

        Init.registerPotions(registry);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
        Init.registerItemRenders();
        Init.registerBlockRenders();
	}
    
	static {
		FluidRegistry.enableUniversalBucket();
	}
}
