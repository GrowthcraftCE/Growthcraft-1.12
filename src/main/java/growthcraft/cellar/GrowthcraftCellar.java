package growthcraft.cellar;

import growthcraft.cellar.init.GrowthcraftCellarBlocks;
import growthcraft.cellar.proxy.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
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

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        // Fluids First


        GrowthcraftCellarBlocks.init();

        GrowthcraftCellarBlocks.register();

        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.registerModelBakeryVariants();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        // Any custom event buses
    }
}
