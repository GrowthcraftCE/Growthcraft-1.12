package growthcraft.grapes;

import growthcraft.core.shared.compat.Compat;
import growthcraft.grapes.common.CommonProxy;
import growthcraft.grapes.common.Init;
import growthcraft.grapes.common.compat.rustic.Recipes;
import growthcraft.grapes.common.compat.thaumcraft.GrapesAspectRegistry;
import growthcraft.grapes.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
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
		dependencies = "required-after:" + growthcraft.cellar.shared.Reference.MODID)
public class GrowthcraftGrapes {
	static final String CLIENT_PROXY_CLASS = "growthcraft.grapes.client.ClientProxy";
	static final String SERVER_PROXY_CLASS = "growthcraft.grapes.common.CommonProxy";

	@Mod.Instance(Reference.MODID)
	public static GrowthcraftGrapes instance;

	@SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		Init.preInitBlocks();
		Init.preInitItems();
		Init.preInitFluids();

		proxy.preInit();
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.init();
		Init.initBoozes();
		if (Compat.isModAvailable_Rustic())
			Recipes.initBoozes();
		Init.initRecipes();
		Init.registerRecipes();
		if (Loader.isModLoaded("thaumcraft")) {
			GrapesAspectRegistry.register();
		}
	}

	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
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
	public void lootLoad(LootTableLoadEvent evt) {
		Init.lootLoad(evt);
	}
}