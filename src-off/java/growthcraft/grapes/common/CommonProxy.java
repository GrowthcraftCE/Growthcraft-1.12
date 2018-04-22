package growthcraft.grapes.common;

public class CommonProxy {
	// REVISE_TEAM

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
    }

    public void registerModelBakeryVariants() { }

    public void registerSpecialRenders() { }

}
