package growthcraft.bamboo;

import growthcraft.bamboo.init.GrowthcraftBambooBlocks;
import growthcraft.bamboo.init.GrowthcraftBambooItems;
import growthcraft.bamboo.init.GrowthcraftBambooRecipes;
import growthcraft.bamboo.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftBamboo {

    public static Configuration configuration;

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftBamboo instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        GrowthcraftBambooBlocks.init();
        GrowthcraftBambooBlocks.register();
        GrowthcraftBambooItems.init();
        GrowthcraftBambooItems.register();
        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        GrowthcraftBambooRecipes.registerRecipes();
        proxy.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }
}
