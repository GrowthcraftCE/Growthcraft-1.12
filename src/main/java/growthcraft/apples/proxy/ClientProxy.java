package growthcraft.apples.proxy;

import growthcraft.apples.init.GrowthcraftApplesBlocks;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        GrowthcraftApplesBlocks.setCustomStateMappers();
        registerRenders();
    }

    @Override
    public void init() {
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    @Override
    public void registerRenders() {
        GrowthcraftApplesBlocks.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
        // Not needed at this time.
    }

    @Override
    public void registerSpecialRenders() {
        // Not needed at this time.
    }

}
