package growthcraft.milk.common;

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
    public void registerModelBakeryVariants() { }
    public void registerSpecialRenders() { }
    public void registerTileEntities() {
    	Init.registerTileEntities();
    }

}
