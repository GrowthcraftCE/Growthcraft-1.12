package growthcraft.cellar.proxy;

import growthcraft.cellar.init.GrowthcraftCellarItems;

public class ClientProxy extends CommonProxy {
    @Override
    public void init() {
    }

    @Override
    public void registerRenders() {
    	GrowthcraftCellarItems.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {

    }
}
