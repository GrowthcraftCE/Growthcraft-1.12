package growthcraft.cellar.events;

import net.minecraftforge.common.MinecraftForge;

public class CellarEvents {
	public static void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new EventHandlerLivingUpdateEventCellar());
	}
}
