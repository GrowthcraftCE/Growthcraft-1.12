package growthcraft.fishtrap;

import growthcraft.fishtrap.init.GrowthcraftFishtrapBlocks;
import growthcraft.fishtrap.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftFishtrap {



    @Mod.Instance(Reference.MODID)
    public static GrowthcraftFishtrap instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        GrowthcraftFishtrapBlocks.init();
        GrowthcraftFishtrapBlocks.register();
        proxy.registerRenders();
        proxy.registerTitleEntities();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }


}
