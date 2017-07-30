package growthcraft.core.proxy;

import growthcraft.core.Reference;
import growthcraft.core.tileentity.TileEntityRopeKnot;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void registerRenders() { }

    public void registerModelBakeryVariants() { }

    public void registerSpecialRenders() {}

    public void registerTitleEntities() {
        GameRegistry.registerTileEntity(TileEntityRopeKnot.class, Reference.MODID + ":rope_knot");
    }
}
