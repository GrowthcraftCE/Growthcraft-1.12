package growthcraft.core.client;

import growthcraft.core.common.handlers.EnumHandler;
import growthcraft.core.common.init.GrowthcraftCoreBlocks;
import growthcraft.core.common.init.GrowthcraftCoreItems;
import growthcraft.core.common.CommonProxy;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        GrowthcraftCoreItems.registerRenders();
        GrowthcraftCoreBlocks.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
    	GrowthcraftCoreItems.crowbar.registerModelBakeryVariants(EnumHandler.CrowbarTypes.class);
    }

    @Override
    public void registerSpecialRenders() {
        // TileEntitySpecialRenderer for RopeKnot to display the fence post that it was tied around.
        // Still needs work.
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRopeKnot.class, new TileEntityRopeKnotRenderer());
    }

}
