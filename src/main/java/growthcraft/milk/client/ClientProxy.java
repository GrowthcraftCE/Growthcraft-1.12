package growthcraft.milk.client;

import growthcraft.milk.common.CommonProxy;
import growthcraft.milk.common.Init;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
    	super.init();
    	Init.registerFluidColorHandlers();
    }

    @Override
    public void registerRenders() {
        Init.registerBlockRenders();
        Init.registerFluidRenders();
        Init.registerItemRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
    	Init.registerModelBakeryVariants();
    }

    @Override
    public void registerSpecialRenders() {
    	Init.registerBlockSpecialRenders();
    }

}
