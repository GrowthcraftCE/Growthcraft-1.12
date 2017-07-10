package growthcraft.core;

import growthcraft.core.creativetabs.TabGrowthcraft;
import growthcraft.core.init.GrowthcraftCoreItems;
import growthcraft.core.init.GrowthcraftCoreRecipes;
import growthcraft.core.proxy.CommonProxy;
import growthcraft.core.utils.GrowthcraftLogger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftCore {

    public static final CreativeTabs tabGrowthcraft = new TabGrowthcraft();

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftCore instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GrowthcraftLogger.getLogger().info(Reference.NAME + " pre-initializing ...");
        GrowthcraftCoreItems.init();
        GrowthcraftCoreItems.register();

        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GrowthcraftLogger.getLogger().info(Reference.NAME + " initializing ...");
        proxy.registerModelBakeryVariants();
        GrowthcraftCoreRecipes.registerRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        GrowthcraftLogger.getLogger().info(Reference.NAME + " post-initializing ...");
    }

}
