package growthcraft.core;

import growthcraft.core.shared.GrowthcraftCoreApis;
import growthcraft.core.shared.Reference;
import growthcraft.core.shared.compat.Compat;
import growthcraft.core.shared.compat.forestry.InitForestry;
import growthcraft.core.shared.compat.rustic.InitRustic;
import growthcraft.core.common.CommonProxy;
import growthcraft.core.common.Init;
import growthcraft.core.common.creativetabs.TabGrowthcraft;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.item.recipes.ShapelessItemComparableRecipe;
import growthcraft.core.shared.item.recipes.ShapelessMultiRecipe;
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
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
        dependencies = "after:rustic;" +
                "after:forestry")
public class GrowthcraftCore {

    public static final String CLIENT_PROXY_CLASS = "growthcraft.core.client.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "growthcraft.core.common.CommonProxy";

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftCore instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final GrowthcraftCoreConfig config = new GrowthcraftCoreConfig();
    public static final Logger logger = LogManager.getLogger(Reference.MODID);

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        GrowthcraftCoreApis.tabGrowthcraft = new TabGrowthcraft();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    @SuppressWarnings("deprecation")
    public void preInit(FMLPreInitializationEvent event) {
        config.preInit(event, "growthcraft/growthcraft-core.cfg");

        Init.preInitBlocks();
        Init.preInitItems();

        if (Compat.isModAvailable_Rustic()) {
            InitRustic.preInitBlocks();
            InitRustic.preInitItems();
            InitRustic.preInitFluids();
        }
        if (Compat.isModAvailable_Forestry())
            InitForestry.preInitFluids();

        proxy.preInit();

        RecipeSorter.register("minecraft:shapeless_comparator", ShapelessItemComparableRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("minecraft:shapeless_multi", ShapelessMultiRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        Init.registerBlocks(registry);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        Init.registerBlockItems(registry);
        Init.registerItems(registry);

        proxy.postRegisterItems();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        Init.registerRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        Init.registerBlockOres();
        if (Compat.isModAvailable_Rustic())
            InitRustic.registerBlockOres();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        Init.registerItemRenders();
        Init.registerBlockRenders();
    }

}
