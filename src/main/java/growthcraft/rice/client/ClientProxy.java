package growthcraft.rice.client;

import growthcraft.rice.common.CommonProxy;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void postRegisterItems() {
        super.postRegisterItems();
        registerModelBakeryVariants();
    }

    public void registerModelBakeryVariants() {
        //Init.registerItemVariants();
    }

    public void registerSpecialRenders() {

    }

}
