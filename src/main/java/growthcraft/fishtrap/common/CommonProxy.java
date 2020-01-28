package growthcraft.fishtrap.common;

import growthcraft.fishtrap.common.tileentity.TileEntityFishtrap;
import growthcraft.fishtrap.shared.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void init() {
    }

    public void preInit() {
        registerTileEntities();
    }

    public void postInit() {

    }

    @SuppressWarnings("deprecation")
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityFishtrap.class, Reference.MODID + ":fishtrap");
    }

    public void postRegisterItems() {
    }

}
