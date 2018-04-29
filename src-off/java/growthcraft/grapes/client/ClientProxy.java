package growthcraft.grapes.client;

import growthcraft.grapes.common.CommonProxy;
import growthcraft.grapes.common.Init;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
    	super.init();
    	Init.registerItemColorHandlers();
    }

    @Override
    public void registerRenders() {
        Init.registerItemRenders();
        Init.registerFluidRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
        Init.registerItemVariants();
    }

    @Override
    public void registerSpecialRenders() {
        // TileEntitySpecialRenderer for showing the type of grape that is stored.
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrapeVineFruit.class, new RendererGrapeVineFruit());
    }
}
