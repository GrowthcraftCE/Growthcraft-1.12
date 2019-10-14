package growthcraft.hops;

import growthcraft.hops.common.CommonProxy;
import growthcraft.hops.common.Init;
import growthcraft.hops.common.handler.HarvestDropsEventHandler;
import growthcraft.hops.shared.Reference;
import growthcraft.hops.shared.config.GrowthcraftHopsConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
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

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GrowthcraftHops {
    static final String CLIENT_PROXY_CLASS = "growthcraft.hops.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.hops.common.CommonProxy";

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftHops instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {

        GrowthcraftHopsConfig.preInit(event);
        MinecraftForge.EVENT_BUS.register(new HarvestDropsEventHandler());

        Init.preInitBlocks();
        Init.preInitItems();
        Init.preInitFluids();
        Init.preInitLootTables();

        proxy.preInit();
        proxy.registerTileEntities();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
        Init.initBoozes();
        Init.registerRecipes();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
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

}
