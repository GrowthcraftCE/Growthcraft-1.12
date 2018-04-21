package growthcraft.cellar.client;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.client.gui.GuiCultureJar;
import growthcraft.cellar.client.gui.GuiFermentBarrel;
import growthcraft.cellar.client.gui.GuiFruitPress;
import growthcraft.cellar.client.gui.GuiBrewKettle;
import growthcraft.cellar.common.CommonProxy;
import growthcraft.cellar.common.handlers.EnumHandler.EnumYeast;
import growthcraft.cellar.common.init.GrowthcraftCellarBlocks;
import growthcraft.cellar.common.init.GrowthcraftCellarItems;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
    	registerRenders();
        registerModelBakeryVariants();
        registerSpecialRenders();
    }
    
    @Override
    public void init() {
    	super.init();
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:fruit_press", GuiFruitPress.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:brew_kettle", GuiBrewKettle.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:ferment_barrel", GuiFermentBarrel.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:culture_jar", GuiCultureJar.class);
    }

    @Override
    public void registerRenders() {
    	super.registerRenders();
    	GrowthcraftCellarItems.registerRenders();
    	GrowthcraftCellarBlocks.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
    	GrowthcraftCellarItems.yeast.registerModelBakeryVariants(EnumYeast.class);
    }

    @Override
    public void registerSpecialRenders() {
    	super.registerSpecialRenders();
    	GrowthcraftCellarBlocks.registerSpecialRenders();
    }
}
