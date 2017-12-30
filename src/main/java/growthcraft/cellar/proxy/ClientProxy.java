package growthcraft.cellar.proxy;

import growthcraft.cellar.init.GrowthcraftCellarBlocks;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        registerRenders();
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    @Override
    public void registerRenders() {
        GrowthcraftCellarBlocks.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {

    }

    @Override
    public void registerSpecialRenders() {

    }
}
