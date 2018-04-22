package growthcraft.hops.common;

import growthcraft.hops.shared.Reference;
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

    public void registerRenders() { }

    public void registerTileEntities() {
//        GameRegistry.registerTileEntity(TileEntityHopsBush.class, Reference.MODID + ":hops_bush");
    }

    public void registerModelBakeryVariants() { }

    public void registerSpecialRenders() { }

}
