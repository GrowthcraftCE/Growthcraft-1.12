package growthcraft.bees.proxy;

import growthcraft.bees.init.GrowthcraftBeesItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {
    	GrowthcraftBeesItems.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
    	GrowthcraftBeesItems.registerItemVariants();
    }

    @Override
    public void registerSpecialRenders() {
    }
}
