package growthcraft.fishtrap;

import growthcraft.fishtrap.common.CommonProxy;
import growthcraft.fishtrap.common.Init;
import growthcraft.fishtrap.shared.Reference;
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
	dependencies = "required-after:"+growthcraft.core.shared.Reference.MODID)
public class GrowthcraftFishtrap {
	private static final String CLIENT_PROXY_CLASS = "growthcraft.fishtrap.client.ClientProxy";
	private static final String SERVER_PROXY_CLASS = "growthcraft.fishtrap.common.CommonProxy";
	
    @Mod.Instance(Reference.MODID)
    public static GrowthcraftFishtrap instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        Init.initBlocks();
        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();

        Init.registerRecipes();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit();
    }

	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();

        Init.registerBlocks(registry);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();
		
        Init.registerBlockItems(registry);
        
        proxy.postRegisterItems();
	}
    
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
        Init.registerBlockRenders();
	}
}
