package growthcraft.grapes.proxy;

import growthcraft.grapes.Reference;
import growthcraft.grapes.tileentity.TileEntityGrapeVineFruit;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void init() {
        registerModelBakeryVariants();
        registerSpecialRenders();
    }

    public void preInit() {
        registerRenders();
        registerTileEntities();
    }
    public void registerRenders() {}

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityGrapeVineFruit.class, Reference.MODID + ":fishtrap");
    }

    public void registerModelBakeryVariants() { }

    public void registerSpecialRenders() {}

}
