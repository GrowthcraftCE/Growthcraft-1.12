package growthcraft.core.client;

import growthcraft.core.shared.init.GrowthcraftCoreItems;
import growthcraft.core.common.CommonProxy;
import growthcraft.core.common.Init;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        Init.registerItemRenders();
        Init.registerBlockRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
    	GrowthcraftCoreItems.crowbar.registerModelBakeryVariants(GrowthcraftCoreItems.CrowbarTypes.class);
    }

    @Override
    public void registerSpecialRenders() {
        // TileEntitySpecialRenderer for RopeKnot to display the fence post that it was tied around.
        // Still needs work.
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRopeKnot.class, new TileEntityRopeKnotRenderer());
    }

}
