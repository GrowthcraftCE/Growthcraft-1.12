package growthcraft.milk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import growthcraft.milk.common.CommonProxy;
import growthcraft.milk.common.Init;
import growthcraft.milk.common.handlers.EntityDropsHandler;
import growthcraft.milk.shared.GrowthcraftMilkUserApis;
import growthcraft.milk.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
     dependencies = "required-after:"+growthcraft.core.shared.Reference.MODID+";"+
    		        "after:"+growthcraft.bees.shared.Reference.MODID)
public class GrowthcraftMilk {
    static final String CLIENT_PROXY_CLASS = "growthcraft.milk.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.milk.common.CommonProxy";

    @Mod.Instance(Reference.MODID)
    public static GrowthcraftMilk instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

	public static Logger logger = LogManager.getLogger(Reference.MODID);

	public static final GrowthcraftMilkUserApis userApis = new GrowthcraftMilkUserApis();
	
    static {
        FluidRegistry.enableUniversalBucket();
    }
    
	// Events
	public static final EventBus MILK_BUS = new EventBus();


    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
    	
    	userApis.setConfigDirectory(event.getModConfigurationDirectory());
    	
        Init.preInitFluids();
        Init.preInitBlocks();
        Init.preInitItems();
        Init.preInitEffects();
    	Init.perInitCheese();
        userApis.preInit();

        userApis.register();
        
        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
        Init.initFluids();
        Init.initRecipes();
        userApis.init();
        userApis.loadConfigs();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EntityDropsHandler());
        userApis.postInit();
        
		Init.registerItemOres();
		Init.registerFluidOres();
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
		Init.registerFluidBlocks(registry);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();
		
        Init.registerItems(registry);
        Init.registerBlockItems(registry);
		Init.registerFluidItems(registry);
        
        proxy.postRegisterItems();
	}
    
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
        Init.registerItemRenders();
        Init.registerBlockRenders();
		Init.registerFluidRenders();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerCraftingRecipes(RegistryEvent.Register<IRecipe> event)
	{
		IForgeRegistry<IRecipe> registry = event.getRegistry();
		Init.registerCraftingRecipes(registry);
	}
	

}
