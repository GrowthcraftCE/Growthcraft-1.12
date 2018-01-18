package growthcraft.milk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import growthcraft.milk.handlers.EntityDropsHandler;
import growthcraft.milk.init.GrowthcraftMilkBlocks;
import growthcraft.milk.init.GrowthcraftMilkCheeses;
import growthcraft.milk.init.GrowthcraftMilkEffects;
import growthcraft.milk.init.GrowthcraftMilkFluids;
import growthcraft.milk.init.GrowthcraftMilkItems;
import growthcraft.milk.proxy.CommonProxy;
import growthcraft.milk.utils.GrowthcraftMilkUserApis;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftMilk {

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftMilk instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

	public static Logger logger = LogManager.getLogger(Reference.MODID);

	public static final GrowthcraftMilkUserApis userApis = new GrowthcraftMilkUserApis();
	
    static {
        FluidRegistry.enableUniversalBucket();
    }
    
	// Events
	public static final EventBus MILK_BUS = new EventBus();


    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
    	
    	GrowthcraftMilkCheeses.perInit();
    	
    	userApis.setConfigDirectory(event.getModConfigurationDirectory());
    	
        GrowthcraftMilkFluids.preInit();
        GrowthcraftMilkBlocks.preInit();
        GrowthcraftMilkItems.preInit();
        GrowthcraftMilkEffects.preInit();
        userApis.preInit();

        GrowthcraftMilkFluids.register();
        GrowthcraftMilkBlocks.register();
        GrowthcraftMilkItems.register();
        userApis.register();
        
        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
        GrowthcraftMilkFluids.init();
        userApis.init();
        userApis.loadConfigs();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EntityDropsHandler());
        userApis.postInit();
    }

}
