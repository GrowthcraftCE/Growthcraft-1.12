package growthcraft.fishtrap.proxy;

import growthcraft.fishtrap.init.GrowthcraftFishtrapBlocks;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {
        GrowthcraftFishtrapBlocks.registerRenders();
    }

}
