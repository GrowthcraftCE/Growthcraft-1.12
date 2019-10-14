package growthcraft.hops.client;

import growthcraft.hops.common.CommonProxy;
import growthcraft.hops.common.Init;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        super.init();
        Init.registerItemColorHandlers();
        registerSpecialRenders();
    }

    @Override
    public void postRegisterItems() {
        super.postRegisterItems();
        registerModelBakeryVariants();
    }

    public void registerModelBakeryVariants() {
        Init.registerItemVariants();
    }

    public void registerSpecialRenders() {

    }
}