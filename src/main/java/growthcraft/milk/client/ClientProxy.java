package growthcraft.milk.client;

import growthcraft.milk.common.CommonProxy;
import growthcraft.milk.common.Init;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
        Init.registerFluidColorHandlers();
        registerSpecialRenders();
    }

    public void registerModelBakeryVariants() {
        Init.registerModelBakeryVariants();
    }

    public void registerSpecialRenders() {
        Init.registerBlockSpecialRenders();
    }

    @Override
    public void postRegisterItems() {
        super.postRegisterItems();
        registerModelBakeryVariants();
    }
}
