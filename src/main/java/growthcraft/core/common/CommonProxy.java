package growthcraft.core.common;

import growthcraft.core.Reference;
import growthcraft.core.common.tileentity.TileEntityRopeKnot;
import growthcraft.core.common.worldgen.BlockSaltGeneration;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void init() {
        GameRegistry.registerWorldGenerator(new BlockSaltGeneration(), 0);
    }

    public void registerRenders() { }

    public void registerModelBakeryVariants() { }

    public void registerSpecialRenders() {}

    public void registerTitleEntities() {
        GameRegistry.registerTileEntity(TileEntityRopeKnot.class, Reference.MODID + ":rope_knot");
    }
}
