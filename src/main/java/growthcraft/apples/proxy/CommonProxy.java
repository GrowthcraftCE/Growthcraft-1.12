package growthcraft.apples.proxy;

public class CommonProxy {

    public void init() {
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    public void preInit() {
        registerRenders();
        registerTileEntities();
    }

    public void registerModelBakeryVariants() { }
    public void registerRenders() { }
    public void registerSpecialRenders() {  }
    private void registerTileEntities() { }



}
