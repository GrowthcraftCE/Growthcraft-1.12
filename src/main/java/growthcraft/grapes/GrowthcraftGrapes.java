package growthcraft.grapes;

import growthcraft.grapes.common.CommonProxy;
import growthcraft.grapes.common.init.GrowthcraftGrapesBlocks;
import growthcraft.grapes.common.init.GrowthcraftGrapesFluids;
import growthcraft.grapes.common.init.GrowthcraftGrapesItems;
import growthcraft.grapes.common.init.GrowthcraftGrapesRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftGrapes {

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftGrapes instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        GrowthcraftGrapesBlocks.preInit();
        GrowthcraftGrapesBlocks.register();

        GrowthcraftGrapesItems.preInit();
        GrowthcraftGrapesItems.register();
        
    	GrowthcraftGrapesFluids.preInit();
    	GrowthcraftGrapesFluids.register();

        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
    	proxy.init();        
    	GrowthcraftGrapesRecipes.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

    }

}
