package growthcraft.bamboo.proxy;

import growthcraft.bamboo.init.GrowthcraftBambooBlocks;

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
        GrowthcraftBambooBlocks.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {

    }

    @Override
    public void registerSpecialRenders() {

    }
}