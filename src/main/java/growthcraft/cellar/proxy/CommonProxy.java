package growthcraft.cellar.proxy;

import growthcraft.cellar.Reference;
import growthcraft.cellar.tileentity.TileEntityBrewKettle;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit() {
    }

    public void registerRenders() {
    }
    public void registerModelBakeryVariants() { }

    public void registerSpecialRenders() {
    }

    public void registerTitleEntities() {
        GameRegistry.registerTileEntity(TileEntityBrewKettle.class, Reference.MODID + ":brew_kettle");
    }

}
