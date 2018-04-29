package growthcraft.bamboo;

import growthcraft.bamboo.common.CommonProxy;
import growthcraft.bamboo.common.Init;
import growthcraft.bamboo.shared.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
	dependencies = "required-after:"+growthcraft.core.shared.Reference.MODID)
public class GrowthcraftBamboo {
    private static final String CLIENT_PROXY_CLASS = "growthcraft.bamboo.client.ClientProxy";
    private static final String SERVER_PROXY_CLASS = "growthcraft.bamboo.common.CommonProxy";
    
    public static Configuration configuration;

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftBamboo instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        Init.initBlocks();
        Init.registerBlocks();
        Init.initItems();
        Init.registerItems();
        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        Init.registerRecipes();
        proxy.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }
}
