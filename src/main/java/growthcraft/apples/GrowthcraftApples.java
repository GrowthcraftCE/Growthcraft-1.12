package growthcraft.apples;

import growthcraft.apples.init.GrowthcraftApplesBlocks;
import growthcraft.apples.init.GrowthcraftApplesItems;
import growthcraft.apples.init.GrowthcraftApplesRecipes;
import growthcraft.apples.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftApples {

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftApples instance;

    @SidedProxy(serverSide = growthcraft.apples.Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        GrowthcraftApplesBlocks.preInit();
        GrowthcraftApplesItems.preInit();
        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        GrowthcraftApplesRecipes.registerRecipes();
        proxy.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

    }
}
