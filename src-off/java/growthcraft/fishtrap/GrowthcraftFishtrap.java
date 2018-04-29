package growthcraft.fishtrap;

import growthcraft.fishtrap.common.CommonProxy;
import growthcraft.fishtrap.common.Init;
import growthcraft.fishtrap.shared.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
	dependencies = "required-after:"+growthcraft.core.shared.Reference.MODID)
public class GrowthcraftFishtrap {
	private static final String CLIENT_PROXY_CLASS = "growthcraft.fishtrap.client.ClientProxy";
	private static final String SERVER_PROXY_CLASS = "growthcraft.fishtrap.common.CommonProxy";
	
    @Mod.Instance(Reference.MODID)
    public static GrowthcraftFishtrap instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        Init.initBlocks();
        Init.registerBlocks();
        proxy.registerRenders();
        proxy.registerTitleEntities();
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
