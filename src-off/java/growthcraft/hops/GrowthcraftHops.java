package growthcraft.hops;

import growthcraft.hops.common.CommonProxy;
import growthcraft.hops.common.Init;
import growthcraft.hops.shared.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftHops {
    static final String CLIENT_PROXY_CLASS = "growthcraft.hops.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.hops.common.CommonProxy";

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftHops instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {

        Init.preInitBlocks();
        Init.registerBlocks();

        Init.preInitItems();
        Init.registerItems();
        
        Init.preInitFluids();
        Init.registerFluids();

        proxy.preInit();
        proxy.registerTileEntities();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
        Init.registerRecipes();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

    }
}
