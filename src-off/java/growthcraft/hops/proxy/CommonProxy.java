package growthcraft.hops.proxy;

import growthcraft.hops.Reference;
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
