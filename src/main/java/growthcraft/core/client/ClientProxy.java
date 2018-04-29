package growthcraft.core.client;

import growthcraft.core.shared.init.GrowthcraftCoreItems;
import growthcraft.core.common.CommonProxy;
import growthcraft.core.common.Init;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
        registerRenders();
	}
	
	@Override
	public void init() {
		super.init();
        registerSpecialRenders();
	}
	
	@Override
    public void postRegisterItems() {
    	super.postRegisterItems();
    	registerModelBakeryVariants();
    }
	
    public void registerRenders() {
        Init.registerItemRenders();
        Init.registerBlockRenders();
    }

    public void registerModelBakeryVariants() {
    	GrowthcraftCoreItems.crowbar.registerModelBakeryVariants(GrowthcraftCoreItems.CrowbarTypes.class);
    }

    public void registerSpecialRenders() {
        // TileEntitySpecialRenderer for RopeKnot to display the fence post that it was tied around.
        // Still needs work.
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRopeKnot.class, new TileEntityRopeKnotRenderer());
    }

}
