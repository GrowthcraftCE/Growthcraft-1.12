package growthcraft.bees;

import growthcraft.bees.common.CommonProxy;
import growthcraft.bees.common.Init;
import growthcraft.bees.common.lib.config.user.UserBeesConfig;
import growthcraft.bees.common.lib.config.user.UserFlowersConfig;
import growthcraft.bees.shared.Reference;
import growthcraft.core.shared.client.gui.GrowthcraftGuiProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
     dependencies = "required-after:"+growthcraft.cellar.shared.Reference.MODID)
public class GrowthcraftBees {
    static final String CLIENT_PROXY_CLASS = "growthcraft.bees.client.ClientProxy";
    static final String SERVER_PROXY_CLASS = "growthcraft.bees.common.CommonProxy";

	@Mod.Instance(Reference.MODID)
    public static GrowthcraftBees instance;

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @SideOnly(Side.CLIENT)
    public static final GrowthcraftGuiProvider guiProvider = new GrowthcraftGuiProvider();
    
	public static final UserBeesConfig userBeesConfig = new UserBeesConfig();
	public static final UserFlowersConfig userFlowersConfig = new UserFlowersConfig();
    
    
    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
    	Init.preInitItems();
        Init.registerItems();
        
        Init.preInitBlocks();
        Init.registerBlocks();

        Init.preInitFluids();
        Init.registerFluids();
        
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
    	proxy.init();
    	
    	Init.registerRecipes();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    	userBeesConfig.loadUserConfig();
    	userBeesConfig.postInit();
    	userFlowersConfig.loadUserConfig();
    	userFlowersConfig.postInit();
    	
    	
    }

}
