package growthcraft.milk.proxy;

import growthcraft.milk.init.GrowthcraftMilkItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        registerRenders();
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    @Override
    public void registerRenders() {
        GrowthcraftMilkItems.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {

    }

    @Override
    public void registerSpecialRenders() {

    }

}
