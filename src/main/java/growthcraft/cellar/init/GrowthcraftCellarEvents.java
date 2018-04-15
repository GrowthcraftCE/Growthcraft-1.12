package growthcraft.cellar.init;

import growthcraft.cellar.handlers.EventHandlerLivingUpdateEventCellar;
import net.minecraftforge.common.MinecraftForge;

public class GrowthcraftCellarEvents {
	
	private GrowthcraftCellarEvents() {}
	
	public static void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new EventHandlerLivingUpdateEventCellar());
	}
}
