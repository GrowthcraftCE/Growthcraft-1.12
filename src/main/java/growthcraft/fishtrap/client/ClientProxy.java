package growthcraft.fishtrap.client;

import growthcraft.fishtrap.GrowthcraftFishtrap;
import growthcraft.fishtrap.client.gui.GuiFishtrap;
import growthcraft.fishtrap.common.CommonProxy;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
        registerSpecialRenders();
        GrowthcraftFishtrap.guiProvider.register("growthcraft_fishtrap:fishtrap", GuiFishtrap.class);
    }

    @Override
    public void postRegisterItems() {
        super.postRegisterItems();
        registerModelBakeryVariants();
    }

    public void registerModelBakeryVariants() {
    }

    public void registerSpecialRenders() {
    }
}
