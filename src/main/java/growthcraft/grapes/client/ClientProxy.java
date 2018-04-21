package growthcraft.grapes.client;

import growthcraft.grapes.common.CommonProxy;
import growthcraft.grapes.common.init.GrowthcraftGrapesFluids;
import growthcraft.grapes.common.init.GrowthcraftGrapesItems;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
    	super.init();
    	GrowthcraftGrapesItems.registerItemColorHandlers();
    }

    @Override
    public void registerRenders() {
        GrowthcraftGrapesItems.registerRenders();
        GrowthcraftGrapesFluids.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
        GrowthcraftGrapesItems.registerItemVariants();
    }

    @Override
    public void registerSpecialRenders() {
        // TileEntitySpecialRenderer for showing the type of grape that is stored.
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrapeVineFruit.class, new RendererGrapeVineFruit());
    }
}
