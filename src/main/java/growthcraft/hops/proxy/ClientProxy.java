package growthcraft.hops.proxy;

import growthcraft.hops.init.GrowthcraftHopsItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        registerRenders();
    }

    @Override
    public void init() {
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    @Override
    public void registerRenders() {
        GrowthcraftHopsItems.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {

    }

    @Override
    public void registerSpecialRenders() {

    }
}