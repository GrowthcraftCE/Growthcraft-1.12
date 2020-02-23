package growthcraft.apples;

import growthcraft.apples.common.CommonProxy;
import growthcraft.apples.common.Init;
import growthcraft.apples.common.compat.rustic.RusticApplesRecipes;
import growthcraft.apples.shared.Reference;
import growthcraft.core.shared.compat.Compat;
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

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
        dependencies = "required-after:" + growthcraft.core.shared.Reference.MODID)
public class GrowthcraftApples {
    static final String CLIENT_PROXY_CLASS = "growthcraft.apples.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.apples.common.CommonProxy";

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftApples instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        // Register the blocks
        Init.preInitBlocks();
        Init.preInitItems();
        Init.preInitFluids();

        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
        Init.initBoozes();
        if (Compat.isModAvailableRustic())
            RusticApplesRecipes.initBoozes();
        Init.registerRecipes();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        Init.registerBlockOres();
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

        proxy.registerStateMappers();
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        Init.registerItems(registry);
        Init.registerBlockItems(registry);

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
