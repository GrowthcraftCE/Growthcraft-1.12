package growthcraft.cellar.client;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.client.gui.GuiBrewKettle;
import growthcraft.cellar.client.gui.GuiCultureJar;
import growthcraft.cellar.client.gui.GuiFermentBarrel;
import growthcraft.cellar.client.gui.GuiFruitPress;
import growthcraft.cellar.client.gui.GuiHandler;
import growthcraft.cellar.common.CommonProxy;
import growthcraft.cellar.common.Init;
import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import growthcraft.cellar.shared.init.GrowthcraftCellarItems.EnumYeast;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
        registerSpecialRenders();
    }
    
    @Override
    public void postRegisterItems() {
    	super.postRegisterItems();
    	registerModelBakeryVariants();
    }
    
    @Override
    public void init() {
    	super.init();
    	GrowthcraftCellar.guiProvider = new GuiHandler();
    	NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, GrowthcraftCellar.guiProvider);
    	
    	registerSpecialRenders();
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:fruit_press", GuiFruitPress.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:brew_kettle", GuiBrewKettle.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:ferment_barrel", GuiFermentBarrel.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:culture_jar", GuiCultureJar.class);
    }

    public void registerModelBakeryVariants() {
    	GrowthcraftCellarItems.yeast.registerModelBakeryVariants(EnumYeast.class);
    }

    public void registerSpecialRenders() {
    	Init.registerBlockSpecialRenders();
    }
}
