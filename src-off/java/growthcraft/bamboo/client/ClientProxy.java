package growthcraft.bamboo.client;

import growthcraft.bamboo.common.CommonProxy;
import growthcraft.bamboo.common.Init;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
        Init.setBlockCustomStateMappers();
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