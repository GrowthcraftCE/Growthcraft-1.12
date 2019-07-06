package growthcraft.bamboo;

import growthcraft.bamboo.common.CommonProxy;
import growthcraft.bamboo.common.Init;
import growthcraft.bamboo.shared.Reference;
import growthcraft.bamboo.shared.config.GrowthcraftBambooConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
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

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
        dependencies = "required-after:" + growthcraft.core.shared.Reference.MODID)
public class GrowthcraftBamboo {
    private static final String CLIENT_PROXY_CLASS = "growthcraft.bamboo.client.ClientProxy";
    private static final String SERVER_PROXY_CLASS = "growthcraft.bamboo.common.CommonProxy";

    public static Configuration configuration;

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftBamboo instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        GrowthcraftBambooConfig.preInit(event);

        if (GrowthcraftBambooConfig.enableGrowthcraftBamboo) {
            Init.preInitBlocks();
            Init.preInitItems();
            proxy.preInit();
        }
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        if (GrowthcraftBambooConfig.enableGrowthcraftBamboo) {
            Init.registerRecipes();
            Init.registerSmeltingRecipes();
            proxy.init();
        }
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        // Nothing to do here at this time.
    }

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        if (GrowthcraftBambooConfig.enableGrowthcraftBamboo) {
            IForgeRegistry<Block> registry = event.getRegistry();

            Init.registerBlocks(registry);

            proxy.registerStateMappers();
        }
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        if (GrowthcraftBambooConfig.enableGrowthcraftBamboo) {
            IForgeRegistry<Item> registry = event.getRegistry();

            Init.registerItems(registry);
            Init.registerBlockItems(registry);

            proxy.postRegisterItems();
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        if (GrowthcraftBambooConfig.enableGrowthcraftBamboo) {
            Init.registerItemRenders();
            Init.registerBlockRenders();
        }
    }
}
