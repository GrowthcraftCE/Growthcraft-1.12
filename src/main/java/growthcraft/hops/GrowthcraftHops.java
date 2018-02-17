package growthcraft.hops;

import growthcraft.hops.init.GrowthcraftHopsBlocks;
import growthcraft.hops.init.GrowthcraftHopsFluids;
import growthcraft.hops.init.GrowthcraftHopsItems;
import growthcraft.hops.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftHops {

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftHops instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {

        GrowthcraftHopsBlocks.init();
        GrowthcraftHopsBlocks.register();

        GrowthcraftHopsItems.init();
        GrowthcraftHopsItems.register();
        
        GrowthcraftHopsFluids.init();
        GrowthcraftHopsFluids.register();

        proxy.preInit();
        proxy.registerTileEntities();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

    }
}
