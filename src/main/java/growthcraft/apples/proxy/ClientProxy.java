package growthcraft.apples.proxy;

import growthcraft.apples.init.GrowthcraftApplesBlocks;
import growthcraft.apples.init.GrowthcraftApplesItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        GrowthcraftApplesBlocks.setCustomStateMappers();
        registerRenders();
    }

    @Override
    public void init() {
        GrowthcraftApplesBlocks.registerBlockColorHandlers();

        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    @Override
    public void registerRenders() {
        GrowthcraftApplesBlocks.registerRenders();
        GrowthcraftApplesItems.registerRenders();
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
