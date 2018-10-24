package growthcraft.rice;

import growthcraft.rice.common.CommonProxy;
import growthcraft.rice.common.Init;
import growthcraft.rice.shared.GrowthcraftRiceUserApi;
import growthcraft.rice.shared.Reference;
import growthcraft.rice.shared.config.GrowthcraftRiceConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftRice {
    static final String CLIENT_PROXY_CLASS = "growthcraft.rice.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.rice.common.CommonProxy";

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftRice instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger(Reference.MODID);

    // TODO: Implement configuration options.
    public static final GrowthcraftRiceUserApi userApis = new GrowthcraftRiceUserApi();

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        userApis.setConfigDirectory(event.getModConfigurationDirectory());

        GrowthcraftRiceConfig.preInit(event);

        Init.preInitBlocks();
        Init.preInitItems();
        Init.preInitFluids();

        userApis.preInit();
        userApis.register();

        proxy.preInit();
        proxy.registerTileEntities();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
        Init.initBoozes();

        Init.initRecipes();
        Init.registerRecipes();

        userApis.init();
        userApis.loadConfigs();

        // TODO: Rustic Compat

        // TODO: Thaumcraft Compat

    }

    @Mod.EventHandler
    public static void  postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        userApis.postInit();

        Init.registerItemOres();
    }

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        Init.registerBlocks(registry);
        Init.registerFluidBlocks(registry);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        Init.registerItems(registry);
        proxy.postRegisterItems();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        Init.registerItemRenders();
        Init.registerBlockRenders();
        Init.registerFluidRenders();
    }

    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent event) {
        Init.lootLoad(event);
    }

}
