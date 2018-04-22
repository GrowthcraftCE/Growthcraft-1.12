package growthcraft.apples.client;

import growthcraft.apples.common.CommonProxy;
import growthcraft.apples.common.Init;
import growthcraft.apples.shared.init.GrowthcraftApplesBlocks;
import growthcraft.apples.shared.init.GrowthcraftApplesItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        Init.setCustomBlockStateMappers();
        registerRenders();
    }

    @Override
    public void init() {
        Init.registerBlockColorHandlers();

        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    @Override
    public void registerRenders() {
        Init.registerBlockRenders();
        Init.registerItemRenders();
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
