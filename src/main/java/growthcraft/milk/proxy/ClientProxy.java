package growthcraft.milk.proxy;

import growthcraft.milk.init.GrowthcraftMilkBlocks;
import growthcraft.milk.init.GrowthcraftMilkFluids;
import growthcraft.milk.init.GrowthcraftMilkItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
    	super.init();
    	GrowthcraftMilkFluids.registerColorHandlers();
    }

    @Override
    public void registerRenders() {
        GrowthcraftMilkBlocks.registerRenders();
        GrowthcraftMilkFluids.registerRenders();
        GrowthcraftMilkItems.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {

    }

    @Override
    public void registerSpecialRenders() {

    }

}
