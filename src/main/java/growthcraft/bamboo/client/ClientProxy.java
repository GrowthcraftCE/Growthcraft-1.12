package growthcraft.bamboo.client;

import growthcraft.bamboo.common.CommonProxy;
import growthcraft.bamboo.common.Init;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        Init.registerBlockColorHandlers();
        registerSpecialRenders();
    }

    @Override
    public void postRegisterItems() {
        super.postRegisterItems();
        registerModelBakeryVariants();
    }

    public void registerModelBakeryVariants() {
    }

    public void registerSpecialRenders() {

    }

    @Override
    public void registerStateMappers() {
        Init.setBlockCustomStateMappers();
    }

}