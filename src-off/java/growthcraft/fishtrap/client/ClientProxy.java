package growthcraft.fishtrap.client;

import growthcraft.fishtrap.client.gui.GuiHandler;
import growthcraft.fishtrap.common.CommonProxy;
import growthcraft.fishtrap.common.Init;
import growthcraft.fishtrap.shared.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());
    }

    @Override
    public void registerRenders() {
        Init.registerBlockRenders();
    }

}
