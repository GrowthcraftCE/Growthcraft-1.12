package growthcraft.milk.common;

public class CommonProxy {

    public void init() {
    }

    public void preInit() {
        registerTileEntities();
    }

	public void postInit() {
		
	}
	
	public void postRegisterItems() {
	}
    
    public void registerTileEntities() {
    	Init.registerTileEntities();
    }

}
