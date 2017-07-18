package growthcraft.fishtrap.proxy;

import growthcraft.fishtrap.Reference;
import growthcraft.fishtrap.tileentity.TileEntityFishtrap;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void registerRenders() { }

    public void registerTitleEntities() {
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":block_fishtrap");
    }

}
