package growthcraft.cellar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import growthcraft.cellar.proxy.CommonProxy;
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
    
    // REVISE_TEAM
    public static Logger logger = LogManager.getLogger(Reference.MODID);

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        //GrowthcraftGrapesItems.init();
        //GrowthcraftGrapesItems.register();

        proxy.registerRenders();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.registerModelBakeryVariants();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

    }
}
