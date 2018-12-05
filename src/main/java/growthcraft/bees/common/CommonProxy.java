package growthcraft.bees.common;

public class CommonProxy {
	// REVISE_TEAM

    public void init() {
    }

    public void preInit() {
        registerTileEntities();
    }

	public void postInit() {
		
	}
	
    public void registerTileEntities() {
    	Init.registerTileEntities();
    }

	public void postRegisterItems() {
	}

}
