package growthcraft.cellar.proxy;

import growthcraft.cellar.init.GrowthcraftCellarItems;
import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.client.gui.GuiFermentBarrel;
import growthcraft.cellar.client.gui.PREVGuiBrewKettle;
import growthcraft.cellar.init.GrowthcraftCellarBlocks;

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
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:brew_kettle", PREVGuiBrewKettle.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:ferment_barrel", GuiFermentBarrel.class);
    }

    @Override
    public void registerRenders() {
    	super.registerRenders();
    	GrowthcraftCellarItems.registerRenders();
    	GrowthcraftCellarBlocks.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {

    }

    @Override
    public void registerSpecialRenders() {

    }
}
