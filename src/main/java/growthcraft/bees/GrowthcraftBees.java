package growthcraft.bees;

import growthcraft.bees.common.CommonProxy;
import growthcraft.bees.common.Init;
import growthcraft.bees.common.lib.config.user.UserBeesConfig;
import growthcraft.bees.common.lib.config.user.UserFlowersConfig;
import growthcraft.bees.shared.Reference;
import growthcraft.bees.shared.config.GrowthcraftBeesConfig;
import growthcraft.core.shared.inventory.GrowthcraftGuiProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
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
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
        dependencies = "required-after:" + growthcraft.cellar.shared.Reference.MODID)
public class GrowthcraftBees {
    static final String CLIENT_PROXY_CLASS = "growthcraft.bees.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.bees.common.CommonProxy";

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftBees instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    public static GrowthcraftGuiProvider guiProvider = new GrowthcraftGuiProvider();

    public static final UserBeesConfig userBeesConfig = new UserBeesConfig();
    public static final UserFlowersConfig userFlowersConfig = new UserFlowersConfig();

    public static Logger logger;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        GrowthcraftBeesConfig.preInit(event);
        logger = event.getModLog();

        Init.preInitItems();
        Init.preInitBlocks();
        Init.preInitFluids();

        userBeesConfig.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/bees/bees.json");
        userBeesConfig.preInit();
        userBeesConfig.register();

        userFlowersConfig.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/bees/flowers.json");
        userFlowersConfig.preInit();
        Init.initUserApisDefaults();
        userFlowersConfig.register();

        proxy.preInit();

    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        userBeesConfig.init();
        userFlowersConfig.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, guiProvider);
        proxy.init();
        Init.initBoozes();

        Init.registerRecipes();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        userBeesConfig.loadUserConfig();
        userBeesConfig.postInit();
        userFlowersConfig.loadUserConfig();
        userFlowersConfig.postInit();

        Init.registerItemOres();
        Init.registerFluidOres();
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
        Init.registerBlockItems(registry);
        Init.registerFluidItems(registry);

        proxy.postRegisterItems();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        Init.registerItemRenders();
        Init.registerBlockRender();
        Init.registerFluidRender();
    }

    @SubscribeEvent
    public void registerCraftingRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        Init.registerCraftingRecipes(registry);
    }

    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent evt) {
        Init.lootLoad(evt);
    }
}
