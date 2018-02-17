package growthcraft.hops.proxy;

import growthcraft.hops.init.GrowthcraftHopsFluids;
import growthcraft.hops.init.GrowthcraftHopsItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        registerRenders();
    }

    @Override
    public void init() {
    	GrowthcraftHopsItems.registerItemColorHandlers();
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    @Override
    public void registerRenders() {
        GrowthcraftHopsItems.registerRenders();
        GrowthcraftHopsFluids.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
    	GrowthcraftHopsItems.registerItemVariants();
    }

    @Override
    public void registerSpecialRenders() {

    }
}