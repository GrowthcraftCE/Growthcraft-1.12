package growthcraft.grapes.common;

import growthcraft.grapes.common.handler.HarvestDropsEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	// REVISE_TEAM

    public void init() {
    }

    public void preInit() {
        registerTileEntities();
    }

	public void postInit() {
        MinecraftForge.EVENT_BUS.register(new HarvestDropsEventHandler());
	}
	
    public void registerTileEntities() {
    }

	public void postRegisterItems() {
	}

}
