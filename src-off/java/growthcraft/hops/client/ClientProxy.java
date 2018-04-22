package growthcraft.hops.client;

import growthcraft.hops.common.CommonProxy;
import growthcraft.hops.common.Init;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        registerRenders();
    }

    @Override
    public void init() {
    	Init.registerItemColorHandlers();
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    @Override
    public void registerRenders() {
        Init.registerItemRenders();
        Init.registerFluidRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
    	Init.registerItemVariants();
    }

    @Override
    public void registerSpecialRenders() {

    }
}