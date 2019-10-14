package growthcraft.cellar.common;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CommonProxy {

    public void preInit() {
    }

    public void init() {
        FMLInterModComms.sendMessage(
                "waila",
                "register",
                "growthcraft.cellar.common.compat.waila.WailaDataProvider.callbackRegister"
        );
    }

    public void registerTitleEntities() {
        Init.registerTileEntities();
    }

    public void postRegisterItems() {
    }

}
