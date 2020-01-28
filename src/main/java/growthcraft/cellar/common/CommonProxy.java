package growthcraft.cellar.common;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CommonProxy {

    public void preInit() {
        // Nothing to do here at this time.
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
        // Nothing to do here at this time.
    }

    public void registerStateMappers() {
        // Nothing to do here at this time.
    }

}
