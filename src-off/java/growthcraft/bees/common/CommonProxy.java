package growthcraft.bees.common;

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
    	Init.registerTileEntities();
    }

    public void registerModelBakeryVariants() { }

    public void registerSpecialRenders() { }

}
