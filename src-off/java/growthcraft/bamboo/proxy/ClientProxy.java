package growthcraft.bamboo.proxy;

import growthcraft.bamboo.init.GrowthcraftBambooBlocks;
import growthcraft.bamboo.init.GrowthcraftBambooItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
        GrowthcraftBambooBlocks.setCustomStateMappers();
        registerRenders();
    }

    @Override
    public void init() {
        GrowthcraftBambooBlocks.registerBlockColorHandlers();

        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    @Override
    public void registerRenders() {
        GrowthcraftBambooBlocks.registerRenders();
        GrowthcraftBambooItems.registerRenders();
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