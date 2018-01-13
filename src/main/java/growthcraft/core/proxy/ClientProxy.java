package growthcraft.core.proxy;

import growthcraft.core.handlers.EnumHandler;
import growthcraft.core.init.GrowthcraftCoreBlocks;
import growthcraft.core.init.GrowthcraftCoreItems;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        GrowthcraftCoreItems.registerRenders();
        GrowthcraftCoreBlocks.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
    	GrowthcraftCoreItems.crowbar.registerModelBakeryVariants(EnumHandler.CrowbarTypes.values());
    }

    @Override
    public void registerSpecialRenders() {
        // TileEntitySpecialRenderer for RopeKnot to display the fence post that it was tied around.
        // Still needs work.
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRopeKnot.class, new TileEntityRopeKnotRenderer());
    }

}
