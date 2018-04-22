package growthcraft.milk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import growthcraft.milk.common.CommonProxy;
import growthcraft.milk.common.handlers.EntityDropsHandler;
import growthcraft.milk.shared.GrowthcraftMilkUserApis;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.init.GrowthcraftMilkBlocks;
import growthcraft.milk.shared.init.GrowthcraftMilkCheeses;
import growthcraft.milk.shared.init.GrowthcraftMilkEffects;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import growthcraft.milk.shared.init.GrowthcraftMilkItems;
import growthcraft.milk.shared.init.GrowthcraftMilkRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
     dependencies = "required-after:"+growthcraft.core.shared.Reference.MODID+";"+
    		        "after:"+growthcraft.bees.shared.Reference.MODID)
public class GrowthcraftMilk {
    static final String CLIENT_PROXY_CLASS = "growthcraft.milk.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.milk.common.CommonProxy";

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftMilk instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
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
    	
    	userApis.setConfigDirectory(event.getModConfigurationDirectory());
    	
        GrowthcraftMilkFluids.preInit();
        GrowthcraftMilkBlocks.preInit();
        GrowthcraftMilkItems.preInit();
        GrowthcraftMilkEffects.preInit();
    	GrowthcraftMilkCheeses.perInit();
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
        GrowthcraftMilkRecipes.init();
        userApis.init();
        userApis.loadConfigs();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EntityDropsHandler());
        userApis.postInit();
    }

}
