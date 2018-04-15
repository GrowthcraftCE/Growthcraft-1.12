package growthcraft.cellar.common.init;

import growthcraft.cellar.common.handlers.EventHandlerLivingUpdateEventCellar;
import net.minecraftforge.common.MinecraftForge;

public class GrowthcraftCellarEvents {
	
	private GrowthcraftCellarEvents() {}
	
	public static void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new EventHandlerLivingUpdateEventCellar());
	}
}
