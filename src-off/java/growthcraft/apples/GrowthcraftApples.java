package growthcraft.apples;

import growthcraft.apples.common.CommonProxy;
import growthcraft.apples.common.Init;
import growthcraft.apples.shared.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
     dependencies = "required-after:"+growthcraft.core.shared.Reference.MODID)
public class GrowthcraftApples {
    static final String CLIENT_PROXY_CLASS = "growthcraft.apples.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.apples.common.CommonProxy";

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftApples instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        // Register the blocks
    	Init.preInitBlocks();
        Init.registerBlocks();

        Init.preInitItems();
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
