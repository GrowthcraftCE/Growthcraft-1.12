package growthcraft.cellar.common;

public class CommonProxy {

    public void preInit() {
    }

    public void init() {
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    public void registerRenders() {
    }
    public void registerModelBakeryVariants() { }

    public void registerSpecialRenders() {
    }

    public void registerTitleEntities() {
    	Init.registerTileEntities();
    }

}
