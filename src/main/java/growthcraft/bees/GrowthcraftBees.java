package growthcraft.bees;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import growthcraft.bees.init.GrowthcraftBeesBlocks;
import growthcraft.bees.init.GrowthcraftBeesFluids;
import growthcraft.bees.init.GrowthcraftBeesItems;
import growthcraft.bees.proxy.CommonProxy;
import growthcraft.core.GrowthcraftGuiProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftBees {

	@Mod.Instance(Reference.MODID)
    public static GrowthcraftBees instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final GrowthcraftGuiProvider guiProvider = new GrowthcraftGuiProvider();
    
    // REVISE_TEAM
    public static Logger logger = LogManager.getLogger(Reference.MODID);

    
    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        GrowthcraftBeesItems.preInit();
        GrowthcraftBeesItems.register();
        
        GrowthcraftBeesBlocks.preInit();
        GrowthcraftBeesBlocks.register();

        GrowthcraftBeesFluids.preInit();
        GrowthcraftBeesFluids.register();
        
        proxy.preInit();

    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, guiProvider);
    	proxy.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

    }

}
