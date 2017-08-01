package growthcraft.milk;

import growthcraft.milk.init.GrowthcraftMilkBlocks;
import growthcraft.milk.init.GrowthcraftMilkItems;
import growthcraft.milk.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftMilk {

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftMilk instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {

        GrowthcraftMilkBlocks.init();
        GrowthcraftMilkItems.init();

        GrowthcraftMilkBlocks.register();
        GrowthcraftMilkItems.register();

        proxy.registerRenders();
        proxy.registerSpecialRenders();
        proxy.registerTitleEntities();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.registerModelBakeryVariants();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }





}
