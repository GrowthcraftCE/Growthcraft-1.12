package growthcraft.bees.proxy;

import growthcraft.bees.init.GrowthcraftBeesBlocks;

public class CommonProxy {

    public void init() {
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    public void preInit() {
        registerRenders();
        registerTileEntities();
    }

    public void registerRenders() { }

    public void registerTileEntities() {
    	GrowthcraftBeesBlocks.registerTileEntities();
    }

    public void registerModelBakeryVariants() { }

    public void registerSpecialRenders() { }

}
